package org.springframework.beans.factory;

import org.springframework.beans.FatalBeanException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;
import sun.misc.ThreadGroupUtils;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BeanCreationException extends FatalBeanException {
    private final String beanName;
    private final String resourceDescription;
    private List<Throwable> relatedCauses;

    public BeanCreationException(String message) {
        super(message);
        this.beanName=null;
        this.resourceDescription=null;
    }

    public BeanCreationException(String beanName, String msg) {
        super("Error creating bean with name '" + beanName + "': " + msg);
        this.beanName=beanName;
        this.resourceDescription=null;
    }

    public BeanCreationException(String beanName,String msg,Throwable cause){
        this(beanName,msg);
        initCause(cause);
    }

    public BeanCreationException(String resourceDescription,String beanName,String msg){
        super("Error creating bean with name '" + beanName + "'" +
                (resourceDescription != null ? " defined in " + resourceDescription : "") + ": " + msg);
        this.resourceDescription=resourceDescription;
        this.beanName=beanName;
        this.relatedCauses=null;
    }
    public BeanCreationException(String resourceDescription,String beanName,String msg,
    Throwable cause){
        this(resourceDescription,beanName,msg);
        initCause(cause);
    }

    public String getBeanName() {
        return beanName;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }
    public void addRelatedCause(Throwable ex){
        if(this.relatedCauses==null){
            this.relatedCauses=new ArrayList<>();
        }
        this.relatedCauses.add(ex);
    }
    public Throwable[] getRelatedCauses(){
        if(this.relatedCauses==null){
            return null;
        }
        return this.relatedCauses.toArray(new Throwable[0]);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder(super.toString());
        if(this.relatedCauses!=null){
            for (Throwable relatedCause:this.relatedCauses){
                sb.append("\nRelated cause: ");
                sb.append(relatedCause);
            }
        }
        return sb.toString();
    }

    public void printStackTrace(PrintStream ps){
        synchronized (ps){
            super.printStackTrace(ps);
            if(this.relatedCauses!=null){
                for (Throwable relatedCause : this.relatedCauses) {
                    ps.println("Related cause:");
                    relatedCause.printStackTrace(ps);
                }
            }
        }
    }
    @Override
    public void printStackTrace(PrintWriter pw) {
        synchronized (pw) {
            super.printStackTrace(pw);
            if (this.relatedCauses != null) {
                for (Throwable relatedCause : this.relatedCauses) {
                    pw.println("Related cause:");
                    relatedCause.printStackTrace(pw);
                }
            }
        }
    }

    @Override
    public boolean contains(@Nullable Class<?> exClass) {
        if (super.contains(exClass)) {
            return true;
        }
        if (this.relatedCauses != null) {
            for (Throwable relatedCause : this.relatedCauses) {
                if (relatedCause instanceof NestedRuntimeException &&
                        ((NestedRuntimeException) relatedCause).contains(exClass)) {
                    return true;
                }
            }
        }
        return false;
    }

}
