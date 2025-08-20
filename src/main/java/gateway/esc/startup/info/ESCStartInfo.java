package gateway.esc.startup.info;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.LogFactory;
import smartTerminal.com.dc.esb.startup.info.LoadItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author Hao
 * date 2025/8/19 17:09
 */
@Slf4j
public class ESCStartInfo {
    public static boolean isStartLoad=true;

    private static HashMap<String,Long> _fileTimes= new HashMap<>(16);

    private static List<LoadItem> _baseService = new ArrayList<>(16);

    private static List<LoadItem> _adapterFlow = new ArrayList<>();

    private static List<LoadItem> _protocolType= new ArrayList<>();

    private static List<LoadItem> _protocol = new ArrayList<>();
    private static List<LoadItem> _service = new ArrayList<>();
    private static List<LoadItem> _authCtrl = new ArrayList<>();
    private static List<LoadItem> _identify = new ArrayList<>();
    private static List<LoadItem> _loadListener = new ArrayList<>();
    private static List<LoadItem> _specialProtocolRefresh = new ArrayList<>();








}
