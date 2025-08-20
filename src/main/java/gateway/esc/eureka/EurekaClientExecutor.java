package gateway.esc.eureka;


import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.apache.log4j.Logger;

/**
 * author Hao
 * date 2025/8/19 15:30
 */
public class EurekaClientExecutor {
    private static final Logger log = Logger.getLogger(EurekaClientExecutor.class);
    private static final Object mLock = new Object();

    public EurekaClientExecutor() {
    }

    private volatile static EurekaClientExecutor instance = null;

    public static EurekaClientExecutor getInstance() {
        if (instance == null) {
            synchronized (mLock) {
                if (instance == null) {
                    instance = new EurekaClientExecutor();
                }
            }
        }

        return instance;
    }

    public String getStatus() {
        String re = "NotStarted";
        InstanceInfo info = ApplicationInfoManager.getInstance().getInfo();
        InstanceInfo.InstanceStatus status;
        if (info != null) {
            status = info.getStatus();
            if (InstanceInfo.InstanceStatus.UP.equals(status)) {
                re = status.toString();
            }
        }
        return re;
    }
}
