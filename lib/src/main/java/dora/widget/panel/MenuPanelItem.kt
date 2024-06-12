package dora.widget.panel

import android.content.Context
import android.text.TextUtils
import android.view.View
import java.util.UUID

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

    companion object {

        /**
         * 虽然用它生成了默认的menuName，但是不建议你使用，最好使用业务的命名覆盖它。
         */
        fun generateMenuName(prefix: String? = null) : String {
            val uuid = UUID.randomUUID().toString()
            return if (!TextUtils.isEmpty(prefix)) {
                prefix + "-" + uuid.replace("-".toRegex(), "")
            } else {
                uuid.replace("-".toRegex(), "")
            }
        }
    }
}