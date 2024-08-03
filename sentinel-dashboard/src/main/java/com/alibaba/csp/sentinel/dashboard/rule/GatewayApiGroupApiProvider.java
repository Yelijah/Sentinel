package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Desc
 *
 * @author Elijah
 * created on 2024-08-02
 */
@Component("gatewayApiGroupProvider")
@ConditionalOnProperty(prefix = "sentinel", name = "datasource", havingValue = "default", matchIfMissing = true)
public class GatewayApiGroupApiProvider implements DynamicRuleProvider<List<ApiDefinitionEntity>> {
    @Autowired
    private AppManagement appManagement;
    @Autowired
    private SentinelApiClient sentinelApiClient;

    @Override
    public List<ApiDefinitionEntity> getRules(String appName) throws Exception {
        if (StringUtil.isBlank(appName)) {
            return new ArrayList<>();
        }
        List<MachineInfo> list = appManagement.getDetailApp(appName).getMachines()
                .stream()
                .filter(MachineInfo::isHealthy)
                .sorted((e1, e2) -> Long.compare(e2.getLastHeartbeat(), e1.getLastHeartbeat())).collect(Collectors.toList());
        if (list.isEmpty()) {
            return new ArrayList<>();
        } else {
            MachineInfo machine = list.get(0);
            return sentinelApiClient.fetchApis(machine.getApp(), machine.getIp(), machine.getPort()).get();
        }
    }
}