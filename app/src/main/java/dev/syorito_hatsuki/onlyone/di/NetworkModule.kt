package dev.syorito_hatsuki.onlyone.di

import dev.syorito_hatsuki.onlyone.api.OnlyOneApi
import dev.syorito_hatsuki.onlyone.api.OnlyOneApiImpl
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.cache.*
import io.ktor.client.features.cache.storage.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import org.koin.dsl.module

val networkModule = module {
    single { provideKtorClient() }
    single { provideOnlyOneApiService(get()) }
}

private fun provideKtorClient(): HttpClient {
    return HttpClient(Android) {
        expectSuccess = true
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
        install(HttpCache)

        defaultRequest {
            url.protocol = URLProtocol.HTTPS
            url.host = "only-one.su/api"
            followRedirects = true
        }
    }
}

private fun provideOnlyOneApiService(httpClient: HttpClient): OnlyOneApi {
    return OnlyOneApiImpl(httpClient)
}