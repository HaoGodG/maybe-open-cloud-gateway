package gateway.esc.jmx.bean;

import gateway.esc.client.terminal.Terminal;
import gateway.esc.startup.info.ESCStartInfo;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import smartTerminal.com.dc.esb.container.sclite.ServiceContainer;
import smartTerminal.com.dc.esb.startup.info.LoadItem;

import java.io.File;
import java.util.List;

/**
 * author Hao
 * date 2025/9/4 17:38
 */
@Slf4j
public class BaseService implements BaseServiceMBean {
    private String baseServiceConfig = null;

    public BaseService() {
        this.baseServiceConfig = getRootPath();
    }

    public String getRootPath() {
        String root = null;
        try {
            root = Terminal.getInstance().getConfigRoot();
        } catch (Exception e) {
            log.error("getRootPath error", e);
            root = "../esc_conf";
        }
        return root + "/common/baseService.xml";
    }

    public RetStatus addBaseService(String id){
        RetStatus status=new RetStatus(BaseService.class+"addBaseService");
        try {
            loadOneService(id);
            status.setSuccessFlag("start ok");
        }catch (Exception e){
            log.error("BaseService load error",e);
            status.setErrorFlag("BaseService load error",e);
        }
        return status;
    }

    public void loadOneService(String id){
        File file = new File(this.baseServiceConfig);
        boolean flag = ESCStartInfo.checkFileIsUpdate(file);
        if (!flag){
            return;
        }
        if (file.exists() && file.getName().endsWith(".xml")) {
            Document doc;
            try {
                doc= new SAXReader().read(file);
                List<Node>list =doc.selectNodes("//composite//component");
                for (Node node:list){
                    String name=node.selectSingleNode("@name").getText();
                    if (id.equals(name)){
                        registerBaseService(name,node);
                        return;
                    }
                }
            } catch (Exception e) {
                log.error("load baseService error", e);
            }
        }
    }

    public void registerBaseService(String name, Node node) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        LoadItem loadItem = new LoadItem("loadBaseService",name);
        try{
            String serviceInterface=null;
            String serviceImpl=null;
            String model="";
            Node interfceNode=node.selectSingleNode("interface.java");
            if (interfceNode!=null){
                serviceInterface=interfceNode.selectSingleNode("@interface").getText().trim();
            }else {
                serviceInterface="com.dc.esb.container.core.sclite.IService";
            }
            Node ServiceImplNode=node.selectSingleNode("implementation.java");
            if (ServiceImplNode!=null){
                serviceImpl=ServiceImplNode.selectSingleNode("@class").getText().trim();
            }else {
                serviceImpl=serviceInterface;
            }
            Node ModelNode=node.selectSingleNode("model");
            if (ModelNode!=null){
                model=ModelNode.getText().trim();
            }
            ServiceContainer.getInstance().registerService(name,null,serviceImpl);
            loadItem.setSuccessMsg("load base service ok",this.baseServiceConfig);
        }

    }
}
