/*
 * 服务器应用程序类
 * 1.对注册的用户进行判断，并登记或者拒绝
 * 2.将新登录的用户更新至所有在线客户端
 * 3.解析收到的数据并返回应答
 * 4.对用户的登记进行更新
 */
package p2pserver;

import beans.UserConnected;
import java.util.Date;
import java.util.List;
import view.ServerFrame;

/**
 *
 * @author ws
 */
public class P2PServer {

    public static int server_port = 4444;
    private ServerFrame sf;
    private Connection c;

    public P2PServer(ServerFrame sf) {
        this.sf = sf;
        this.c = new Connection(this, this.server_port);

    }
/*
     parseCommand方法解析收到的数据，并做出相应应答
     */
    public void parseCommand(String ip, int port, String data) {
        int firstIndex = data.indexOf(":");
        String command = data.substring(0, firstIndex);
        if (command.equals("register")) {
            boolean register_result = register(ip, port, data.substring(firstIndex + 1));
            if (register_result) {
                String result = "register:ok";
                this.broadCast(result);
                result = "list:";
                result += this.sf.userModel.getUserListInfo();
                this.broadCast(result);
            } else {
                String result = "register:no";
                this.c.sendMessage(ip, port, result);
            }
        } else if (command.equals("connect")) {
            connect(ip, port, data.substring(firstIndex + 1));
            String result = "list:";
            result += this.sf.userModel.getUserListInfo();
            this.c.sendMessage(ip, port, result);
        }
    }
/*
     register方法判断用户是否已登记，若已登记，则拒绝其注册
     */
    public boolean register(String ip, int port, String data) {
        if (this.sf.userModel != null) {
            UserConnected user = this.sf.userModel.findUser(data);
            if (user == null) {
                user = new UserConnected(data, ip, port);
                this.sf.userModel.addUser(user);
                return true;
            }
        }
        return false;
    }
/*
     addUser方法添加用户至在线用户列表
     */
    public void addUser(UserConnected user) {
        this.sf.userModel.addUser(user);
    }
/*
     connect方法判断用户是否已存在，并更新用户注册时间
     */
    public void connect(String ip, int port, String data) {
        UserConnected user = this.sf.userModel.findUser(data);
        if (user == null) {
            user = new UserConnected(data, ip, port);
            this.sf.userModel.addUser(user);
        } else {
            user.last_connected = (new Date()).getTime();
        }
    }
/*
     broadCast方法将在线用户列表发送至在线用户
     */
    public void broadCast(String result) {
        List<UserConnected> users = this.sf.userModel.getUsers();
        for (UserConnected user : users) {
            this.c.sendMessage(user.getIp(), user.getPort(), result);
        }
    }

}
