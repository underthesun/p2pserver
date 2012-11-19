/*
 * DataUpdateTask类为定时更新在线用户的线程
 */
package p2pserver;

import beans.UserTableModel;
import java.util.TimerTask;

/**
 *
 * @author ws
 */
public class DataUpdateTask extends TimerTask{
    private UserTableModel dataModel;
    public DataUpdateTask(UserTableModel dataModel)
    {
        this.dataModel=dataModel;
    }
    @Override
    public void run() {
        dataModel.update();
    }
    
    
}
