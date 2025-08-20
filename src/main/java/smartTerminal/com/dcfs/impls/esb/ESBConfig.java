package smartTerminal.com.dcfs.impls.esb;

import java.io.File;

/**
 * author Hao
 * date 2025/8/19 14:40
 */
public class ESBConfig {
    private static String instroot = null;
    private static String confRootDirPath = null;


    public static void setRoot(String path) {
        String rootPath = path;
        if ((!isAbsolutePath(path))) {
            rootPath = (new File(path)).getAbsolutePath();
        }
        instroot=rootPath;
        confRootDirPath=rootPath;
    }


    private static boolean isAbsolutePath(String url) {
        if (url != null && url.length() >= 1) {
            return !url.startsWith(".");
        } else {
            return true;
        }
    }
}
