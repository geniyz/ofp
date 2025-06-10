package site.geniyz.ofp.rule

import site.geniyz.ofp.core.*

/**
 * «Правило»
 */
interface IRule: Executable {
    /**
     * некоторый контекст
     * который может быть использован в правиле
     */
    val context: UObject

    /**
     * параметры
     */
    val params: UObject

    override fun execute(): Boolean
}
