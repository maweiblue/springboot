package org.springframework.beans;

public class FatalBeanException extends BeansException {
    public FatalBeanException(String message) {
        super(message);
    }

    public FatalBeanException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
