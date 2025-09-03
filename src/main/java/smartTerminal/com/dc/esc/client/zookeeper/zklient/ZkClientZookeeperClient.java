package smartTerminal.com.dc.esc.client.zookeeper.zklient;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import smartTerminal.com.dc.esc.client.zookeeper.ChildListener;
import smartTerminal.com.dc.esc.client.zookeeper.support.AbstractZookeeperClient;

import java.util.List;

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
            public void handleStateChanged(KeeperState state) throws Exception {
                ZkClientZookeeperClient.this.state = state;
                if (state == KeeperState.Disconnected) {
                    ZkClientZookeeperClient.this.stateChanged(0);
                } else if (state == KeeperState.SyncConnected) {
                    ZkClientZookeeperClient.this.stateChanged(1);
                }
            }

            public void handleNewSession() throws Exception {
                ZkClientZookeeperClient.this.stateChanged(2);
            }

            public void handleSessionEstablishmentError(Throwable error) throws Exception {
                log.error("handleSessionEstablishmentError", error);
            }
        });
    }

    public void createPersistent(String path) {
        try {
            this.client.createPersistent(path, true);
        } catch (ZkNodeExistsException var3) {

        }
    }

    public void createEphemeral(String path) {
        try {
            this.client.createEphemeral(path);
        } catch (ZkNodeExistsException var3) {

        }
    }

    public void delete(String path) {
        try {
            this.client.delete(path);
        } catch (ZkNoNodeException var3) {

        }
    }

    public void deleteRecursive(String path) {
        try {
            this.client.deleteRecursive(path);
        } catch (ZkNoNodeException var3) {

        }
    }

    @Override
    public List<String> getChildren(String var1) {
        try {
            return this.client.getChildren(var1);
        } catch (ZkNoNodeException var3) {
            return null;
        }
    }

    public boolean isConnected() {
        return this.state == KeeperState.SyncConnected;
    }

    public void doClose() {
        this.client.close();
    }

    public IZkChildListener createTargetChildListener(String path, final ChildListener listener) {
        return (parentPath, currentChilds) -> listener.childChanged(parentPath, currentChilds);
    }


    public List<String> addTargetChildListener(String path, IZkChildListener listener) {
        return this.client.subscribeChildChanges(path, listener);
    }

    public void removeTargetChildListener(String path, IZkChildListener listener) {
        this.client.unsubscribeChildChanges(path, listener);
    }

    public void writeData(String path, String data) {
        this.createPersistent(path);
        this.client.writeData(path, data);
        log.info("publish to uri [" + path + "] data:" + data);
    }
}
