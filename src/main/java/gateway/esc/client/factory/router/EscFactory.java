package gateway.esc.client.factory.router;

import gateway.esc.client.factory.listener.TerminalListener;
import lombok.extern.slf4j.Slf4j;
import smartTerminal.com.dc.esc.client.zookeeper.ConfigFile;

/**
 * author Hao
 * date 2025/8/29 16:39
 */
@Slf4j
public abstract class EscFactory {
    public abstract void init();

    public abstract void load();

    public abstract void refresh();

    public static EscFactory getInstance(){
        System.out.println("must reload this method");
        return null;
    }

    public static void refreshFile(String zkUri,String filePath){
     try{
         String uri=zkUri.replace("\\\\","/");
         ConfigFile cfile = new ConfigFile(filePath);
         ZkclientZookeeperClient client = TerminalListener.getClient();
         String data= client.getClient().readData(uri);
         if (data!=null&&data.startsWith("<")){
             Document doc= DocumentHelper.parseText(data);
         }
     }
    }
}
