package smartTerminal.com.dc.esb.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * author Hao
 * date 2025/8/8 17:33
 */
public class MClassLoaderUtil {

    public static String dfltEncName = "UTF-8";
    public static boolean isDecodePath = true;

    public static String getBaseResourcePath() {
        URL url = findClassLoader().getResource(".");
        if (url == null) {
            url = findClassLoader().getResource("");
        }
        return getUrlFileAndDecode(url);
    }

    public static ClassLoader findClassLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = ClassLoader.getSystemClassLoader();
        }
        return loader;
    }

    public static String getUrlFileAndDecode(URL url) {
        return url == null ? null : decodePath(url.getFile());
    }

    public static String decodePath(String path) {
        if (!isDecodePath) {
            return path;
        } else if (path == null) {
            return null;
        } else {
            try {
                return URLDecoder.decode(path, dfltEncName);

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncoding: " + dfltEncName, e);
            }
        }
    }

    public static String getResourcePath(String fileClassesPath) {
        URL url = findClassLoader().getResource(fileClassesPath);
        return getUrlFileAndDecode(url);
    }

    public static String getResourceFile(String fileClassesPath) {
        URL url = findClassLoader().getResource(fileClassesPath);
        return getUrlFileAndDecode(url);
    }
}
