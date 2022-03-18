package com.example.laptop.config;

import com.example.laptop.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    public WebConfiguration() throws IOException {
        Files.createDirectories(Constants.PATH_CODE_FOLDER);
    }
}