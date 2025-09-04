package gateway.esc.client.terminal;

/**
 * author Hao
 * date 2025/9/3 20:03
 */
public class CommUri {

    public static final String ROuTER_RUL="router/router";

    public static final String NOTIFY_COMMON_URI="/notify/common";

    public static final String NOTIFY_COMMON_FILE=getXmlFile(NOTIFY_COMMON_URI);

    public static String getTmnl(String path){
        return "Configs/tmnl"+Terminal.getInstance().getTerminalId()+path;
    }

    public static String getFile(String path){
        return Terminal.getInstance().getConfigRoot()+path;
    }

    public static String getXmlFile(String path){
        return Terminal.getInstance().getConfigRoot()+path+".xml";
    }
}
