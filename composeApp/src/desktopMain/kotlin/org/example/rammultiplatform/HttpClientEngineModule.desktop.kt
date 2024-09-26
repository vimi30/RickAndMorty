package org.example.rammultiplatform

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.dsl.module

actual val httpClientEngine: Module = module {
    single<HttpClientEngine> {
        CIO.create()
    }
}