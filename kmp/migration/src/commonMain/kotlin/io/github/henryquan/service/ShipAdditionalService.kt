package io.github.henryquan.service

import BaseService
import getEngineFactory
import io.github.henryquan.ShipAdditionalMap
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.json.Json
import kotlin.js.JsExport

// @JsExport won't work here because of the suspend function below,
// we have to wait for Kotlin Multiplatform to support this in the future
// because Kotlin suspend is not a JS Promise
// See ShipAdditionalServiceJS
open class ShipAdditionalService : BaseService() {
    override val baseUrl: String =
        "https://raw.githubusercontent.com/wowsinfo/WoWs-Info-Seven/refs/heads/API/json/ship_additional.json"

    /**
     * WarGaming is returning a Text/HTML response instead of JSON.
     * This introduces an exception, NoTransformationFoundException
     * See https://stackoverflow.com/q/65105118
     */
    override val client = HttpClient(getEngineFactory()) {
        install(ContentNegotiation) {
            register(
                // so GitHub is treating it as text/plain, since it is not an API
                ContentType.Any, KotlinxSerializationConverter(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            )
        }
    }

    /**
     * Prepare common parameters for WarGaming API requests,
     * such as the application ID and language.
     * @param params The parameters to prepare.
     * @return The parameters with the application ID and language added.
     */
    private fun prepareParams(params: Map<String, String>): Map<String, String> {
        return params
    }

    suspend fun getShipAdditional(): ShipAdditionalMap {
        // usually, need to provide the path and provide additional params
        return getObject("", prepareParams(mapOf()))
    }
}
