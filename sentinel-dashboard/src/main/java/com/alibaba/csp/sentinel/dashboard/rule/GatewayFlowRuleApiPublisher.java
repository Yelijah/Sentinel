package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Desc
 *
 * @author Elijah
 * created on 2024-08-02
 */
@Component("gatewayFlowRulePublisher")
@ConditionalOnProperty(prefix = "sentinel", name = "datasource", havingValue = "default", matchIfMissing = true)
public class GatewayFlowRuleApiPublisher implements DynamicRulePublisher<List<GatewayFlowRuleEntity>>{
    @Autowired
    private SentinelApiClient sentinelApiClient;
    @Autowired
    private AppManagement appManagement;

    @Override
    public void publish(String app, List<GatewayFlowRuleEntity> rules) throws Exception {
        if (StringUtil.isBlank(app)) {
            return;
        }
        if (rules == null) {
            return;
        }
        Set<MachineInfo> set = appManagement.getDetailApp(app).getMachines();

        for (MachineInfo machine : set) {
            if (!machine.isHealthy()) {
                continue;
            }
            sentinelApiClient.modifyGatewayFlowRules(app, machine.getIp(), machine.getPort(), rules);
        }
    }
}