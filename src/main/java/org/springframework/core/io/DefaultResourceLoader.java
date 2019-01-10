package org.springframework.core.io;

import org.springframework.util.ClassUtils;

import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultResourceLoader implements ResourceLoader {
    private ClassLoader classLoader;
    private final Set<ProtocolResolver> protocolResolvers=new LinkedHashSet<>(4);
    public DefaultResourceLoader(){
//        this.classLoader= ClassUtils
    }
    @Override
    public Resource getResource(String location) {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }
}
