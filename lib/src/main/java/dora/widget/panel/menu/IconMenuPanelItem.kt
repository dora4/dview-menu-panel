package dora.widget.panel.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import dora.widget.panel.R
import dora.widget.panel.MenuPanelItem
import dora.widget.panel.MenuPanelItemRoot

class IconMenuPanelItem(
    override var marginTop: Int,
    override var title: String?,
    private var titleSpan: MenuPanelItemRoot.Span,
    override val menuName: String?,
    @field:DrawableRes private val mIconRes: Int,
    private val text: String?,
    private val showArrowIcon: Boolean,
    private val arrowText: String?
) : MenuPanelItem {

    /**
     * 不显示角落图标，也不显示角落文本。
     *
     * @param menuName
     */
    constructor(menuName: String, iconRes: Int, text: String?) : this(
        1,
        "",
        MenuPanelItemRoot.Span(),
        menuName,
        iconRes,
        text,
        true,
        ""
    )

    constructor(menuName: String, iconRes: Int, text: String?, showArrowIcon: Boolean) : this(
        1,
        "",
        MenuPanelItemRoot.Span(),
        menuName,
        iconRes,
        text,
        showArrowIcon,
        ""
    )

    constructor(marginTop: Int, menuName: String, iconRes: Int, text: String?) : this(
        marginTop,
        "",
        MenuPanelItemRoot.Span(),
        menuName,
        iconRes,
        text,
        true,
        ""
    )

    constructor(
        marginTop: Int,
        menuName: String,
        iconRes: Int,
        text: String?,
        showArrowIcon: Boolean
    ) : this(marginTop, "", MenuPanelItemRoot.Span(), menuName, iconRes, text, showArrowIcon, "")

    /**
     * 显示角落图标，也显示角落文本。
     *
     * @param menuName
     * @param arrowText
     */
    constructor(menuName: String, iconRes: Int, text: String?, arrowText: String?) : this(
        1,
        "",
        MenuPanelItemRoot.Span(),
        menuName,
        iconRes,
        text,
        true,
        arrowText
    )

    constructor(
        menuName: String,
        iconRes: Int,
        text: String?,
        showArrowIcon: Boolean,
        arrowText: String?
    ) : this(1, "", MenuPanelItemRoot.Span(), menuName, iconRes, text, showArrowIcon, arrowText)

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
        get() = R.layout.layout_menu_panel_icon

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
        val menuIconView = menuView.findViewById<ImageView>(ID_IMAGE_VIEW_ICON)
        val menuTextView = menuView.findViewById<TextView>(ID_TEXT_VIEW_MENU)
        val arrowIconView = menuView.findViewById<ImageView>(ID_IMAGE_VIEW_ARROW)
        val arrowTextView = menuView.findViewById<TextView>(ID_TEXT_VIEW_ARROW)
        menuIconView.setImageResource(mIconRes)
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
        val ID_IMAGE_VIEW_ICON: Int = R.id.iv_menu_panel_icon
        @JvmField
        val ID_TEXT_VIEW_MENU: Int = R.id.tv_menu_panel_normal_menu
        @JvmField
        val ID_IMAGE_VIEW_ARROW: Int = R.id.iv_menu_panel_normal_arrow
        @JvmField
        val ID_TEXT_VIEW_ARROW: Int = R.id.tv_menu_panel_normal_arrow
    }
}