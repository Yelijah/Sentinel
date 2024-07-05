package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Desc
 *
 * @author Elijah
 * created on 2024-07-02
 */
@Configuration
@EnableConfigurationProperties(NacosProperties.class)
public class DatasourceConfiguration {

//    @ConditionalOnProperty(prefix = "sentinel", name = "datasource", havingValue = "nacos")
//    @Configuration
//    public static class NacosDatasourceConfiguration {
//
//    }
}