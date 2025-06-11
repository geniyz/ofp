package site.geniyz.ofp.cntxt

import io.ktor.server.routing.RoutingCall
import io.ktor.util.toMap

/**
 * Адаптер контекста
 * формирует контекст на основе заголовков http
 */
class ContextAdapter(
    private val params: MutableMap<String, Any?> = mutableMapOf()
): Context(params) {
    constructor(call: RoutingCall): this(
        call.request.headers.toMap().toMutableMap()
    )
}
