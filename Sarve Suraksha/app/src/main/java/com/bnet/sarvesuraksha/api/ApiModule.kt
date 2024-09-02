package com.bnet.sarvesuraksha.api

enum class ApiModule(private val port: Int) {
    MODULE1(3000),
    MODULE2(3001),
    MODULE3(3002),
    MODULE9(3009);

    val baseUrl: String
        get() = "http://178.162.1.15:$port/api/v1/"
}
