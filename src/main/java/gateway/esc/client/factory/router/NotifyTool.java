package gateway.esc.client.factory.router;

import gateway.esc.client.terminal.CommUri;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.swing.plaf.PanelUI;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author Hao
 * date 2025/9/3 19:55
 */
@Slf4j
@Data
public class NotifyTool {
    private static final String NAME = "name";

    private String name = null;

    private String timestamp = null;

    private String parenteName   = null;

    private String nodeName = null;

    private List<NotifyTool> notifyList = null;

    private static NotifyTool root = null;

    private static Map<String,List<String>> changeMap= new HashMap<>();

    public static final String MSP_COMMON = "MSP.common";

    public static final String COMMON_PROTOCOLTYPE = "protocolDefinition";

    public static final String COMMON_BASESERVICE = "baseService";

    public static final String COMMON_METADATA = "metadata";

    public static final String DEV_CERTS = "iobp.itfin.api.openCerts";

    public static final String DEV_SERIAL_NOS = "iobp.itfin.api.openSernos";

    public static final String DEV_APIAUTH = "iobp.itfin.api.openAuth";

    public static final String ROUTER_ROUTER = "router";

    public static final String ROUTER_ROUTER_CENTER="router_cent";

    public static final String ROUTER_BALANCE="balance";

    public static final String ROUTER_FAULT="fault";

    public static final String ROUTER_ADAPTER="adapter";

    public static final String TERMINAL_SERVICEDEFINITION="serviceDefinition";

    public static final String TERMINAL_SERVICEREGISTER="serviceRegister";

    public static final String TERMINAL_SERVICEAUTH="serviceAuth";

    public static final String TERMINAL_PROTOCOL="protocol";

    public static final String TERMINAL_SERVICEIDENTIFY="serviceIdentify";

    public static final String CF_SERVICECONFIG="CFConfig";
    public static final String PT_SERVICECONFIG="PTConfig";
    public static final String PF_SERVICECONFIG="PFConfig";
    public static final String CT_SERVICECONFIG="CTConfig";

    public static final String TERMINAL_SERVICEDEFINITIONFILES="serviceDefinitionFiles";

    public static final String TERMINAL_SERVICEREGISTERFILES="serviceRegisterFiles";

    public static final String TERMINAL_THREADFACTORY="threadPool";

    public static final String FLOW_CTRL="flowCtrl";

    public static final String COMMON_ROSTERS="roster";

    public static final String COMMON_PING="pin";

    public static final String TERMINAL_HYTRIX="hytrix";

    public static final String TERMINAL_FLOWCTRL="flowCtrl";

    public static final String EXCEPTION_TYPE="exceptionType";

    public static final String BUSINESS_CONFIG="businessConfig";

    public static final String CONFIG_XWD_HEAD="config-xwd-head";

    public static final String TERMINAL_CONFIG="config";

    public static final String TERMINAL_LOG4J2="log4j2-spring";

    public static final String TERMINAL_RSA_CONF="rsa";

    public static final String TERMINAL_CONF_SAFE="safe";

    public static final String TRANS_CODE="transCode";

    public static final String SYSTEM_INFO="systemInfo";

    public static final String CONFIG_INFO="configInfo";

    public static final String IP_MAC_WHITE_LIST="iobp.itfin.api.ipmacWhite";

    public static final String HYSTRIXINFO="hystrixInfo";

    public static NotifyTool getRoot(){
        root=null;
        init();
        return root;
    }

    private static void init(){
        if (root==null){
            root=new NotifyTool();
            root.setName("root");
            root.setParenteName("");
            root.setTimestamp("");
        }
        List<NotifyTool> toolList= new ArrayList<>();
        try {
            File commonFile=new File(CommUri.NOTIFY_COMMON_FILE);
        }
    }


}
