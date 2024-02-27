package com.momiji.gateway.handler

import com.momiji.api.common.model.ResponseStatus
import com.momiji.api.common.model.SimpleResponse
import com.momiji.gateway.exception.GatewayException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice(basePackages = ["com.momiji.gateway.controller"])
class GatewayExceptionHandler {

    @ExceptionHandler(value = [GatewayException::class])
    fun gatewayExceptionHandler(ex: GatewayException): ResponseEntity<SimpleResponse> {
        return ResponseEntity.badRequest().body(
            SimpleResponse(
                status = ResponseStatus.BAD_REQUEST,
                errorMessage = ex.message
            )
        )
    }
}
