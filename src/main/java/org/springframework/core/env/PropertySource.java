package org.springframework.core.env;

/**
 * 抽象类，作为key,value的基础类
 * @param <T>
 */
public abstract class PropertySource<T> {
//    protected  final
    protected final String name;
    protected final T source;

    public PropertySource(String name, T source) {
        this.name = name;
        this.source = source;
    }

    public PropertySource(String name) {
        this(name,(T)new Object());
    }

    public String getName() {
        return name;
    }

    public T getSource() {
        return source;
    }
    public boolean containsProperty(String name){
        return (getProperty(name)!=null);
    }

    protected abstract Object getProperty(String name);

    @Override
    public boolean equals(Object obj) {
     return  (this==obj||(obj instanceof PropertySource));
    }

    /**
     * 这个Ps啥也不干，只是用作，一般后面会被替代
     */
    public static class StubPropertySource extends PropertySource<Object>{

        public StubPropertySource(String name) {
            super(name, new Object());
        }

        protected Object getProperty(String name) {
            return null;
        }
    }

    static class ComparisonPropertySource extends StubPropertySource{
        private static final String USAGE_ERROR =
                "ComparisonPropertySource instances are for use with collection comparison only";

        public ComparisonPropertySource(String name) {
            super(name);
        }

        @Override
        public Object getSource() {
           throw  new UnsupportedOperationException(USAGE_ERROR);
        }

        @Override
        public boolean containsProperty(String name) {
            throw new UnsupportedOperationException(USAGE_ERROR);
        }
    }
}
