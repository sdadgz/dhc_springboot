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

    private String uploadPath; // 上传的物理位置
    private String downloadPath; // 下载的url
    private String imgUploadPath;
    private String fileUploadPath;

}
