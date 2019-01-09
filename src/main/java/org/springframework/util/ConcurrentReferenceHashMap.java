package org.springframework.util;

import javax.swing.text.Segment;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ConcurrentReferenceHashMap<K,V> extends AbstractMap<K,V> implements ConcurrentMap<K,V> {
    private static final int DEFAULT_INITIAL_CAPACITY=16;
    private static final float DEFAULT_LOAD_FACTOR=0.75f;
    private static final int DEFAULT_CONCURRENCY_LEVEL=16;
    private static final ReferenceType DEFAULT_REFERENCE_TYPE=ReferenceType.SOFT;
    private static final int MAXIMUM_CONCURRENCY_LEVEL=1<<16;
    private static final int MAXIMUM_SEGMENT_SIZE =1<<30;
//    private final Segment
    public enum  ReferenceType{
        SOFT,
        WEAK
    }
    protected final class Segment extends ReentrantLock{
//        private final Reference
    }

    protected class ReferenceManager{
//        private final Ref
    }
    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {

    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {

    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return null;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return null;
    }
}
