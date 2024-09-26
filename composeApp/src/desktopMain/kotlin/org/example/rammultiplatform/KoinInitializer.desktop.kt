package org.example.rammultiplatform

import appModule
import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(httpClientEngine, appModule, viewModelModule)
        }
    }
}