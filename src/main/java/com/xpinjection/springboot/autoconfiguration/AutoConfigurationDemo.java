package com.xpinjection.springboot.autoconfiguration;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.xpinjection.springboot.autoconfiguration.config.JustGifItProperties;
import com.xpinjection.springboot.autoconfiguration.services.ConverterService;
import com.xpinjection.springboot.autoconfiguration.services.GifEncoderService;
import com.xpinjection.springboot.autoconfiguration.services.VideoDecoderService;
import lombok.AllArgsConstructor;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.io.File;

/**
 * @author Nazar Lelyak.
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(JustGifItProperties.class)
@ConditionalOnClass({FFmpegFrameGrabber.class, AnimatedGifEncoder.class})
public class AutoConfigurationDemo {

//    @Value("${multipart.location}/gif/")
//    private String gifLocation;

    private JustGifItProperties properties;

    @ConditionalOnProperty(prefix = "com.justgifit", name = "create-result-dir)")
    private boolean createResultDir() {
//        File gifFolder = new File(gifLocation);
        File gifFolder = properties.getGifLocation();
        if (!gifFolder.exists()) {
            gifFolder.mkdir();
        }
        return true;
    }

    @Bean
    @ConditionalOnMissingBean(VideoDecoderService.class)
    public VideoDecoderService videoDecoderService() {
        return new VideoDecoderService();
    }

    @Bean
    @ConditionalOnMissingBean(GifEncoderService.class)
    public GifEncoderService gifEncoderService() {
        return new GifEncoderService();
    }

    @Bean
    @ConditionalOnMissingBean(ConverterService.class)
    public ConverterService converterService() {
        return new ConverterService();
    }


    @Configuration
    @ConditionalOnWebApplication
    public static class WebConfiguration {

        // deregister a filter
        @Bean
        @ConditionalOnProperty(prefix = "com.justgifit", name = "optimize")
        public FilterRegistrationBean deRegisterHiddenHttpMethodFilter(HiddenHttpMethodFilter filter) {
            FilterRegistrationBean bean = new FilterRegistrationBean(filter);
            bean.setEnabled(false);
            return bean;
        }
    }

}
