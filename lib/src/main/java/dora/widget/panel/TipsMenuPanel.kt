package dora.widget.panel

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

/**
 * 自动给最后加一行提示信息，如共有几条记录的菜单面板。
 */
class TipsMenuPanel : MenuPanel {

    private var tips: String? = ""
    private var tipsColor = -0x666667
    private var tipsView: TextView? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setEmptyTips(): TipsMenuPanel {
        setTips("")
        return this
    }

    fun setTips(tips: String): TipsMenuPanel {
        this.tips = tips
        return this
    }

    fun setTipsColor(color: Int): TipsMenuPanel {
        tipsColor = color
        return this
    }

    override fun updatePanel() {
        tipsView?.apply {
            container.removeView(this)
        }
        if (tips != null && tips!!.isNotEmpty()) {
            tipsView = TextView(context)
            val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin = dp2px(context, 5f)
            lp.bottomMargin = dp2px(context, 5f)
            tipsView!!.gravity = Gravity.CENTER_HORIZONTAL
            tipsView!!.setTextColor(tipsColor)
            tipsView!!.layoutParams = lp
            tipsView!!.text = tips
            // 增加了底部的tips
            container.addView(tipsView)
        }
        super.updatePanel()
    }

    private fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, context.resources.displayMetrics
        ).toInt()
    }
}