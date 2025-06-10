package site.geniyz.ofp.cntxt

import site.geniyz.ofp.core.UObject

/**
 * «Контекст» выполнения правил-команд
 */
open class Context(
    private val initMap: Map<String, Any?>
): UObject {

    constructor(vararg p: Pair<String, Any?>?) : this(
        buildMap {
            p
                .filterNotNull()
                .forEach { pair -> set(pair.first, pair.second) }
        }
    )
    constructor(): this(emptyMap())

    var map = initMap.toMutableMap()

    private fun getDeep(m: Map<*,*>, k: String):Any? =
        if(k.isBlank()){ null }else {
            if (k.contains(".")) {
                (m[k.substringBefore(".")] as Map<*, *>?)?.let {
                    getDeep(it, k.substringAfter("."))
                }
            } else {
                m[k]
            }
        }

    private fun setDeep(m: MutableMap<String, Any?>?, k: String, v: Any?): MutableMap<String, Any?> {
        val r = m ?: mutableMapOf<String, Any?>()
        return if (k.isBlank()) {
            r
        } else {
            if (k.contains(".")) {
                r[k.substringBefore(".")] = setDeep((r[k.substringBefore(".")] as MutableMap<String, Any?>?) ?: mutableMapOf(), k.substringAfter("."), v)
            } else {
                r[k] = v
            }
            r
        }
    }

    override fun get(k: String) = getDeep(map, k) // map[k]
    override fun set(k: String, value: Any?){
        setDeep(map, k, value)
    }

    override fun toString()= map.map{ "${it.key} → ${it.value}" }.joinToString("; ")

    companion object {
        val NONE = Context()
    }
}
