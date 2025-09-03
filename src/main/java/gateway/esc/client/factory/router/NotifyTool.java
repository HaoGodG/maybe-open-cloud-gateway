package gateway.esc.client.factory.router;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author Hao
 * date 2025/9/3 19:55
 */
@Slf4j
public class NotifyTool {
    private static final String NAME = "name";

    private String name = null;

    private String timestamp = null;

    private String parenteName   = null;

    private String nodeName = null;

    private List<NotifyTool> notifyList = null;

    private static NotifyTool root = null;

    private static Map<String,List<String>> changeMap= new HashMap<>();

    public static final String MSP_COMMON = "msp.common";


}
