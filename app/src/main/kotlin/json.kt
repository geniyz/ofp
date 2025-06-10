package site.geniyz.ofp

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    prettyPrint        = true
    isLenient          = true
    allowTrailingComma = true
    ignoreUnknownKeys  = true
    encodeDefaults     = true
    coerceInputValues  = true
    explicitNulls      = true
}
