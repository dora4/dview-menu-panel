package dora.widget.panel

interface MenuPanelItemRoot {

    /**
     * 菜单的标题。
     *
     * @return
     */
    var title: String?

    fun hasTitle(): Boolean

    /**
     * 获取标题四周的间距。
     *
     * @return
     */
    fun getTitleSpan(): Span

    fun setTitleSpan(titleSpan: Span)

    /**
     * 菜单的上边距。
     *
     * @return
     */
    var marginTop: Int

    class Span {
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0

        constructor()

        /**
         * 根据水平间距和垂直间距设置四周的间距，常用。
         *
         * @param horizontal
         * @param vertical
         */
        constructor(horizontal: Int, vertical: Int) : this(
            horizontal,
            vertical,
            horizontal,
            vertical
        )

        /**
         * 同时设置水平间距和垂直间距。
         *
         * @param size
         */
        constructor(size: Int) : this(size, size)

        constructor(left: Int, top: Int, right: Int, bottom: Int) {
            this.left = left
            this.top = top
            this.right = right
            this.bottom = bottom
        }
    }

    companion object {
        var DEFAULT_MARGIN_TOP = 2
        var DEFAULT_TITLE_SPAN = 24
    }
}