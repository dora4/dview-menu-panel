package dora.widget.panel

import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_MARGIN_TOP
import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_TITLE_SPAN
import java.util.Arrays

class MenuPanelItemGroup @JvmOverloads constructor(
    override var marginTop: Int = DEFAULT_MARGIN_TOP,
    override var title: String? = null,
    private var titleSpan: MenuPanelItemRoot.Span = MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN),
    val items: MutableList<MenuPanelItem>
) : MenuPanelItemRoot {

    constructor(
        marginTop: Int = DEFAULT_MARGIN_TOP,
        title: String?,
        titleSpan: MenuPanelItemRoot.Span = MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN),
        vararg items: MenuPanelItem?) :
            this(marginTop, title, titleSpan, Arrays.asList<MenuPanelItem>(*items))

    constructor(
        marginTop: Int = DEFAULT_MARGIN_TOP,
        vararg items: MenuPanelItem?) :
            this(marginTop, "", MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN), Arrays.asList<MenuPanelItem>(*items))

    constructor(
        title: String?,
        titleSpan: MenuPanelItemRoot.Span = MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN),
        vararg items: MenuPanelItem?) :
            this(DEFAULT_MARGIN_TOP, title, titleSpan, Arrays.asList<MenuPanelItem>(*items))

    constructor(
        vararg items: MenuPanelItem) :
            this(DEFAULT_MARGIN_TOP, "",  MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN), Arrays.asList<MenuPanelItem>(*items))

    override fun hasTitle(): Boolean {
        return title != null && title != ""
    }

    override fun getTitleSpan(): MenuPanelItemRoot.Span {
        return titleSpan
    }

    override fun setTitleSpan(titleSpan: MenuPanelItemRoot.Span) {
        this.titleSpan = titleSpan
    }

    companion object {

        /**
         * 快速构建一个菜单项分组（MenuPanelItemGroup）。
         *
         * 用于将多个 [MenuPanelItem] 包装成一组，统一设置标题等属性，用于面板展示。
         * 比如可以将多个按钮项、输入框项或普通项组合成一个分组。
         *
         * @param title 分组标题，可为空字符串表示不显示标题
         * @param items 可变参数，表示该分组下包含的多个菜单项
         * @return 构建好的 [MenuPanelItemGroup] 实例
         *
         * @since 1.37
         */
        fun group(title: String, vararg items: MenuPanelItem): MenuPanelItemGroup {
            return MenuPanelItemGroup(title = title, items = items.toMutableList())
        }
    }
}