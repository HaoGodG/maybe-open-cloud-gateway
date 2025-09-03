package gateway.esc.client.factory.listener;

import gateway.esc.client.terminal.Terminal;
import gateway.esc.startup.info.ESCStartInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import smartTerminal.com.dc.esb.startup.info.LoadItem;
import smartTerminal.com.dc.esc.client.zookeeper.zklient.ZkClientZookeeperClient;
import smartTerminal.com.dc.esc.client.zookeeper.zklient.ZkClientZookeeperTransporter;

/**
 * author Hao
 * date 2025/8/29 16:36
 */
@Slf4j
public class TerminalListener {
    private static ZkClientZookeeperClient client=null;

    public static void startListener(){
        Terminal config = Terminal.getInstance();
        if (log.isInfoEnabled()){
            log.info("current config is ="+config.isUserApollo());
        }
        if (config.isUserApollo()){
            LoadItem apolloItem=new LoadItem("startListeners","ApolloConfigListener");
            try {
                ApolloConfigListener.start();
                apolloItem.setSuccessMsg("start apolloListener success","");
                ESCStartInfo.addListenerInfo(apolloItem);
            }catch (Exception ex){
                log.error("start apolloListener error",ex);
                apolloItem.setFailedMsg("start apolloListener error:"+ex.getMessage(),"");
                ESCStartInfo.addListenerInfo(apolloItem);
            }
        }else {
            getClient();
            LoadItem routerItem=new LoadItem("startListeners","ServiceRouterListener");
            try {
                ServiceRouterListener.start(client.getClient());
                routerItem.setSuccessMsg("start ServiceRouterListener success","");
                ESCStartInfo.addListenerInfo(routerItem);
            }catch (Exception ex){
                log.error("start ServiceRouterListener error",ex);
                routerItem.setFailedMsg("start ServiceRouterListener error:"+ex.getMessage(),"");
                ESCStartInfo.addListenerInfo(routerItem);
            }
            LoadItem balanceItem =new LoadItem("startListeners","ServiceBalanceListener");
            try {
                ServiceBalanceListener.start(client.getClient());
                balanceItem.setSuccessMsg("start ServiceBalanceListener success", "");
                ESCStartInfo.addListenerInfo(balanceItem);
            }catch (Exception e ){
                log.error("start ServiceBalanceListener error",e);
                balanceItem.setFailedMsg("start ServiceBalanceListener error:"+e.getMessage(),"");
                ESCStartInfo.addListenerInfo(balanceItem);
            }
            LoadItem commonItem =new LoadItem("startListeners","CommonConfigListener");
            try {
                CommonConfigListener.start(client.getClient());
                commonItem.setSuccessMsg("start CommonConfigListener success", "");
                ESCStartInfo.addListenerInfo(commonItem);
            }catch (Exception e ){
                log.error("start CommonConfigListener error",e);
                commonItem.setFailedMsg("start CommonConfigListener error:"+e.getMessage(),"");
                ESCStartInfo.addListenerInfo(commonItem);
            }
            LoadItem terminalItem =new LoadItem("startListeners","TerminalConfigListener");
            try {
                TerminalConfigListener.start(client.getClient());
                terminalItem.setSuccessMsg("start TerminalConfigListener success", "");
                ESCStartInfo.addListenerInfo(terminalItem);
            }catch (Exception e ){
                log.error("start TerminalConfigListener error",e);
                terminalItem.setFailedMsg("start TerminalConfigListener error:"+e.getMessage(),"");
                ESCStartInfo.addListenerInfo(terminalItem);
            }
        }
    }

    public static ZkClientZookeeperClient getClient(){
        if (client==null){
            client= ZkClientZookeeperTransporter.getInstance().connect();
        }
        if (client.isConnected()){
            return client;
        }else {
            client.close();
            client=ZkClientZookeeperTransporter.getInstance().connect();
        }
        return client;
    }

}
