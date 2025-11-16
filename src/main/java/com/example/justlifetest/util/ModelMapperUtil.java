package com.example.justlifetest.util;

import org.modelmapper.ModelMapper;

public class ModelMapperUtil {

    private static ModelMapperUtil instance;

    private ModelMapper mapper;

    private ModelMapperUtil() {
        mapper = new ModelMapper();
    }

    public static ModelMapperUtil getInstance() {
        if (ModelMapperUtil.instance == null) {
            ModelMapperUtil.instance = new ModelMapperUtil();
        }
        return ModelMapperUtil.instance;
    }

    public ModelMapper getMapper() {
        return mapper;
    }
}
