package com.kestats.api.utils;

import java.lang.reflect.Method;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.stereotype.Component;

@Component
public class CustomIdGenerator extends SequenceStyleGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner) throws HibernateException {
        UUID id = null;
        try {
            Method getIdMethod = owner.getClass().getMethod("getId");
            Object value = getIdMethod.invoke(owner);
            if (value instanceof UUID) {
                id = (UUID) value;
            }
        } catch (Exception e) {
            // Log or ignore if no getId() method is available
        }
        return id != null ? id : UUID.randomUUID();
    }

    @Override
    public boolean allowAssignedIdentifiers() {
        return true;
    }
}
