package com.xpinjection.springboot.init;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Getter @Setter
@ConfigurationProperties(prefix = "library")
public class LibrarySettings {

    private int size;


    /*@Value("${library.no.such.value: default message}")
    private String defaultMessage;

    private List<String> list;

    @Value("#{${db.connections}}")
    private Map<String, String> dbParamsMap;*/
}
