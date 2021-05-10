package core.server;

import core.connector.SimpleConnectorConfig;
import core.container.ContainerConfig;
import core.container.SimpleEngineConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author james
 * 简单的服务器配置
 */
public class MyServerConfig implements ContainerConfig {
    private Integer serverPort;
    private String shutdown;
    private String serviceName;
    private List<SimpleConnectorConfig> configs;
    private SimpleEngineConfig engineConfig;

    public MyServerConfig(){
        configs=new ArrayList<>();
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getShutdown() {
        return shutdown;
    }

    public void setShutdown(String shutdown) {
        this.shutdown = shutdown;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public SimpleEngineConfig getEngineConfig() {
        return engineConfig;
    }

    public void setEngineConfig(SimpleEngineConfig engineConfig) {
        this.engineConfig = engineConfig;
    }

    public void addConfig(SimpleConnectorConfig config){
        configs.add(config);
    }

    @Override
    public String toString() {
        return "MyServerConfig{" +
                "serverPort=" + serverPort +
                ", shutdown='" + shutdown + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", configs=" + configs +
                ", engineConfig=" + engineConfig +
                '}';
    }
}
