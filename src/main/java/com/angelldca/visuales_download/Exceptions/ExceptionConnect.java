package com.angelldca.visuales_download.Exceptions;

import org.springframework.shell.command.CommandExceptionResolver;
import org.springframework.shell.command.CommandHandlingResult;

public class ExceptionConnect extends RuntimeException {

    private final int code;

   public ExceptionConnect(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}