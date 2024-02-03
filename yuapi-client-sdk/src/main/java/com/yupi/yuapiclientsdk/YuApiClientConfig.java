package com.yupi.yuapiclientsdk;

import com.yupi.yuapiclientsdk.client.YuApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 75654
 * @date 2024/1/29 22:14
 */
// 配置类标记
@Configuration
// 能够读取 application.yml 中的参数, 指定前缀 yuapi.client
@ConfigurationProperties("yuapi.client")
// 自动扫描组件
@ComponentScan
// 自动生成 setter getter
@Data
public class YuApiClientConfig {
    private String accessKey;
    private String secretKey;

    // 创建一个Bean
    @Bean
    public YuApiClient yuApiClient() {
        // 通过密钥构建一个实例
        return new YuApiClient(accessKey, secretKey);
    }
}
