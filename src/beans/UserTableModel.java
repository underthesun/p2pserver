/*
 * UserTableModel继承了AbstractTableModel组件，用以显示在线用户相关信息
 * 其中update方法判断用户是否掉线，若用户注册时间超过30秒，则移除用户
 */
package beans;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ws
 */
public class UserTableModel extends AbstractTableModel implements TableModelListener {

    private List<UserConnected> users;

    public List<UserConnected> getUsers() {
        return users;
    }
    public String[] fileds={ "Username", "Ip", "Port", "LifeTime"};

    public void addUser(UserConnected user)
    {
        users.add(user);
        int  row=users.size();
        this.fireTableRowsInserted(row, row);
    }
    public UserTableModel() {
        users = new ArrayList<UserConnected>();
    }

    public String getUserListInfo()
    {
        String result="";
        for(UserConnected user:users)
        {
            result+=user.getUserInfo();
        }
        return result;
    }
    
    public UserConnected findUser(String username)
    {
        for(UserConnected user:users)
        {
            if(user.getUsername().equals(username))
            {
                return user;
            }
        }
        return null;
    }
    
    public void updateUser(String username, UserConnected now)
    {
        for(int i=0;i<users.size();i++)
        {
            UserConnected user=users.get(i);
            if(username.equals(user.getUsername()))
            {
                user=now;
                this.fireTableRowsUpdated(i, i);
            }
        }
    }
    
    public void update()
    {
        long current=(new Date()).getTime();
        for(int i=0;i<users.size();i++)
        {
            UserConnected user=users.get(i);
            if((current-user.getLast_connected())/1000>30)//若用户注册时间超过30秒，则移除用户
            {
                this.users.remove(i);
                i--;
            }
        }
        this.fireTableDataChanged();
    }
    
    @Override 
    public String getColumnName(int index)
    {
        return this.fileds[index];
    }
    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        int column = this.fileds.length;
        return column;
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        try {
            if(columnIndex==3)
            {
                long s=((new Date()).getTime()-users.get(rowIndex).getBegin())/1000;
                return s+"";
            }
            Field[] fds = Class.forName("beans.UserConnected").getDeclaredFields();
            return fds[columnIndex].get(users.get(rowIndex)).toString();
        } catch (Exception ex) {
            Logger.getLogger(UserTableModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        fireTableChanged(e); 
    }
}
