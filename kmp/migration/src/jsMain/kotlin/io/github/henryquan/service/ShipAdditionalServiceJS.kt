package io.github.henryquan.service

import io.github.henryquan.ShipAdditionalMap
import promiseScope
import kotlin.js.Promise

// This isn't the nicest solution, @JSName can update the service name
// Functions will need to add something to not conflict with the common service
// This is a migration, so in the future all jsMain code will be removed,
// but this will work until then, also see: https://github.com/ForteScarlet/kotlin-suspend-transform-compiler-plugin
// https://github.com/wowsinfo/PersonalRatingService/issues/1

@JsExport
@JsName("ShipAdditionalService")
class ShipAdditionalServiceJS: ShipAdditionalService() {

    fun getShipAdditionalPromise(): Promise<ShipAdditionalMap> = promiseScope {
        getShipAdditional()
    }
}
