package gateway.esc.startup;

import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author Hao
 * date 2025/9/3 20:28
 */
@Slf4j
public class TokenApplyTask extends TimerTask {

    public volatile static String token="";

    private volatile static ConcurrentHashMap commonConfig = new ConcurrentHashMap();

    public void loadConfig(String key,String value){
        commonConfig.put(key,value);
    }


    @Override
    public void run() {
        log.info("TokenApplyTask run ...");
    }
}
