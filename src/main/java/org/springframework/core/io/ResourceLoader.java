package org.springframework.core.io;

import org.springframework.util.ResourceUtils;

public interface ResourceLoader {
    String CLASSPATH_URL_PREFIX= ResourceUtils.CLASSPATH_URL_PREFIX;
    Resource getResource(String location);
    ClassLoader getClassLoader();

}
