package smartTerminal.com.dc.esc.client.zookeeper.zklient;

import gateway.esc.client.terminal.Terminal;
import smartTerminal.com.dc.esc.client.zookeeper.ZookeeperTransporter;

/**
 * author Hao
 * date 2025/9/3 19:31
 */
public class ZkClientZookeeperTransporter implements ZookeeperTransporter {

    private static String url = null;
    private static ZkClientZookeeperTransporter instance = new ZkClientZookeeperTransporter();

    public ZkClientZookeeperTransporter() {
    }

    public static ZkClientZookeeperTransporter getInstance() {
        url = Terminal.getInstance().getZkUrl();
        return instance;
    }

    @Override
    public ZkClientZookeeperClient connect(String var1) {
        return new ZkClientZookeeperClient(var1);
    }

    public ZkClientZookeeperClient connect() {
        return url != null  && url.length() > 0 ? new ZkClientZookeeperClient(url) : null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String var1) {
        ZkClientZookeeperTransporter.url = var1;
    }
}
