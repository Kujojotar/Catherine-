package core.container;

import java.util.ArrayList;
import java.util.List;

public class MyHostConfig implements ContainerConfig,SimpleHostConfig {
    private String name;
    private String appBase;
    private boolean unPackWARS=true;
    private boolean autoDeploy=true;
    private List<MyContextConfig> configs;

    public MyHostConfig(){
        configs=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppBase() {
        return appBase;
    }

    public void setAppBase(String appBase) {
        this.appBase = appBase;
    }

    public boolean isUnPackWARS() {
        return unPackWARS;
    }

    public void setUnPackWARS(boolean unPackWARS) {
        this.unPackWARS = unPackWARS;
    }

    public boolean isAutoDeploy() {
        return autoDeploy;
    }

    public void setAutoDeploy(boolean autoDeploy) {
        this.autoDeploy = autoDeploy;
    }

    public void addConfig(MyContextConfig config){
        configs.add(config);
    }

    @Override
    public String toString() {
        return "MyHostConfig{" +
                "name='" + name + '\'' +
                ", appBase='" + appBase + '\'' +
                ", unPackWARS=" + unPackWARS +
                ", autoDeploy=" + autoDeploy +
                ", configs=" + configs +
                '}';
    }
}
