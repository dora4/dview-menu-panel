package dora.widget.panel.menu

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import dora.widget.panel.R
import dora.widget.panel.MenuPanelItem
import dora.widget.panel.MenuPanelItemGroup
import dora.widget.panel.MenuPanelItemRoot
import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_MARGIN_TOP
import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_TITLE_SPAN

class InputMenuPanelItem
    @JvmOverloads constructor(
    override var marginTop: Int = DEFAULT_MARGIN_TOP,
    override var title: String? = "",
    private var titleSpan: MenuPanelItemRoot.Span = MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN),
    override val menuName: String? = MenuPanelItem.generateMenuName("InputMenuPanelItem"),
    private val hint: String?  = "",
    private val content: String? = "",
    private val showArrowIcon: Boolean = false,
    private val watcher: ContentWatcher? = null,
    private val onRandom: ContentWriter? = null,
) : MenuPanelItem {

    constructor(title: String, titleSpan: MenuPanelItemRoot.Span, hint: String,
                content: String, showArrowIcon: Boolean, watcher: ContentWatcher? = null,
                onRandom: ContentWriter? = null) : this(DEFAULT_MARGIN_TOP,
        title, titleSpan, MenuPanelItem.generateMenuName("InputMenuPanelItem"),
        hint, content, showArrowIcon, watcher, onRandom)

    constructor(title: String, titleSpan: MenuPanelItemRoot.Span, hint: String,
                content: String, showArrowIcon: Boolean,
                onRandom: ContentWriter? = null) : this(title, titleSpan, hint, content, showArrowIcon, null, onRandom)

    constructor(hint: String,
                content: String, watcher: ContentWatcher? = null) : this(DEFAULT_MARGIN_TOP,
        "", MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN), MenuPanelItem.generateMenuName("InputMenuPanelItem"),
        hint, content, false, watcher)

    constructor(hint: String,
                content: String) : this(hint, content, null)

    override fun initData(menuView: View) {
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.topMargin = marginTop
        menuView.layoutParams = lp
        val editText = menuView.findViewById<EditText>(ID_EDIT_TEXT_INPUT)
        editText.hint = hint
        if (!TextUtils.isEmpty(content)) {
            editText.setText(content)
            editText.setSelection(content!!.length)
        }
        if (watcher != null) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    watcher.onContentChanged(this@InputMenuPanelItem, s.toString())
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
        val arrowView = menuView.findViewById<LinearLayout>(ID_LINEAR_LAYOUT_ARROW)
        arrowView.setOnClickListener {
            onRandom?.onRandomContent(this, editText)
        }
        if (showArrowIcon) {
            arrowView.visibility = View.VISIBLE
        } else {
            arrowView.visibility = View.GONE
        }
    }

    override fun hasTitle(): Boolean {
        return title != null && title != ""
    }

    override fun getTitleSpan(): MenuPanelItemRoot.Span {
        return titleSpan
    }

    override fun setTitleSpan(titleSpan: MenuPanelItemRoot.Span) {
        this.titleSpan = titleSpan
    }

    override val layoutId: Int
        get() = R.layout.layout_menu_panel_input

    override fun inflateView(context: Context): View {
        return LayoutInflater.from(context).inflate(layoutId, null)
    }

    interface ContentWatcher {
        fun onContentChanged(item: InputMenuPanelItem, content: String)
    }

    interface ContentWriter {
        fun onRandomContent(item: InputMenuPanelItem, editText: EditText)
    }

    companion object {

        @JvmField
        val ID_EDIT_TEXT_INPUT: Int = R.id.et_menu_panel_input
        @JvmField
        val ID_LINEAR_LAYOUT_ARROW: Int = R.id.ll_menu_panel_arrow
        @JvmField
        val ID_IMAGE_VIEW_RANDOM: Int = R.id.iv_menu_panel_random

        /**
         * 快速构建一个输入菜单项的分组。
         *
         * 每个输入项由一个 Triple<String, String, String> 表示：
         * - 第一个值为 menuName（唯一标识）
         * - 第二个值为 hint（输入框提示）
         * - 第三个值为 content（初始内容）
         *
         * 可选传入统一的内容监听器 [watcher] 和“随机内容”生成器 [onRandom]。
         *
         * @param title 分组标题
         * @param items 输入项的配置数组
         * @param watcher 每个输入框内容变化的监听器（可选）
         * @param onRandom 点击箭头图标后的内容生成逻辑（可选）
         * @return MenuPanelItemGroup 实例
         *
         * @since 1.38
         */
        fun groupInputItem(
            title: String,
            vararg items: Triple<String, String, String>,
            watcher: ContentWatcher? = null,
            onRandom: ContentWriter? = null
        ): MenuPanelItemGroup {
            return MenuPanelItemGroup(
                title = title,
                items = items.map {
                    InputMenuPanelItem(
                        marginTop = DEFAULT_MARGIN_TOP,
                        title = "",
                        titleSpan = MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN),
                        menuName = it.first,
                        hint = it.second,
                        content = it.third,
                        showArrowIcon = onRandom != null,
                        watcher = watcher,
                        onRandom = onRandom
                    )
                }.toTypedArray()
            )
        }
    }
}