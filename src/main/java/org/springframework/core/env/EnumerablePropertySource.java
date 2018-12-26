package org.springframework.core.env;

public abstract class EnumerablePropertySource<T> extends PropertySource<T> {
    public EnumerablePropertySource(String name) {
        super(name);
    }

    public EnumerablePropertySource(String name, T source) {
        super(name, source);
    }
    public abstract String[] getProperyNames();
}
