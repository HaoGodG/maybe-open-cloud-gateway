package smartTerminal.com.dc.esc.client.zookeeper;

import lombok.extern.slf4j.Slf4j;

/**
 * author Hao
 * date 2025/9/1 14:02
 */
@Slf4j
public class ConfigFile {
    String fileName;
    String tempFileName;
    String oldFileName;

    public ConfigFile(String file) {
        String file1=file.replace(".xml.xml",".xml");
        this.fileName = file1;
        this.tempFileName = file1+".temp";
        this.oldFileName = file1+".old";
    }
}
