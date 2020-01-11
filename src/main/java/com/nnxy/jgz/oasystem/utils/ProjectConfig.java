package com.nnxy.jgz.oasystem.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author JGZ
 * CreateTime 2019/12/31 15:09
 * Email 1945282561@qq.com
 */
@ConfigurationProperties
@PropertySource("classpath:projectConfig.properties")
@Configuration
@Data
public class ProjectConfig {
    private String serverAddress;
    private String noticeFile;
    private String noticeFileAddress;
    private String headPortraits;
    private String headPortraitsAddress;
    private String userFile;
    private String userFileAddress;
    private String articleFile;
    private String articleFileAddress;
}
