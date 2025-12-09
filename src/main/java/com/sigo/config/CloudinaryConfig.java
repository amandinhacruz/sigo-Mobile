package com.sigo.config;



import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class CloudinaryConfig {

    @Value("${dmvughncq}")
    private String cloudName;

    @Value("${275324558262478}")
    private String apiKey;

    @Value("${A2rA1Jkn5UcKpdhn2vIB1t8BptM}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
}

