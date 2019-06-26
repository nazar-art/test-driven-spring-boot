package com.xpinjection.springboot.autoconfiguration.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * @author Nazar Lelyak.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "com.justgifit")
public class JustGifItProperties {

    /**
     * The location of the animated gifs.
     */
    private File gifLocation;

    /**
     * Whether or not to optimize web filters.
     */
    private Boolean optimize;
}
