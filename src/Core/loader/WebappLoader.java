package Core.loader;

import Core.container.Container;

import java.lang.reflect.Constructor;

/**
 * @author james
 * WebappClassLoader的管理器
 */
public class WebappLoader implements Loader,Runnable {
    private WebappClassLoader loader=null;
    private Container container=null;
    private boolean delegate;
    private String loaderClass;
    private boolean shutdown=false;
    private String packageUri="Core.loader.";
    private char modifyType='n';

    public WebappLoader(Container container){
        super();
        this.container=container;
        loaderClass="WebappClassLoader";
        try {
            loader = createClassLoader();
        }catch (Exception e){
            e.printStackTrace();
            loader=null;
        }
    }



    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container=container;
    }

    @Override
    public boolean getDelegate() {
        return delegate;
    }

    @Override
    public void setDelegate(boolean delegate) {
        this.delegate=delegate;
    }

    @Override
    public boolean getReloadable() {
        return false;
    }

    @Override
    public boolean modified() {
        return loader.modified();
    }

    public String getLoaderClass() {
        return loaderClass;
    }

    public void setLoaderClass(String loaderClass) {
        this.loaderClass = loaderClass;
    }

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    //先留个坑，这边有点恁不懂。。。
    private WebappClassLoader createClassLoader() throws Exception{
        Class clazz=Class.forName(packageUri+loaderClass);
        WebappClassLoader classLoader=null;
        if(clazz.getClassLoader().getParent()==null){
            classLoader=(WebappClassLoader)clazz.newInstance();
        }else{
            Class[] argTypes={ClassLoader.class};
            Object[] args={clazz.getClassLoader().getParent()};
            Constructor constr=clazz.getConstructor(argTypes);
            classLoader=(WebappClassLoader)constr.newInstance(args);
        }
        return classLoader;
    }

    public Class<?> loadClass(String name)throws ClassNotFoundException{
        return loader.loadClass(name);
    }

    //暂时先放空
    public void addRepositories(){
    }

    public void setPermission(){

    }

    public void setClassPath(String path){
        System.out.println("抱歉，我的能力不足以支持JSP");
    }

    /**
     * 开启检查线程，判断资源文件夹是否产生变化
     * 检查线程将在一定的时间间隔进行一次检查
     * 如果产生变化，将得到受到更改的文件以及更改类型
     * 将变化信息交给Context容器进行进一步处理
     */
    public synchronized void run(){
        String lastModifiedFileName=null;
        while(!shutdown){
            try{
                wait(6000);
            }catch (InterruptedException e){

            }
            if(modified()){
                lastModifiedFileName=loader.getLastModifiedFileName();
                modifyType=loader.getModifyType();
                notifyContainer(lastModifiedFileName);
            }
        }
        loader.setShutdown(true);
        loader.shutdown();
        shutdown();
    }

    /**
     * 关闭资源
     */
    public void shutdown(){
        packageUri=null;
        loaderClass=null;
        loader=null;
        container=null;
    }

    /**
     * 通知父容器对变化做出进一步处理
     * @param modifiedFileName
     */
    private void notifyContainer(String modifiedFileName){
        container.Notified(modifiedFileName,modifyType);
    }

    public void openExamThread(){
        Thread thread=new Thread(this);
        thread.start();
    }

}