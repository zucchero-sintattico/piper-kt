package piperkt.common.events

import piperkt.common.ddd.ValueObject
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
interface DomainEvent : ValueObject {
    val type: String get() = this::class.simpleName!!
}
