package dora.widget.panel

import android.content.Context
import android.view.View

/**
 * 实现它来自定义面板的菜单，你也可以使用[dora.widget.panel.menu.AbsMenuPanelItem]。
 */
interface MenuPanelItem : MenuPanelItemRoot {

    val layoutId: Int
    fun inflateView(context: Context): View

    /**
     * 每个菜单都有独一无二的menuName。
     */
    val menuName: String?
    fun initData(menuView: View)
}