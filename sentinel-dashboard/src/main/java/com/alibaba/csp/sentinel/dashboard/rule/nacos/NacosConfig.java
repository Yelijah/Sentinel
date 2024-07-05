/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import java.util.List;
import java.util.Properties;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
@Configuration
@ConditionalOnProperty(prefix = "sentinel", name = "datasource", havingValue = "nacos")
public class NacosConfig {

    @Bean
    public ConfigService nacosConfigService(NacosProperties nacosProperties) throws Exception {
        Properties properties = new Properties();
        if (!StringUtils.isEmpty(nacosProperties.getServerAddr())) {
            properties.setProperty(PropertyKeyConst.SERVER_ADDR, nacosProperties.getServerAddr());
        }
        else {
            throw new IllegalArgumentException("nacos.server-addr is empty");
        }
        if (!StringUtils.isEmpty(nacosProperties.getNamespace())) {
            properties.setProperty(PropertyKeyConst.NAMESPACE, nacosProperties.getNamespace());
        }
        if (!StringUtils.isEmpty(nacosProperties.getUsername())) {
            properties.setProperty(PropertyKeyConst.USERNAME, nacosProperties.getUsername());
        }
        if (!StringUtils.isEmpty(nacosProperties.getPassword())) {
            properties.setProperty(PropertyKeyConst.PASSWORD, nacosProperties.getPassword());
        }
        return ConfigFactory.createConfigService(properties);
    }
}
