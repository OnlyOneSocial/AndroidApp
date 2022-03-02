package dev.syorito_hatsuki.onlyone.di

import dev.syorito_hatsuki.onlyone.api.OnlyOneApi
import dev.syorito_hatsuki.onlyone.api.OnlyOneApiImpl
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
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