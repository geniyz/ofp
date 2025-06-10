package site.geniyz.ofp.core

class EmptyUObject: UObject {
    override fun get(k: String)= null
    override fun set(k: String, value: Any?) {}
}
