package org.springframework.core.io.support;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

public interface ResourcePatternResolver extends ResourceLoader {
    String CLASSPATH_ALL_URL_PREFIX="classpath*:";
    Resource[] getResources(String locationPattern) throws IOException;
}
