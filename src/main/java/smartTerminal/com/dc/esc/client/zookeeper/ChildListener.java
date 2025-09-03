package smartTerminal.com.dc.esc.client.zookeeper;

import java.util.List;

/**
 * author Hao
 * date 2025/9/2 11:10
 */
public interface ChildListener {
    void childChanged(String var1, List<String> var2);
}
