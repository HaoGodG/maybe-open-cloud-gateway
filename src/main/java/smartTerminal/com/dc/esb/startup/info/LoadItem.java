package smartTerminal.com.dc.esb.startup.info;

import lombok.Data;

/**
 * author Hao
 * date 2025/8/19 17:04
 */
@Data
public class LoadItem {
    private String type;
    private String id;
    private boolean flag = false;
    private String info;
    private String fileName;
    private long loadTime = 0L;

    public LoadItem(String type, String id) {
        this.type = type;
        this.id = id;
        this.flag = false;
    }

    public LoadItem(String type, String id, boolean flag, String info, String fileName, long loadTime) {
        this.type = type;
        this.id = id;
        this.flag = flag;
        this.info = info;
        this.fileName = fileName;
        this.loadTime = loadTime;
    }

    public void setFailedMsg(String info, String filePath) {
        this.flag = false;
        this.info = info;
        this.fileName = filePath;
        this.loadTime = System.currentTimeMillis();
    }

    public void setSuccessMsg(String info, String filePath) {
        this.flag = true;
        this.info = info;
        this.fileName = filePath;
        this.loadTime = System.currentTimeMillis();
    }
}
