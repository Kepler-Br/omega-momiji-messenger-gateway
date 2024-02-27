package com.momiji.gateway.exception

open class GatewayException(message: String?, thorwable: Throwable?) :
    RuntimeException(message, thorwable) {

    constructor(message: String) : this(message, null)

    constructor(throwable: Throwable) : this(null, throwable)
}
