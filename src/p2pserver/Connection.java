/*
 * Connection类定义UDP连接，创建UDP套接字用于发送、接收消息，并对接收到的消息，调用相关方法进行解析
 */
package p2pserver;

import beans.Packet;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ws
 */
public class Connection {

    private int server_port = 4444;
    private DatagramSocket dataSocket;
    private P2PServer server;
    private ReceiveConnection recv;
    private SendConnection send;
    private int latest_out_index=0;
    public int max_keep=100;
    private HashMap<Integer,Packet> packet_map;

    public Connection(P2PServer server, int server_port) {
        packet_map=new HashMap<Integer,Packet>();
        this.server = server;
        this.server_port=server_port;
        try {
            dataSocket = new DatagramSocket(server_port);
        } catch (SocketException ex) {
            Logger.getLogger(P2PServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.recv = new ReceiveConnection(dataSocket,this);
        Thread t=new Thread(this.recv);
        t.start();
        this.send = new SendConnection(dataSocket);
    }
/*
     调用Server.parseCommand方法对收到的数据进行解析
     */
    public void parseCommand(String ip, int port, String data) {
        server.parseCommand(ip, port, data);
    }
/*
     调用SendConnection.sendMessage方法发送消息
     */
    public void sendMessage(String ip, int port, String data)
    {
        Packet packet=new Packet(ip,port,data,latest_out_index);
        this.latest_out_index++;
        this.packet_map.put(packet.getIndex(), packet);
        this.send.sendMessage(ip, port, data);
    }
}
