package core.loader;

import Constants.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @author james
 * @version 0.1
 * 一个简陋的WebappClassLoader
 * 为了方便起见，这个Loader只支持在servlet文件夹下加载servlet
 */
public class WebappClassLoader extends URLClassLoader {
    private ClassLoader parent;
    //这个是目标文件目录的路径
    private String dir_url=Constants.WEB_ROOT+File.separator+"source"+File.separator+"servlet";

    //下面是此加载器不可接触的包，如果请求的servlet全限定路径包含这些包名将被交给父加载器处理
    private static final String[] triggers={
            "javax.servlet.Servlet"
    };
    private static final String[] packageTriggers={
            "javax",
            "org.xml.sax",
            "prg.w3c.dom",
            "org.apache.xerces",
            "org.apache.xalan"
    };

    protected File dir=new File(dir_url);
    protected Deque<Long> modifiedTime=null;
    protected File[] files=dir.listFiles();
    protected Map<File,Long> records=new HashMap<>();
    private String lastModifiedFile=null;
    private String loadUrl=(dir_url.substring(dir_url.indexOf(Constants.WEB_ROOT)+Constants.WEB_ROOT.length()+1)+File.separator).replaceAll("\\\\",".");
    private boolean shutdown=false;
    private char modifyType='n';

    public WebappClassLoader(){
            super(new URL[0]);
            this.parent=getParent();
            URL url=null;
            try{
                url=new URL("file://"+Constants.WEB_ROOT+ File.separator+"source"+File.separator+"servlet");
            }catch (Exception e){
                e.printStackTrace();
            }
            for(File file:files){
                records.put(file, file.lastModified());
            }
            addURL(url);
            modifiedTime=new LinkedList<>();
            modifiedTime.add(dir.lastModified());
    }

    public WebappClassLoader(ClassLoader loader){
        super(new URL[0]);
        //注意一下构造器问题
        this.parent=loader;
        URL url=null;
        try{
            url=new URL("file://"+Constants.WEB_ROOT+ File.separator+"source"+File.separator+"servlet");
        }catch (Exception e){
            e.printStackTrace();
        }
        addURL(url);
        for(File file:files){
            records.put(file, file.lastModified());
        }
        modifiedTime=new LinkedList<>();
        modifiedTime.add(dir.lastModified());
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(name.startsWith("javax.servlet")){
            return parent.loadClass(name);
        }
        name=loadUrl+name;
        return super.loadClass(name);
    }

    public void addRepository(String path){

    }

    public char getModifyType() {
        return modifyType;
    }

    /**
     * 监控目标文件夹有没有被修改
     * 实现的方法有些傻逼，说实话
     * @return
     */
    public boolean modified(){
          if(dir.lastModified()==modifiedTime.getLast()){
              return false;
          }
          findLastModifiedFile();
          modifiedTime.add(dir.lastModified());
          return true;
    }

    public void findLastModifiedFile(){
        File[] newFiles=dir.listFiles();
        if(files.length> newFiles.length){
            for(File file:files){
                if(!file.exists()){
                    lastModifiedFile=file.getName().substring(0,file.getName().indexOf(".java"));
                    modifyType='d';
                    break;
                }
            }
            files=newFiles;
        }
        for(File file:newFiles){
            if(records.get(file)==null){
                records.put(file,file.lastModified());
                lastModifiedFile=file.getName().substring(0,file.getName().indexOf(".java"));
                files=newFiles;
                modifyType='a';
                return ;
            }
            if(file.lastModified()!=records.get(file)){
                records.put(file,file.lastModified());
                lastModifiedFile=file.getName().substring(0,file.getName().indexOf(".java"));
                files=newFiles;
                modifyType='u';
                return ;
            }
        }
    }

    public String getLastModifiedFileName(){
        return lastModifiedFile;
    }

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    //小本买卖，检查不要太频繁
    //说实话应该是在WebClassLoader里开启监控，在这里好像没什么用🤡
    private synchronized void run(){
         while(!shutdown){
             try{
                 wait(6000);
             }catch (InterruptedException e){

             }
             if(modified()){
                 System.out.println("检测到文件被修改");
                 findLastModifiedFile();
                 System.out.println(lastModifiedFile);
             }
         }
    }

    //越想越觉得傻逼，呜呜呜🤡
    private void modifiedDir(String url)throws FileNotFoundException {
        File file=new File(url);
        if(!file.exists()){
            throw new FileNotFoundException("找不到目标路径");
        }
        this.dir=file;
        this.modifiedTime=new LinkedList<>();
        modifiedTime.add(file.lastModified());
        loadUrl=(dir_url.substring(dir_url.indexOf(Constants.WEB_ROOT)+Constants.WEB_ROOT.length()+1)+File.separator).replaceAll("\\\\",".");
    }

    /**
     * 关闭时清理资源
     */
    public void shutdown(){
        parent=null;
        dir_url=null;
        dir=null;
        modifiedTime=null;
        lastModifiedFile=null;
        files=null;
        records=null;
        loadUrl=null;
        return ;
    }
}
