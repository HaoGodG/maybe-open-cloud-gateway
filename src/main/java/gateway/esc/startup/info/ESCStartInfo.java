package gateway.esc.startup.info;

import gateway.esc.client.terminal.Terminal;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.LogFactory;
import smartTerminal.com.dc.esb.startup.info.LoadItem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author Hao
 * date 2025/8/19 17:09
 */
@Slf4j
public class ESCStartInfo {
    public static boolean isStartLoad = true;

    private static HashMap<String, Long> _fileTimes = new HashMap<>(16);

    private static List<LoadItem> _baseService = new ArrayList<>(16);

    private static List<LoadItem> _adapterFlow = new ArrayList<>();

    private static List<LoadItem> _protocolType = new ArrayList<>();

    private static List<LoadItem> _protocol = new ArrayList<>();
    private static List<LoadItem> _service = new ArrayList<>();
    private static List<LoadItem> _authCtrl = new ArrayList<>();
    private static List<LoadItem> _identify = new ArrayList<>();
    private static List<LoadItem> _loadListener = new ArrayList<>();
    private static List<LoadItem> _specialProtocolRefresh = new ArrayList<>();

    private static LoadItem _paramCenterRefresh = new LoadItem("ParamCenterRefresh", "ParamCenterRefresh");

    public static LoadItem getParamCenterRefresh() {
        return _paramCenterRefresh;
    }

    //todo 要进一步看一下这个方法干嘛的，应该不是确认文件是否更新，而是延迟加载
    public static boolean checkFileIsUpdate(File file) {
        int updateCheckTimes = Terminal.getInstance().getUpdateCheckTimes();
        long updateCheckInterval = Terminal.getInstance().getUpdateCheckInterval();

        int currCheckTimes = 0;
        String fileName = file.getAbsolutePath();
        Long lastLoadTime = _fileTimes.get(fileName);
        if (lastLoadTime == null) {
            //todo 这里为什么文件不存在也返回true
            if (!file.exists()) {
                for (; currCheckTimes < updateCheckTimes; currCheckTimes++) {
                    log.debug("file [" + file.getName() + "] not exists,wait " + updateCheckInterval + " ms");
                    try {
                        Thread.sleep(updateCheckInterval);
                    } catch (InterruptedException e) {
                        log.error("check file is update failed!", e);
                        return false;
                    }
                    if (file.exists()) {
                        break;
                    }
                }
            }
            return true;
        }
        long lastLoadTimeLong = lastLoadTime.longValue();
        long fileTime = file.lastModified();

        for (; currCheckTimes < updateCheckTimes; currCheckTimes++) {
            if (fileTime > lastLoadTimeLong) {
                return true;
            } else {
                log.debug("file [{}] not update,wait {} ms", file.getName(), updateCheckInterval);

                try {
                    Thread.sleep(updateCheckInterval);
                } catch (InterruptedException e) {
                    log.error("check file is update failed!", e);
                    return false;
                }
            }
        }
        //todo 这个方法好像是只要不出现异常就返回true
        return true;
    }


}
