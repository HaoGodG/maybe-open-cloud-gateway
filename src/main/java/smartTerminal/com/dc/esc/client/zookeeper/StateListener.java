package smartTerminal.com.dc.esc.client.zookeeper;

/**
 * author Hao
 * date 2025/9/2 11:08
 */
public interface StateListener {
    int DISCONNECTED=0;
    int CONNECTED=1;
    int RECONNECTED=2;

    void stateChanged(int var1);
}
