package dora.widget.panel.menu

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import dora.widget.panel.R
import dora.widget.panel.MenuPanelItem
import dora.widget.panel.MenuPanelItemRoot

class ButtonMenuPanelItem(
    override var marginTop: Int,
    override var title: String?,
    private var titleSpan: MenuPanelItemRoot.Span,
    override val menuName: String?,
    private val text: String?,
    private val textColor: Int,
) : MenuPanelItem {

    constructor(menuName: String, text: String?, textColor: Int = Color.BLACK) : this(
        1,
        "",
        MenuPanelItemRoot.Span(),
        menuName,
        text,
        textColor
    )

    constructor(marginTop: Int, menuName: String, text: String?, textColor: Int = Color.BLACK) : this(
        marginTop,
        "",
        MenuPanelItemRoot.Span(),
        menuName,
        text,
        textColor
    )

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
        val menuTextView = menuView.findViewById<TextView>(R.id.tv_menu_panel_button_menu)
        menuTextView.text = text
        menuTextView.setTextColor(textColor)
    }

    companion object {

        @JvmField
        val ID_TEXT_VIEW_MENU: Int = R.id.tv_menu_panel_button_menu
    }
}