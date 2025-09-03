package gateway.esc.client.terminal;

/**
 * author Hao
 * date 2025/9/3 20:04
 */
public class TerminalUri extends CommUri {
    private String tmnlid = null;
    private String tmnlRoot = null;

    private String metaDataPath = "/metadata";
    private String consumerPath = "/consumer";
    private String consumerServiceAuthPath = "/consumer/authCtrl";
    private String consumerIpsPath = "/consumer/ips";
    private String consumerIdentifyPath = "/consumer/identify";
    private String protocolPath = "/protocol";
    private String protocolServerPath = "protocol";
    private String protocolDefinitionPath = "/protocol/protocolDefinition";
    private String providerPath = "/provider";
    private String providerRegisterInfoPath = "/provider/protocolDefinition";
    private String providerServicePath = "/provider/service";
    private String providerAdapterPath = "/provider/adapter";
    private String flowCtrlPath = "/provider/flowCtrl";

    private String metaDataUri = null;
    private String consumerUri = null;
    private String consumerServiceAuthUri = null;
    private String consumerIpsUri = null;
    private String consumerIdentifyUri = null;
    private String protocolUri = null;
    private String protocolServerUri = null;
    private String protocolDefinitionUri = null;
    private String providerUri = null;
    private String providerRegisterInfoUri = null;
    private String providerServiceUri = null;
    private String providerAdapterUri = null;
    private String flowCtrlUri = null;

    private String metaDataFile = null;
    private String consumerFile = null;
    private String consumerServiceAuthFile = null;
    private String consumerIpsFile = null;
    private String consumerIdentifyFile = null;
    private String protocolFile = null;
    private String protocolServerFile = null;
    private String protocolDefinitionFile = null;
    private String providerFile = null;
    private String providerRegisterInfoFile = null;
    private String providerServiceFile = null;
    private String providerAdapterFile = null;
    private String flowCtrlFile = null;

    private String notifyTerminalUri;

    public TerminalUri(String tmnlId) {
        tmnlid = tmnlId;
        tmnlRoot = "Configs/tmnl" + tmnlid;

        metaDataUri=getTmnl(this.metaDataPath);
        consumerUri= getTmnl(this.consumerPath);
        consumerServiceAuthUri= getTmnl(this.consumerServiceAuthPath);
        consumerIpsUri= getTmnl(this.consumerIpsPath);
        consumerIdentifyUri= getTmnl(this.consumerIdentifyPath);
        protocolUri= getTmnl(this.protocolPath);
        protocolServerUri= getTmnl(this.protocolServerPath);
        protocolDefinitionUri= getTmnl(this.protocolDefinitionPath);
        providerUri= getTmnl(this.providerPath);
        providerRegisterInfoUri= getTmnl(this.providerRegisterInfoPath);
        providerServiceUri= getTmnl(this.providerServicePath);
        providerAdapterUri= getTmnl(this.providerAdapterPath);
        flowCtrlUri= getTmnl(this.flowCtrlPath);

        metaDataFile = getFile(this.metaDataPath);
        consumerFile= getFile(this.consumerPath);
        consumerServiceAuthFile= getFile(this.consumerServiceAuthPath);
        consumerIpsFile= getFile(this.consumerIpsPath);
        consumerIdentifyFile= getFile(this.consumerIdentifyPath);
        protocolFile= getFile(this.protocolPath);
        protocolServerFile= getFile(this.protocolServerPath);
        protocolDefinitionFile= getFile(this.protocolDefinitionPath);
        providerFile= getFile(this.providerPath);
        providerRegisterInfoFile= getFile(this.providerRegisterInfoPath);
        providerServiceFile= getFile(this.providerServicePath);
        providerAdapterFile= getFile(this.providerAdapterPath);
        flowCtrlFile= getFile(this.flowCtrlPath);
        notifyTerminalUri="/notify/terminal/"+tmnlid;

    }
}
