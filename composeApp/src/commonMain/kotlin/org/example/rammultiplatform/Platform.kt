package org.example.rammultiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform