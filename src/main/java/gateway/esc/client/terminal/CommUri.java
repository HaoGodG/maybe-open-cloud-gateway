package gateway.esc.client.terminal;

/**
 * author Hao
 * date 2025/9/3 20:03
 */
public class CommUri {

    public static final String ROuTER_RUL="router/router";

    public static String getTmnl(String path){
        return "Configs/tmnl"+Terminal.getInstance().getTerminalId()+path;
    }

    public static String getFile(String path){
        return Terminal.getInstance().getConfigRoot()+path;
    }
}
