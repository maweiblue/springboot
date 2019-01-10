package org.springframework.core.io;

public interface ProtocolResolver {
    Resource resolve(String location,ResourceLoader resourceLoader);
}
