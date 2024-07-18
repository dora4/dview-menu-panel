package dora.widget.panel

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.IntRange
import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_MARGIN_TOP
import java.util.LinkedList
import java.util.UUID

/**
 * 通用功能菜单，类似于RecyclerView。
 */
open class MenuPanel : ScrollView, View.OnClickListener {

    /**
     * 面板的背景颜色，一般为浅灰色。
     */
    private var panelBgColor = DEFAULT_PANEL_BG_COLOR
    protected var menuPanelItems: MutableList<MenuPanelItem> = ArrayList()
    protected var viewsCache: MutableList<View> = ArrayList()
    private var onPanelMenuClickListener: OnPanelMenuClickListener? = null
    private var onPanelScrollListener: OnPanelScrollListener? = null
    private val groupInfoList: MutableList<GroupInfo> = ArrayList()
    private val listenerInfo = LinkedList<ListenerDelegate>()

    lateinit var panelRoot: FrameLayout

    /**
     * 存放Menu和Custom View。
     */
    lateinit var container: LinearLayout

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    fun removeItem(item: MenuPanelItem): MenuPanel {
        val position = seekForItemPosition(item)
        if (position != SEEK_FOR_ITEM_ERROR_NOT_FOUND &&
            position != SEEK_FOR_ITEM_ERROR_MISS_MENU_NAME
        ) {
            removeItem(position)
        } else {
            Log.e(TAG, "failed to seekForItemPosition，$position")
        }
        return this
    }

    private fun init(context: Context) {
        isFillViewport = true
        addContainer(context)
    }

    fun setOnPanelMenuClickListener(l: OnPanelMenuClickListener) {
        onPanelMenuClickListener = l
    }

    fun setOnPanelScrollListener(l: OnPanelScrollListener) {
        onPanelScrollListener = l
    }

    fun parseItemView(item: MenuPanelItem, isLoadData: Boolean = false): View {
        val menuView = item.inflateView(context)
        if (isLoadData) {
            item.initData(menuView)
        }
        return menuView
    }

    val items: List<MenuPanelItem>
        get() = menuPanelItems

    fun getItem(@IntRange(from = 0) position: Int): MenuPanelItem? {
        if (position > menuPanelItems.size - 1) {
            return null
        }
        return menuPanelItems[position]
    }

    val itemViewsCache: List<View>
        get() = viewsCache

    fun getGroupInfo(item: MenuPanelItem): GroupInfo? {
        for (groupInfo in groupInfoList) {
            if (groupInfo.hasItem(item)) {
                return groupInfo
            }
        }
        return null
    }

    /**
     * 根据item的position移除一个item，此方法被多处引用，修改前需要理清布局层级结构。
     *
     * @param position
     * @return
     */
    fun removeItem(@IntRange(from = 0) position: Int): MenuPanel {
        val item = menuPanelItems[position]
        val groupInfo = getGroupInfo(item)
        val belongToGroup = groupInfo != null
        val view = getItemView(position)
        if (!belongToGroup) {
            container.removeView(view)
        } else {
            // 属于一个组
            val menuGroupCard = groupInfo!!.groupMenuCard
            menuGroupCard.removeView(view)
            groupInfo.removeItem(item)
            // 一个组内的item全部被移除后，也移除掉这个组
            if (groupInfo.isEmpty) {
                // 连同title一起移除
                container.removeView(menuGroupCard)
                groupInfoList.remove(groupInfo)
            }
        }
        menuPanelItems.removeAt(position)
        viewsCache.removeAt(position)
        listenerInfo.removeAt(position)
        return this
    }

    /**
     * 清空所有item和相关view。
     */
    fun clearAll(): MenuPanel {
        if (menuPanelItems.size > 0) {
            menuPanelItems.clear()
        }
        container.removeAllViews()
        viewsCache.clear()
        groupInfoList.clear()
        listenerInfo.clear()
        return this
    }

    /**
     * 移除连续的item。
     *
     * @param start 第一个item的下标，包括
     * @param end   最后一个item的下标，包括
     * @return
     */
    fun removeItemRange(@IntRange(from = 0) start: Int, end: Int): MenuPanel {
        for (i in start until end + 1) {
            removeItem(start)
        }
        return this
    }

    /**
     * 从某个位置移除到最后一个item。
     *
     * @param start 第一个item的下标，包括
     * @return
     */
    fun removeItemFrom(@IntRange(from = 0) start: Int): MenuPanel {
        val end = menuPanelItems.size - 1
        if (start <= end) {
            // 有就移除
            removeItemRange(start, end)
        }
        return this
    }

    /**
     * 从第一个item移除到某个位置。
     *
     * @param end 最后一个item的下标，包括
     * @return
     */
    fun removeItemTo(end: Int): MenuPanel {
        val start = 0
        removeItemRange(start, end)
        return this
    }

