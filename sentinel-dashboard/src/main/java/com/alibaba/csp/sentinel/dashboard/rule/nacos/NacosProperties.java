package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc
 *
 * @author Elijah
 * created on 2024-06-30
 */
@ConfigurationProperties(prefix = "nacos")
//@ConditionalOnProperty(prefix = "sentinel", name = "datasource", havingValue = "nacos")
public class NacosProperties {

    private String serverAddr;

    private String username;

    private String password;

    private String namespace;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private String groupId;

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}