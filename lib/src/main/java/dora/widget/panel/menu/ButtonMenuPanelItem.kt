package dora.widget.panel.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import dora.widget.panel.R
import dora.widget.panel.MenuPanelItem
import dora.widget.panel.MenuPanelItemGroup
import dora.widget.panel.MenuPanelItemRoot
import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_MARGIN_TOP
import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_TITLE_SPAN

class ButtonMenuPanelItem @JvmOverloads constructor(
    override var marginTop: Int = DEFAULT_MARGIN_TOP,
    override var title: String? = "",
    private var titleSpan: MenuPanelItemRoot.Span = MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN),
    override val menuName: String? = MenuPanelItem.generateMenuName("ButtonMenuPanelItem"),
    private val text: String? = "",
    private val textColor: Int,
) : MenuPanelItem {

    constructor(menuName: String, text: String, textColor: Int) :
            this(DEFAULT_MARGIN_TOP, "", MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN), menuName, text, textColor)

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
        get() = R.layout.layout_menu_panel_button

    override fun inflateView(context: Context): View {
        return LayoutInflater.from(context).inflate(layoutId, null)
    }

    override fun initData(menuView: View) {
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.topMargin = marginTop
        menuView.layoutParams = lp
        val menuTextView = menuView.findViewById<TextView>(ID_TEXT_VIEW_MENU)
        menuTextView.text = text
        menuTextView.setTextColor(textColor)
    }

    companion object {

        @JvmField
        val ID_TEXT_VIEW_MENU: Int = R.id.tv_menu_panel_button_menu

        /**
         * 快速构建一组按钮菜单项的分组。
         *
         * 每个按钮项由一个 Triple<String, String, Int> 表示：
         * - 第一个值为 menuName（唯一标识）
         * - 第二个值为 text（按钮文本）
         * - 第三个值为 textColor（按钮颜色）
         *
         * @param title 分组标题
         * @param items 按钮配置数组
         * @return MenuPanelItemGroup 实例
         *
         * @since 1.38
         */
        fun groupButtonItem(
            title: String,
            vararg items: Triple<String, String, Int>
        ): MenuPanelItemGroup {
            return MenuPanelItemGroup(
                title = title,
                items = items.map {
                    ButtonMenuPanelItem(
                        menuName = it.first,
                        text = it.second,
                        textColor = it.third
                    )
                }.toTypedArray()
            )
        }
    }
}