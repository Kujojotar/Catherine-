package core.container;

public class MyContextConfig implements ContainerConfig {
    private String path;
    private String docBase;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDocBase() {
        return docBase;
    }

    public void setDocBase(String docBase) {
        this.docBase = docBase;
    }

    @Override
    public String toString() {
        return "MyContextConfig{" +
                "path='" + path + '\'' +
                ", docBase='" + docBase + '\'' +
                '}';
    }
}
