import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.Js

actual fun getEngineFactory(): HttpClientEngineFactory<HttpClientEngineConfig> {
    return Js
}