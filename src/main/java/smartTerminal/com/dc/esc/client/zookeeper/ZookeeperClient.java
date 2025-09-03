package smartTerminal.com.dc.esc.client.zookeeper;

import java.util.List;

/**
 * author Hao
 * date 2025/9/2 10:52
 */
public interface ZookeeperClient {
    void create(String var1,boolean var2);

    void delete(String var1);

    List<String> getChildren(String var1);

    List<String> addChildListener(String var1,ChildListener var2);

    void removeChildListener(String var1,ChildListener var2);

    void addStateListener(StateListener var1);

    void removeStateListener(StateListener var1);

    void writeData(String var1,String var2);

    boolean isConnected();

    void close();

    String getUrl();
}
