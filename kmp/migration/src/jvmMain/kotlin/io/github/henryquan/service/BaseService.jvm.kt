import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual fun getEngineFactory(): HttpClientEngineFactory<HttpClientEngineConfig> {
    return OkHttp
}
