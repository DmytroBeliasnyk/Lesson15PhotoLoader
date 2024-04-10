package org.photo.loader.config;

import org.photo.loader.model.CatalogName;
import org.photo.loader.model.CustomCatalog;
import org.photo.loader.model.Photo;
import org.photo.loader.service.GeneralService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner commandLineRunner(final GeneralService generalService,
                                               final ResourceLoader resourceLoader) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Resource resource = resourceLoader.getResource("classpath:/images");
                File images = resource.getFile();
                for (var name : CatalogName.values()) {
                    generalService.save(new CustomCatalog(name));
                }
                for (var image : images.listFiles()) {
                    try (FileInputStream fis = new FileInputStream(image)) {
                        String base64String = Base64.getEncoder().
                                encodeToString(fis.readAllBytes());
                        String imageName = image.getName().toLowerCase();
                        CatalogName catalog = null;
                        if (imageName.contains("java") && !imageName.contains("javascript")) {
                            catalog = CatalogName.JAVA;
                        } else if (imageName.contains("python")) {
                            catalog = CatalogName.PYTHON;
                        } else if (imageName.contains("js") || imageName.contains("javascript")) {
                            catalog = CatalogName.JAVASCRIPT;
                        } else if (imageName.contains("c++")) {
                            catalog = CatalogName.C;
                        }
                        generalService.save(new Photo(
                                imageName,
                                base64String,
                                generalService.findCatalog(catalog)));
                    }
                }
            }
        };
    }
}
