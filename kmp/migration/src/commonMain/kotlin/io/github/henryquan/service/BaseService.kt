import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect fun getEngineFactory(): HttpClientEngineFactory<HttpClientEngineConfig>

abstract class BaseService {
    abstract val baseUrl: String

    open val client = HttpClient(getEngineFactory()) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
    }

    suspend inline fun <reified T> getObject(
        path: String,
        params: Map<String, String> = emptyMap()
    ): T {
        val response = client.get("$baseUrl$path") {
            params.forEach { (key, value) ->
                parameter(key, value)
            }
        }
        return response.body<T>()
    }
}
