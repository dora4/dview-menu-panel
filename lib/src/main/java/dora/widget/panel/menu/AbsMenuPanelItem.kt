package dora.widget.panel.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import dora.widget.panel.IMenu
import dora.widget.panel.MenuPanelItem
import dora.widget.panel.MenuPanelItemRoot

/**
 * 可用它自定义面板菜单。
 *
 * @param <T> 数据实体类
</T> */
abstract class AbsMenuPanelItem<T : IMenu>(
    override var marginTop: Int,
    private var titleSpan: MenuPanelItemRoot.Span,
    protected var menu: T
) : MenuPanelItem {

    constructor(menu: T) : this(1, MenuPanelItemRoot.Span(), menu)

    override fun inflateView(context: Context): View {
        val view = LayoutInflater.from(context).inflate(layoutId, null)
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.topMargin = marginTop
        view.layoutParams = lp
        return view
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

    override val menuName: String?
        get() = menu.menuName
}