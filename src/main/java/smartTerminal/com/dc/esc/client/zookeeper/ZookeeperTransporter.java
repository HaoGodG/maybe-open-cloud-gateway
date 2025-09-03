package smartTerminal.com.dc.esc.client.zookeeper;

/**
 * author Hao
 * date 2025/9/3 19:32
 */
public interface ZookeeperTransporter {

    ZookeeperClient connect(String var1);

    ZookeeperClient connect();

    String getUrl();

    void setUrl(String var1);
}
