package io.github.henryquan

import kotlin.js.JsExport

/**
 * Just some simple use case
 */
@JsExport
class FilterUseCase(
    private val additionalMap: ShipAdditionalMap,
) {
    fun filterSigma(sigma: Double): List<String> {
        return additionalMap.mapNotNull { (key, value) ->
            if (value.sigma >= sigma) key else null
        }
    }

    fun filterHEPen(pen: Double): List<String> {
        return additionalMap.mapNotNull { (key, value) ->
            if (value.alphaPiercingHE >= pen) key else null
        }
    }
}
