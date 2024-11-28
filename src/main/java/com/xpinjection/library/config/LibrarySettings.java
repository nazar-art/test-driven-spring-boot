package com.xpinjection.library.config;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "library")
public class LibrarySettings {
    @Min(0)
    private int size;
}
