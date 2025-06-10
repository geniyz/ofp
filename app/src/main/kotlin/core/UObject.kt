package site.geniyz.ofp.core

/**
 * Универсальный объект
 */
interface UObject{
    operator fun get(k: String): Any?
    operator fun set(k: String, value: Any?)
}
