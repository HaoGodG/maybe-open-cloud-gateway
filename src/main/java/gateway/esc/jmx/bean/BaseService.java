package gateway.esc.jmx.bean;

import gateway.esc.client.terminal.Terminal;
import lombok.extern.slf4j.Slf4j;

/**
 * author Hao
 * date 2025/9/4 17:38
 */
@Slf4j
public class BaseService implements BaseServiceMBean {
    private String baseServiceConfig = null;

    public BaseService() {
        this.baseServiceConfig = getRootPath();
    }

    public String getRootPath() {
        String root = null;
        try {
            root = Terminal.getInstance().getConfigRoot();
        } catch (Exception e) {
            log.error("getRootPath error", e);
            root = "../esc_conf";
        }
        return root + "/common/baseService.xml";
    }
}
