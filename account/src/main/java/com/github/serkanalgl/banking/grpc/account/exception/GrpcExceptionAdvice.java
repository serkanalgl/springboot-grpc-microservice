package com.github.serkanalgl.banking.grpc.account.exception;

import io.grpc.Status;
import io.grpc.StatusException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
@Slf4j
public class GrpcExceptionAdvice {

    @GrpcExceptionHandler(AccountNotFoundException.class)
    public StatusException handleAccountNotFoundException(AccountNotFoundException ex) {
        var status = Status.NOT_FOUND.withDescription(ex.getLocalizedMessage()).withCause(ex);
        log.error("(GrpcExceptionAdvice) AccountNotFoundException: ", ex);
        return status.asException();
    }

    @GrpcExceptionHandler(IllegalArgumentException.class)
    public StatusException handleIllegalArgumentException(IllegalArgumentException ex) {
        var status = Status.INVALID_ARGUMENT.withDescription(ex.getLocalizedMessage()).withCause(ex);
        log.error("(GrpcExceptionAdvice) IllegalArgumentException: ", ex);
        return status.asException();
    }

    @GrpcExceptionHandler(Exception.class)
    public StatusException handleException(Exception ex) {
        var status = Status.INTERNAL.withDescription(ex.getLocalizedMessage()).withCause(ex);
        log.error("(GrpcExceptionAdvice) Exception: ", ex);
        return status.asException();
    }

}
