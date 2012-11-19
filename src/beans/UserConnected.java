/*
 * UserConnection类对用户注册事件进行处理，获取用户信息，更新用户的注册时间
 */
package beans;

import java.util.Date;

/**
 *
 * @author ws
 */
public class UserConnected{
    public String username;
    public String ip;
    public int port;
    public long begin;
    public long last_connected;
    public static String[] fileds={"username","ip","port","begin"};
    
    

    public UserConnected(String username, String ip, int port)
    {
        this.username=username;
        this.ip=ip;
        this.port=port;
        this.begin=new Date().getTime();
        this.last_connected=this.begin;
    }
    public String getUserInfo()
    {
        String info=username+" "+ip+" "+port+";";
        return info;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getLast_connected() {
        return last_connected;
    }

    public void setLast_connected(long last_connected) {
        this.last_connected = last_connected;
    }
    
}
