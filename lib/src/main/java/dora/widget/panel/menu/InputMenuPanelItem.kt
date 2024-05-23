package dora.widget.panel.menu

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import dora.widget.panel.R
import dora.widget.panel.MenuPanelItem
import dora.widget.panel.MenuPanelItemRoot

class InputMenuPanelItem
/**
 *
 * @param marginTop
 * @param title
 * @param titleSpan
 * @param menuName 提示信息
 * @param content 文本框输入的内容
 * @param watcher
 */(
    override var marginTop: Int,
    override var title: String?,
    private var titleSpan: MenuPanelItemRoot.Span,
    override val menuName: String?,
    private val content: String,
    private val watcher: ContentWatcher?
) : MenuPanelItem {
    constructor(menuName: String) : this(1, "", MenuPanelItemRoot.Span(), menuName, "", null)
    constructor(menuName: String, content: String) : this(
        1,
        "",
        MenuPanelItemRoot.Span(),
        menuName,
        content,
        null
    )

    constructor(menuName: String, content: String, watcher: ContentWatcher?) : this(
        1,
        "",
        MenuPanelItemRoot.Span(),
        menuName,
        content,
        watcher
    )

    override fun initData(menuView: View) {
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.topMargin = marginTop
        menuView.layoutParams = lp
        val editText = menuView.findViewById<EditText>(R.id.et_menu_panel_input)
        editText.hint = menuName
        if (!TextUtils.isEmpty(content)) {
            editText.setText(content)
            editText.setSelection(content.length)
        }
        if (watcher != null) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    watcher.onContentChanged(this@InputMenuPanelItem, s.toString())
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
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

    override val layoutId: Int
        get() = R.layout.layout_menu_panel_input

    override fun inflateView(context: Context): View {
        return LayoutInflater.from(context).inflate(layoutId, null)
    }

    interface ContentWatcher {
        fun onContentChanged(item: InputMenuPanelItem?, content: String?)
    }

    companion object {
        @JvmField
        val ID_EDIT_TEXT_INPUT: Int = R.id.et_menu_panel_input
    }
}