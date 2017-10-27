package com.qinhetea.api.controller

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalControllerExceptionHandler {

  @ResponseStatus(HttpStatus.CONFLICT, reason = "Conflict")
  @ExceptionHandler(DataIntegrityViolationException::class)
  fun handleConflict() = Unit

}
