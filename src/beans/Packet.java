/*
 * Packet类定义收到的UDP数据包，包括IP地址、端口号、数据等
 * 以及相应的set、get方法
 */
package beans;

/**
 *
 * @author ws
 */
public class Packet {
    private String ip;
    private int port;
    private String data;
    private int index;

    public Packet(String ip, int port, String data,int index)
    {
        this.ip=ip;
        this.port=port;
        this.data=data;
        this.index=index;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    
}
