package org.springframework.beans;

import org.springframework.core.NestedRuntimeException;

public abstract class BeansException extends NestedRuntimeException {
    public BeansException(String message) {
        super(message);
    }
    public BeansException(String msg,Throwable cause){
        super(msg,cause);
    }
}
