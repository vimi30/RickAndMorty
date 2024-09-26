package org.example.rammultiplatform

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.math.sin

actual val httpClientEngine: Module = module {
    single<HttpClientEngine> {
        CIO.create()
    }
}