package smartTerminal.com.dc.esb.container.sclite;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.HashMap;

/**
 * author Hao
 * date 2025/9/4 20:22
 */
@Slf4j
public class ServiceContainer implements IMainTainableContainer {

    private HashMap<String, IService> components = new HashMap<>();
    private HashMap<String, String> clsNames = new HashMap<>();
    private HashMap<String, String> clsPathes = new HashMap<>();
    private static ServiceContainer container = null;

    public static ServiceContainer getInstance() {
        if (container == null) {
            container = new ServiceContainer();
        }
        return container;
    }

    private ServiceContainer(){
    }

    public void refreshConfig(InputStream in)throws ServiceMaintainException{
        log.debug("Refreshing services configurations...");
    }

    public void registerService(String name,String classPath,String className) throws ServiceMaintainException{
        synchronized(this){
            this.clsNames.put(name,className);
            this.clsPathes.put(name,classPath==null?"":classPath);
            this.reloadService(name);
        }
    }

    public void removeService(String name){
        try{
            this.stopService(name);
        }catch (ServiceMaintainException e){
            log.error("Failed to stop service ",name,e);
            return;
        }
        synchronized (this){
            this.clsNames.remove(name);
            this.clsPathes.remove(name);
            this.components.remove(name);
        }
    }

}
