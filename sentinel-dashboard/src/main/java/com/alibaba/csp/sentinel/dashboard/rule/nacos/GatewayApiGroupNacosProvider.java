package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc
 *
 * @author Elijah
 * created on 2024-08-02
 */
@Component("gatewayApiGroupProvider")
@ConditionalOnProperty(prefix = "sentinel", name = "datasource", havingValue = "nacos")
public class GatewayApiGroupNacosProvider implements DynamicRuleProvider<List<ApiDefinitionEntity>> {
    @Autowired
    private ConfigService configService;
    private Converter<String, List<ApiDefinitionEntity>> converter = s -> JSON.parseArray(s, ApiDefinitionEntity.class);

    @Override
    public List<ApiDefinitionEntity> getRules(String appName) throws Exception {
        String rules = configService.getConfig(appName + NacosConfigUtil.GW_API_GROUP_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, 3000);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}