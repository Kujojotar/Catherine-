package core.container;

import core.connector.SimpleConnectorConfig;

import java.util.ArrayList;
import java.util.List;

public class MyEngineConfig implements ContainerConfig,SimpleEngineConfig {
    private List<SimpleHostConfig> configs;
    private String name;
    private String defaultHost;

    public MyEngineConfig(){
        configs=new ArrayList<>();
    }

    public void addConfig(SimpleHostConfig config){
        configs.add(config);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultHost() {
        return defaultHost;
    }

    public void setDefaultHost(String defaultHost) {
        this.defaultHost = defaultHost;
    }

    @Override
    public String toString() {
        return "MyEngineConfig{" +
                "configs=" + configs +
                ", name='" + name + '\'' +
                ", defaultHost='" + defaultHost + '\'' +
                '}';
    }
}