    val itemCount: Int
        get() = menuPanelItems.size

    fun addMenuGroup(itemGroup: MenuPanelItemGroup): MenuPanel {
        val hasTitle = itemGroup.hasTitle()
        val items = itemGroup.items
        val titleView = TextView(context)
        titleView.setPadding(
            itemGroup.getTitleSpan().left, itemGroup.getTitleSpan().top,
            itemGroup.getTitleSpan().right, itemGroup.getTitleSpan().bottom
        )
        titleView.text = itemGroup.title
        titleView.textSize = 15f
        titleView.setTextColor(DEFAULT_TITLE_COLOR)
        val menuGroupCard = LinearLayout(context)
        menuGroupCard.orientation = LinearLayout.VERTICAL
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.topMargin = itemGroup.marginTop
        menuGroupCard.layoutParams = lp
        if (hasTitle) {
            menuGroupCard.addView(titleView)
        }
        for (item in items) {
            // 清除组内item的边距等
            applyDefault(item)
            addMenuToCard(item, menuGroupCard)
        }
        container.addView(menuGroupCard)
        // 保存菜单组信息
        groupInfoList.add(GroupInfo(items, menuGroupCard))
        return this
    }

    override fun addView(child: View) {
        if (child !is FrameLayout) {
            return
        }
        if (childCount > 1) {
            return
        }
        super.addView(child)
    }

    private fun addContainer(context: Context) {
        panelRoot = FrameLayout(context)
        container = LinearLayout(context)
        container.orientation = LinearLayout.VERTICAL
        container.setBackgroundColor(panelBgColor)
        panelRoot.addView(container)
        addView(panelRoot)
    }

    fun addMenu(item: MenuPanelItem): MenuPanel {
        val menuView = bindItemListener(item)
        if (!item.hasTitle()) {
            container.addView(menuView)
        } else {
            val titleView = TextView(context)
            titleView.setPadding(
                item.getTitleSpan().left, item.getTitleSpan().top,
                item.getTitleSpan().right, item.getTitleSpan().bottom
            )
            titleView.text = item.title
            titleView.textSize = 15f
            titleView.setTextColor(DEFAULT_TITLE_COLOR)
            val menuCard = LinearLayout(context)
            menuCard.orientation = LinearLayout.VERTICAL
            menuCard.addView(titleView)
            menuCard.addView(menuView)
            container.addView(menuCard)
        }
        return this
    }

    private fun addMenuToCard(item: MenuPanelItem, container: LinearLayout) {
        val menuView = bindItemListener(item)
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.topMargin = item.marginTop
        menuView.layoutParams = lp
        container.addView(menuView)
    }

    fun seekForItemPosition(item: MenuPanelItem): Int {
        for (i in menuPanelItems.indices) {
            val mpi = menuPanelItems[i]
            val menu = mpi.menuName
            if (menu == "" || item.menuName == "") {
                return SEEK_FOR_ITEM_ERROR_MISS_MENU_NAME //失去菜单名称
            }
            if (menu == item.menuName) {
                return i
            }
        }
        return SEEK_FOR_ITEM_ERROR_NOT_FOUND
    }

    @Deprecated("已过时，请使用getViewByPosition()方法替代", replaceWith = ReplaceWith("getViewByPosition"),
        level = DeprecationLevel.WARNING)
    fun getCacheChildView(position: Int, viewId: Int): View? {
        val menuView = getItemView(position)
        return menuView?.findViewById(viewId)
    }

    /**
     * 获取MenuPanel中条目布局中的子控件，推荐使用。
     *
     * @param position
     * @param viewId
     * @return
     */
    fun getViewByPosition(@IntRange(from = 0) position: Int, @IdRes viewId: Int): View? {
        val menuView = getItemView(position)
        return menuView?.findViewById(viewId)
    }

    @Deprecated("已过时，请使用getItemView()方法替代", replaceWith = ReplaceWith("getItemView"),
        level = DeprecationLevel.WARNING)
    fun getCacheViewFromItem(item: MenuPanelItem): View? {
        val position = seekForItemPosition(item)
        return if (position != SEEK_FOR_ITEM_ERROR_NOT_FOUND &&
            position != SEEK_FOR_ITEM_ERROR_MISS_MENU_NAME
        ) {
            getItemView(position)
        } else null
    }

    /**
     * 获取item的view，用于修改item的数据。
     *
     * @param item
     * @return
     */
    fun getItemView(item: MenuPanelItem): View? {
        val position = seekForItemPosition(item)
        return if (position != SEEK_FOR_ITEM_ERROR_NOT_FOUND &&
            position != SEEK_FOR_ITEM_ERROR_MISS_MENU_NAME
        ) {
            getItemView(position)
        } else null
    }

