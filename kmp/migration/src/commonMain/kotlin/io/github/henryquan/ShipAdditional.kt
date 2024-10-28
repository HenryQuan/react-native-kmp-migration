package io.github.henryquan

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

typealias ShipAdditionalMap = Map<String, ShipAdditional>

@JsExport
@Serializable
data class ShipAdditional (
    val alphaPiercingHE: Double,
    val sigma: Double,
    val consumables: List<List<ShipConsumable>>
)

@JsExport
@Serializable
data class ShipConsumable (
    val name: String,
    val type: String
)
