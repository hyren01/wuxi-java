package com.jn.primiary.beyondsoft.util;

import com.jn.primiary.beyondsoft.dao.CheckRepository;
import com.jn.primiary.beyondsoft.dao.ModuleRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUtil implements ApplicationContextAware {
    public static CheckRepository fieldCheckRepository;
    public static ModuleRepository schemaModuleRepository;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        fieldCheckRepository = applicationContext.getBean(CheckRepository.class);
        schemaModuleRepository = applicationContext.getBean(ModuleRepository.class);
    }

}
