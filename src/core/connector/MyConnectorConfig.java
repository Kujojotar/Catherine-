package core.connector;

import core.container.ContainerConfig;

public class MyConnectorConfig implements ContainerConfig,SimpleConnectorConfig {
    private Integer port;
    private String protocol;
    private Integer connectionTimeout;
    private Integer redirectPort;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getRedirectPort() {
        return redirectPort;
    }

    public void setRedirectPort(Integer redirectPort) {
        this.redirectPort = redirectPort;
    }

    @Override
    public String toString() {
        return "MyConnectorConfig{" +
                "port=" + port +
                ", protocol='" + protocol + '\'' +
                ", connectionTimeout=" + connectionTimeout +
                ", redirectPort=" + redirectPort +
                '}';
    }
}
