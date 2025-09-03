package gateway.esc.client.terminal;

import lombok.Data;

import java.util.Properties;

/**
 * author Hao
 * date 2025/8/18 17:09
 */
@Data
public class Terminal {

    private String terminalId;
    private String sysId;
    private String ip;
    private String terminalMac;
    private String jmxRegPort;
    private String jmxRmtport;
    private String zkUrl;
    private String domainId = null;
    private String redis = null;
    private int deployType = 0;
    private boolean pinLog = false;
    private long updateCheckInterval = 5000;
    private int updateCheckTimes = 10;
    private int detectPort = 8900;

    private static final String TERMINAL_ECODING = "UTF-8";
    private String configRoot = "../";
    private String terminalConf = null;
    private Properties prop = new Properties();
    private static Terminal instance = null;
    private boolean strategy = true;
    private boolean isUserApollo = true;

    public static final Object LOCK = new Object();

    public static Terminal getInstance(){
        if (instance!=null){
            return instance;
        }
        synchronized (LOCK) {
            if (instance == null) {
                Terminal temp= new Terminal();
                temp.init();
                instance=temp;
            }
        }
        return instance;
    }

    public void init(){
        //todo


    }

    public TerminalUri getUri(){
        return new TerminalUri(this.terminalId);
    }
}
