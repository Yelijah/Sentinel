package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Desc
 *
 * @author Elijah
 * created on 2024-08-02
 */
@Component("gatewayFlowRulePublisher")
@ConditionalOnProperty(prefix = "sentinel", name = "datasource", havingValue = "nacos")
public class GatewayFlowRuleNacosPublisher implements DynamicRulePublisher<List<GatewayFlowRuleEntity>> {
    @Autowired
    private ConfigService configService;
    private Converter<List<GatewayFlowRuleEntity>, String> converter = JSON::toJSONString;
    @Override
    public void publish(String app, List<GatewayFlowRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }
        configService.publishConfig(app + NacosConfigUtil.GW_FLOW_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, converter.convert(rules));
    }
}