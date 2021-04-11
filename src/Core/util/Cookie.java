package Core.util;

import java.util.Date;

/**
 * @author james
 * 一个简单的Cookie实现类
 */
public class Cookie {
    private String key;
    private String value;
    //记录Cookie的创建时间，便于删除
    private Date createdTime;
    //Cookie从创建到被删除的时间，折算为小时是10小时
    private long timeBarrie=36000000;

    public Cookie(){
        createdTime=new Date();
    }

    public Cookie(String key, String value, long maxAge){
        this.key=key;
        this.value=value;
        createdTime=new Date();
        this.timeBarrie=maxAge;
    }

    public Cookie(String key, String value){
        this.key=key;
        this.value=value;
        createdTime=new Date();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    //判断cookie是否过期
    public boolean isTimeOut(Date current){
        if(current.getTime()-createdTime.getTime()>=timeBarrie){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", createdTime=" + createdTime +
                ", timeBarrie=" + timeBarrie +
                '}';
    }
}
