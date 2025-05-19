package dora.widget.panel.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import dora.widget.panel.R
import dora.widget.panel.MenuPanelItem
import dora.widget.panel.MenuPanelItemGroup
import dora.widget.panel.MenuPanelItemRoot
import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_MARGIN_TOP
import dora.widget.panel.MenuPanelItemRoot.Companion.DEFAULT_TITLE_SPAN

class NormalMenuPanelItem @JvmOverloads constructor(
    override var marginTop: Int = DEFAULT_MARGIN_TOP,
    override var title: String? = "",
    private var titleSpan: MenuPanelItemRoot.Span = MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN),
    override val menuName: String? = MenuPanelItem.generateMenuName("NormalMenuPanelItem"),
    private val text: String? = "",
    private val showArrowIcon: Boolean = true,
    private val arrowText: String? = ""
) : MenuPanelItem {

    constructor(marginTop: Int, menuName: String, text: String, showArrowIcon: Boolean, arrowText: String) : this(marginTop, "",
        MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN), menuName, text, showArrowIcon, arrowText)

    constructor(menuName: String, text: String, showArrowIcon: Boolean, arrowText: String) : this(DEFAULT_MARGIN_TOP, "",
        MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN), menuName, text, showArrowIcon, arrowText)

    constructor(marginTop: Int, menuName: String, text: String, showArrowIcon: Boolean) : this(marginTop, menuName, text, showArrowIcon, "")

    constructor(marginTop: Int, menuName: String, text: String) : this(marginTop, menuName, text, true)

    constructor(menuName: String, text: String, showArrowIcon: Boolean) : this(DEFAULT_MARGIN_TOP, menuName, text, showArrowIcon)

    constructor(menuName: String, text: String) : this(DEFAULT_MARGIN_TOP, "",
        MenuPanelItemRoot.Span(DEFAULT_TITLE_SPAN), menuName, text, true, "")

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
        get() = R.layout.layout_menu_panel_normal

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
        val arrowIconView = menuView.findViewById<ImageView>(ID_IMAGE_VIEW_ARROW)
        val arrowTextView = menuView.findViewById<TextView>(ID_TEXT_VIEW_ARROW)
        menuTextView.text = text
        if (showArrowIcon) {
            arrowIconView.visibility = View.VISIBLE
        } else {
            arrowIconView.visibility = View.INVISIBLE
        }
        arrowTextView.text = arrowText
    }

    companion object {
        @JvmField
        val ID_TEXT_VIEW_MENU: Int = R.id.tv_menu_panel_normal_menu
        @JvmField
        val ID_IMAGE_VIEW_ARROW: Int = R.id.iv_menu_panel_normal_arrow
        @JvmField
        val ID_TEXT_VIEW_ARROW: Int = R.id.tv_menu_panel_normal_arrow

        fun group(title: String, vararg items: Pair<String, String>) =
            MenuPanelItemGroup(
                title = title,
                items = items.map { NormalMenuPanelItem(it.first, it.second) }.toTypedArray()
            )
    }
}