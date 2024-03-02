package com.momiji.gateway.handler

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.momiji.api.common.model.ResponseStatus
import com.momiji.api.common.model.SimpleResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice(basePackages = ["com.momiji.gateway.controller"])
class ControllerExceptionHandler {

    @ExceptionHandler(value = [UnrecognizedPropertyException::class])
    fun unrecognizedProperty(ex: UnrecognizedPropertyException): ResponseEntity<SimpleResponse> {
        return ResponseEntity.badRequest().body(
            SimpleResponse(
                status = ResponseStatus.BAD_REQUEST,
                errorMessage = ex.originalMessage
            )
        )
    }
}
