package dora.widget.panel

interface IMenu {
    
    /**
     * 注意：在同一MenuPanel中确保唯一性，且不要使用空字符串，因为删除的时候是根据它删除的。
     *
     * @return
     */
    val menuName: String?
}