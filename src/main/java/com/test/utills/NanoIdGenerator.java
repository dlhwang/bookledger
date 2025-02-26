package com.test.utills;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serial;

public class NanoIdGenerator implements IdentifierGenerator {
    @Serial
    private static final long serialVersionUID = 5950315803265882704L;

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return NanoIdUtils.randomNanoId();
    }
}
