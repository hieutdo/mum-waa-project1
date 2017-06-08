package edu.mum.gf.workaround;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import java.util.HashSet;
import java.util.Set;

public class CDIBeanManager {
    @SuppressWarnings("unchecked")
    static public <T> T getBean(Class<T> clazz) {
        BeanManager beanManager = null;
        try {
            beanManager = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
        } catch (Exception ex) {
            throw new RuntimeException("Could not look up BeanManager", ex);
        }
        Bean<?> bean = beanManager.resolve(beanManager.getBeans(clazz));
        if (bean == null) {
            return null;
        }
        return (T) beanManager.getReference(bean, clazz, beanManager.createCreationalContext(bean));
    }

    static public <T> Set<T> getBeans(Class<T> clazz) {
        BeanManager beanManager = null;
        try {
            beanManager = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
        } catch (Exception ex) {
            throw new RuntimeException("Could not look up BeanManager", ex);
        }
        Set<Bean<?>> beans = beanManager.getBeans(clazz);
        Set<T> instances = new HashSet<T>();
        for (Bean<?> bean : beans) {
            CreationalContext<?> creationalContext = beanManager.createCreationalContext(bean);
            @SuppressWarnings("unchecked")
            T instance = (T) beanManager.getReference(bean, clazz, creationalContext);
            instances.add(instance);
        }
        return instances;
    }

    public Object newJNDIInstance(String jndiName) {
        try {
            //Class<?> clazz = Class.forName(jndiName.substring(jndiName.indexOf("!") + 1));
            //return getBean(clazz);
            return new InitialContext().lookup(jndiName);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}
