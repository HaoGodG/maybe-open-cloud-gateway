package smartTerminal.com.dc.esb.util;

import org.springframework.core.env.PropertiesPropertySource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * author Hao
 * date 2025/8/18 16:45
 */
public class PropertiesTool {
    protected PropertiesTool() {
        // Prevent instantiation
    }

    public static Properties loadByClasspath(Properties properties, String clsPathFileNAme) throws IOException {
        String fileName = MClassLoaderUtil.getResourcePath(clsPathFileNAme);
        return load(properties, fileName);
    }

    public static Properties load(Properties properties, String fileName) throws IOException {
        return load(properties, new File(fileName));
    }

    public static Properties load(Properties properties, File file) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            properties.load(fis);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return properties;
    }

    public static Properties loadByNullSafe(Properties properties, String fileName) throws IOException {
        return fileName == null ? properties : load(properties, fileName);
    }

    public static Properties loadByNullSafe(Properties properties, File file) throws IOException {
        return file == null ? properties : load(properties, file);
    }

}
