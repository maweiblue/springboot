package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

public interface BeanFactory {
    String FACTORY_BEAN_PREFIX="&";
    Object getBean(String name) throws BeansException;
    <T> T getBean(String name,Class<T> requiredType) throws BeansException;
    Object getBean(String name,Object...  args) throws BeansException;
    <T> T getBean(Class<T> requiredType) throws BeansException;
    <T> T getBean(Class<T> requiredType,Object... args) throws BeansException;
//    <T> ObjectProvider<T>
    <T> ObjectProvider<T>  getBeanProvider(Class<T> requiredType);
//    <T> ObjectProvider<T> getBeanProvider(ResolvaleType)
}
