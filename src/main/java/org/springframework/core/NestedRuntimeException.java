package org.springframework.core;

import org.springframework.lang.Nullable;

public abstract class NestedRuntimeException  extends RuntimeException{
    static {
        NestedRuntimeException.class.getName();
    }

    public NestedRuntimeException(String message) {
        super(message);
    }
    public NestedRuntimeException(@Nullable String msg,@Nullable Throwable cause){
        super(msg,cause);
    }

    @Override
    public String getMessage() {
        return NestedExceptionUtils.BuildMessage(super.getMessage(),getCause());
    }
    public Throwable getRootCause(){
        return NestedExceptionUtils.getRootCause(this);
    }

    public Throwable getMostSpecificCause(){
        Throwable rootCause=getRootCause();
        return (rootCause!=null?rootCause:this);
    }
    public boolean contains(Class<?> exType){
        if(exType==null){
            return false;
        }
        if(exType.isInstance(this)){
            return true;
        }
        Throwable cause=getCause();
        if(cause==this){
            return false;
        }
        if(cause instanceof NestedRuntimeException){
            return ((NestedRuntimeException)cause).contains(exType);
        }else{
            while (cause!=null){
                if(exType.isInstance(cause)){
                    return true;
                }
                if(cause.getCause()==cause){
                    break;
                }
                cause=cause.getCause();
            }
            return false;
        }

    }
}
