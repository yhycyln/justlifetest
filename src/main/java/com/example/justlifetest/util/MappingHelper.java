package com.example.justlifetest.util;

public class MappingHelper {

    private MappingHelper() {}

    public static <T, D> D map(T source, Class<D> targetClass) {
        try {
            D target = targetClass.getConstructor().newInstance();
            ModelMapperUtil.getInstance().getMapper().map(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Mapping failed", e);
        }

    }
}

