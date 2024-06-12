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

    constructor(marginTop: Int, items: MutableList<MenuPanelItem>) : this(marginTop, "",
        MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN), items)

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
}