/*
 *ReceiveConnection线程用于接收UDP消息
 */
package p2pserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ws
 */
public class ReceiveConnection implements Runnable {

    private DatagramSocket dataSocket;
    private int bytelen = 1024;
    private byte[] buf = new byte[bytelen];//接受UDP端口数据
    private DatagramPacket dataReceived;
    private Connection c;

    public ReceiveConnection(DatagramSocket dataSocket,  Connection c) {
        this.dataSocket = dataSocket;
        dataReceived = new DatagramPacket(buf, bytelen);
        this.c=c;
    }

    @Override
    public void run() {
        while (true) {
            try {
                dataSocket.receive(dataReceived);
                String ip = dataReceived.getAddress().getHostAddress();
                int port = dataReceived.getPort();
                String data = new String(buf, 0, dataReceived.getLength());
                c.parseCommand(ip,port,data);
            } catch (IOException ex) {
                Logger.getLogger(P2PServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
