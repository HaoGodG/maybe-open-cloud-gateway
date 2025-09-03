package smartTerminal.com.dc.esc.client.zookeeper.zklient;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import smartTerminal.com.dc.esc.client.zookeeper.support.AbstractZookeeperClient;

/**
 * author Hao
 * date 2025/9/2 10:32
 */
@Slf4j
public class ZkClientZookeeperClient extends AbstractZookeeperClient<IZkChildListener> {
    private final ZkClient client;
    private volatile KeeperState state;

    public ZkClient getClient() {
        return this.client;
    }

    public ZkClientZookeeperClient(String url) {
        super(url);
        this.state = KeeperState.SyncConnected;
        this.client = new ZkClient(url, 6000000, 5000);
        this.client.addAuthInfo("digest", "admin:admin".getBytes());
        this.client.subscribeStateChanges(new IZkStateListener() {
            public void handleStateChanged(KeeperState state)throws Exception{
                ZkClientZookeeperClient.this.state=state;
                if (state==KeeperState.Disconnected){
                    ZkClientZookeeperClient.this.stateChanged(0);
                }else if (state==KeeperState.SyncConnected){
                    ZkClientZookeeperClient.this.stateChanged(1);
                }
            }
            public void handleNewSession() throws Exception {
                ZkClientZookeeperClient.this.stateChanged(2);
            }

            public void handleSessionEstablishmentError(Throwable error) throws Exception {
                log.error("handleSessionEstablishmentError",error);
            }
        });
    }

}
