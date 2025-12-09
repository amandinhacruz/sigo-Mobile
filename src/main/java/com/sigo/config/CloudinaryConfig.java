package com.sigo.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;

public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "SEU_CLOUD_NAME",
                "api_key", "SUA_API_KEY",
                "api_secret", "SEU_API_SECRET"
        ));
    }
}
