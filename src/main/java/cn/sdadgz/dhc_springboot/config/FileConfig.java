package cn.sdadgz.dhc_springboot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "my.file-config")
public class FileConfig {

    private String uploadPath;
    private String downloadPath;
    private String imgUploadPath;
    private String fileUploadPath;

}