    @Deprecated("已过时，请使用getItemView()方法替代", replaceWith = ReplaceWith("getItemView"),
        level = DeprecationLevel.WARNING)
    fun getCacheViewFromPosition(position: Int): View? {
        return if (position < viewsCache.size) {
            viewsCache[position]
        } else null
    }

    /**
     * 获取item的view，用于修改item的数据。
     *
     * @param position item的位置，从0开始
     * @return
     */
    fun getItemView(@IntRange(from = 0) position: Int): View? {
        return viewsCache[position]
    }

    protected fun getCacheViewFromTag(tag: String): View? {
        for (delegate in listenerInfo) {
            val dtag = delegate.tag
            if (dtag == tag) {
                val position = delegate.position
                return getItemView(position)
            }
        }
        return null
    }

    /**
     * 绑定item的点击事件。
     *
     * @param item
     * @return 绑定成功后返回item的view
     */
    private fun bindItemListener(item: MenuPanelItem): View {
        menuPanelItems.add(item)
        //解析Item所对应的布局，并调用item的initData
        val menuView = parseItemView(item, true)
        viewsCache.add(menuView)
        val tag = UUID.randomUUID().toString().substring(0, 16)
        menuView.tag = tag
        val delegate = getListenerInfo(tag)
        menuView.setOnClickListener(delegate)
        listenerInfo.add(delegate)
        return menuView
    }

    private fun applyDefault(item: MenuPanelItem) {
        // item的上边距修改为1px
        item.marginTop = DEFAULT_MARGIN_TOP
        // item去掉标题
        item.title = ""
        // item去掉标题边距
        item.setTitleSpan(MenuPanelItemRoot.Span())
    }

    /**
     * 不是菜单，所以不会影响菜单的点击事件位置，但需要自己处理控件内部的点击事件。
     *
     * @param view
     * @param <T>
     */
    fun <T : View> addCustomView(view: T): MenuPanel {
        container.addView(view)
        return this
    }

    fun <T : View> addCustomView(view: T, @IntRange(from = 0) index: Int): MenuPanel {
        container.addView(view, index)
        return this
    }

    fun removeCustomViewAt(@IntRange(from = 0) position: Int): MenuPanel {
        if (container.childCount > position) {
            // 有就移除
            container.removeViewAt(position)
        }
        return this
    }

    /**
     * 样式等参数改变才需要更新，只有类似于addItem、removeItem这样的，不需要调用此方法。
     */
    open fun updatePanel() {
        requestLayout()
    }

    fun getListenerInfo(tag: String): ListenerDelegate {
        return ListenerDelegate(tag, menuPanelItems.size - 1, this)
    }

    class GroupInfo(
        private var items: MutableList<MenuPanelItem>,
        var groupMenuCard: LinearLayout
    ) {
        fun hasItem(item: MenuPanelItem): Boolean {
            return items.contains(item)
        }

        val itemCount: Int
            get() = items.size

        fun addItem(item: MenuPanelItem) {
            items.add(item)
        }

        fun removeItem(item: MenuPanelItem) {
            items.remove(item)
        }

        val isEmpty: Boolean
            get() = items.size == 0

        fun getItems(): MutableList<MenuPanelItem> {
            return items
        }
    }

    override fun onClick(v: View) {
        val tag = v.tag as String
        for (delegate in listenerInfo) {
            if (delegate.tag == tag) {
                val clickPos = delegate.position
                menuPanelItems[clickPos].menuName?.let {
                    onPanelMenuClickListener?.onMenuClick(clickPos, v, it)
                }
                break
            }
        }
    }

    fun setPanelBgColor(@ColorInt color: Int): MenuPanel {
        panelBgColor = color
        container.setBackgroundColor(panelBgColor)
        return this
    }

    interface OnPanelMenuClickListener {
        fun onMenuClick(position: Int, view: View, menuName: String)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (scrollY == 0) {
            onPanelScrollListener?.onScrollToTop()
        } else if (panelRoot.measuredHeight == scrollY + height) {
            onPanelScrollListener?.onScrollToBottom()
        }
    }

    interface OnPanelScrollListener {
        fun onScrollToTop()
        fun onScrollToBottom()
    }

    class ListenerDelegate(
        val tag: String,
        val position: Int,
        private val listener: OnClickListener
    ) : OnClickListener {
        override fun onClick(v: View) {
            listener.onClick(v)
        }
    }

    companion object {
        private const val TAG = "MenuPanel"
        private const val DEFAULT_PANEL_BG_COLOR = -0xa0a07
        private const val DEFAULT_TITLE_COLOR = -0x666667
        private const val SEEK_FOR_ITEM_ERROR_NOT_FOUND = -1
        private const val SEEK_FOR_ITEM_ERROR_MISS_MENU_NAME = -2
    }
}