package dora.widget.panel

import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_MARGIN_TOP
import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_TITLE_SPAN
import java.util.Arrays

class MenuPanelItemGroup @JvmOverloads constructor(
    override var marginTop: Int = DEFAULT_MARGIN_TOP,
    override var title: String?,
    private var titleSpan: MenuPanelItemRoot.Span = MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN),
    val items: MutableList<MenuPanelItem>
) : MenuPanelItemRoot {

    constructor(title: String?, items: MutableList<MenuPanelItem>) : this(
        1,
        title,
        MenuPanelItemRoot.Span(10, 10),
        items
    )

    constructor(title: String?, vararg items: MenuPanelItem?) : this(
        title,
        Arrays.asList<MenuPanelItem>(*items)
    )

    constructor(
        marginTop: Int,
        title: String?,
        titleSpan: MenuPanelItemRoot.Span,
        vararg items: MenuPanelItem?
    ) : this(marginTop, title, titleSpan, Arrays.asList<MenuPanelItem>(*items))

    /**
     * 没有菜单组的标题。
     *
     * @param marginTop
     * @param items
     */
    constructor(marginTop: Int, items: MutableList<MenuPanelItem>) : this(
        marginTop,
        "",
        MenuPanelItemRoot.Span(10, 10),
        items
    )

    constructor(marginTop: Int, vararg items: MenuPanelItem?) : this(
        marginTop,
        Arrays.asList<MenuPanelItem>(*items)
    )

    /**
     * 没有菜单组的上边距。
     *
     * @param title
     * @param titleSpan
     */
    constructor(
        title: String?,
        titleSpan: MenuPanelItemRoot.Span,
        items: MutableList<MenuPanelItem>
    ) : this(0, title, titleSpan, items)

    constructor(
        title: String?,
        titleSpan: MenuPanelItemRoot.Span,
        vararg items: MenuPanelItem?
    ) : this(title, titleSpan, Arrays.asList<MenuPanelItem>(*items))

    override fun hasTitle(): Boolean {
        return title != null && title != ""
    }

    override fun getTitleSpan(): MenuPanelItemRoot.Span {
        return titleSpan
    }

    override fun setTitleSpan(titleSpan: MenuPanelItemRoot.Span) {
        this.titleSpan = titleSpan
    }
}