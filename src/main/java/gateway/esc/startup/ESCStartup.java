package gateway.esc.startup;

import gateway.esc.client.terminal.Terminal;
import gateway.esc.startup.info.ESCStartInfo;
import gateway.esc.terminal.cons.CfgKeyCons;
import gateway.esc.until.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import smartTerminal.com.dc.esb.startup.info.LoadItem;
import smartTerminal.com.dc.esb.util.MClassLoaderUtil;
import smartTerminal.com.dc.esb.util.PropertiesTool;
import smartTerminal.com.dcfs.impls.esb.ESBConfig;

import java.io.File;
import java.io.IOException;
import java.util.Properties;


/**
 * author Hao
 * date 2025/8/8 16:38
 */

@Slf4j
public class ESCStartup {

    static String deploy_path = null;

    static String config_root_path = null;

    static String app = null;


    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("ESCStartup args is null or empty");
            System.exit(1);
        }
    }

    private static void init() {
        ESBConfig.setRoot(config_root_path);
        try {

            startCommandor();

        } catch (IOException e) {
            System.out.println("ESCStartup startCommandor error, please check your system property 'esb.command.port' is set or not");
            System.exit(1);
        }
    }

    private static void startCommandor() throws IOException {
        String cmdPort = System.getProperty(CfgKeyCons.ESB_COMMAND_PORT);
        if (cmdPort == null || cmdPort.length() == 0) {
            throw new IOException("System property 'esb.command.port' is not set");
        }
        int port = -1;
        try {
            port = Integer.parseInt(cmdPort);
        } catch (Exception e) {
            throw new IOException("System property 'esb.command.port' is not a proper Integer");
        }
        CommandRunnable cr = new CommandRunnable(port);
        new Thread(cr, "CommandRunnable-Thread").start();
    }

    public static void start(String[] args) throws Exception {
        long start = System.nanoTime();
        init();
        if (args.length == 1 || args.length == 2) {
            LoadItem paramsCenterRefresh = ESCStartInfo.getParamCenterRefresh();
            try {
                if (Terminal.getInstance().isUserApollo()) {
                    System.out.println("begin to start Apollo client");
                    Environment environment = ApplicationContextUtil.getBeanByClass(Environment.class);
                    System.out.println("apollo.env: ");
                    System.out.println("apollo.appId :");
                    System.out.println("apollo.user :");
                    System.out.println("apollo.cluster :");
                    System.out.println("apollo.cacheDir :");
                    System.out.println("apollo.meta :");
                    System.out.println("apollo.readTimeout :");
                    System.out.println("apollo.connTimeout :");
                    System.out.println("apollo.api.url :");
                    System.out.println("apollo.api.token :");
                    System.out.println("apollo.api.readTimeout :");
                    System.out.println("apollo.api.connTimeout :");

                    TerminalListener.startListeners();
                    System.out.println("Apollo client is started.");

                } else {
                    NotifyconfigFactory.getInstance();
                    Terminal.getInstance().refreshConfig();
//                    TerminalListener.startListeners();
                    System.out.println("Zookeeper client Terminal is started.");

                    new Thread(new ZookeeperListenerDeamon(), "ZookeeperListenerDeamon-Thread").start();
                    Thread.sleep(2000);
                }

                paramsCenterRefresh.setSuccessMsg("param center client startup ok", null);
                ESCStartupInfo.setParamsCenterRegresh(paramsCenterRefresh);
            } catch (Exception e) {
                System.out.println("配置中心启动异常" + e.getMessage());
                paramsCenterRefresh.setFailedMsg("param center client startup failed", null);
                ESCStartupInfo.setParamsCenterRegresh(paramsCenterRefresh);
            }

            app=args[0];
            System.out.println("Ready to start ESC ......");
            System.out.println("\t-->Deploy path is: " + deploy_path);
            System.out.println("\t-->Config root path is: " + config_root_path);
            System.out.println("\t-->ESC Terminal app is: " + app);

            String installPath=config_root_path;
            System.setProperty(CfgKeyCons.ESC_INSTALL_PATH,installPath);

            new ContainerLayncherMain().start();

            LoadItem jmxServerItem = ESCStartInfo.getJmxServer();
            try {
                JMXServer s = new JMXServer();
                s.register();

                jmxServerItem.setSuccessMsg("JMX server startup ok", null);
                ESCStartInfo.setJmxServer(jmxServerItem);
                System.out.println("JMX server is started.");
            }catch (Exception e){
                jmxServerItem.setFailedMsg("JMX server startup failed", null);
                ESCStartInfo.setJmxServer(jmxServerItem);
                System.out.println("JMX server startup failed, reason is "+e.getMessage());
            }

            MonitorStarter.start();
            System.out.println("Monitor is started.");
            JournalWorkMain.start();
            FlowIntTimeCheckThread journalCheckThread = new FlowIntTimeCheckThread();
            journalCheckThread.start();
            LoadItem startFlagItem=ESCStartInfo.getStartFlag();
            startFlagItem.setSuccessMsg("ESC startup ok", null);
            ESCStartInfo.setStartFlag(startFlagItem);

            startGCTask();

            String threadPoolEnable=ESCConfig.getConfig().getProperty(ESCConfig.THEADPOOLMONITOR_ENABLE)==null?"false":ESCConfig.getConfig().getProperty(ESCConfig.THEADPOOLMONITOR_ENABLE,false);
            if ("true".equalsIgnoreCase(threadPoolEnable)){
            startTheadPoolTask();
            startFlowReasourceTask();
            }
            ESCStartInfo.isStartLoad=false;
            String allLoadInfo=ESCStartInfo.checkAllLoadInfo();
            new HTTPHystrixDataHandler("GATEWAY","init",null,null,null,null);
            AdaptorFactory.getAdaptorFrame();
            String localIp=Terminal.getInstance().getIp();
            System.setProperty("local.ip",localIp);
            System.out.println("获取本机IP:"+localIp);
            long end = System.nanoTime();
            System.out.println(allLoadInfo);
            System.out.println("ESC is started successfully, time consuming " + (end - start) / 1000000 + " ms.");
        }else {
            System.out.println("ESCStartup args error, need 1 or 2 args, but now is " + args.length);
            System.exit(1);
        }
    }

    /*
     * 配置系统变量
     */
    public static void initSystemProperties() throws IOException {
        // 初始化系统属性
//        System.setProperty("esc.startup", "true");
//        log.info("ESCStartup system properties initialized.");
        System.out.println("License验证开始");
        System.setProperty("com.bis.install_path", "../bin");
        System.out.println("License验证成功");


        // 设置部署路径
        try {
            deploy_path = new File(MClassLoaderUtil.getBaseResourcePath()).getCanonicalPath();
            String rootPath = MClassLoaderUtil.getBaseResourcePath();
        } catch (Exception e) {

            deploy_path = System.getProperty(CfgKeyCons.ESB_DEPLOY_PATH);
            if (deploy_path == null) {
                System.out.println("Please set system property $ESB_DEPLOY_PATH");
                System.exit(1);
            }

        }
        //配置文件路径
        String terminalConfigPath = MClassLoaderUtil.getResourcePath("config-local.properties");
        if (terminalConfigPath == null) {
            terminalConfigPath = MClassLoaderUtil.getResourcePath("config.properties");
        }
        System.setProperty(CfgKeyCons.ESC_TERMINAL_CONF_FILEPATH, terminalConfigPath);
        //配置根目录
        config_root_path = MClassLoaderUtil.getBaseResourcePath() + "esc_config";
        System.setProperty(CfgKeyCons.ESC_TERMINAL_CONFIG_PATH, config_root_path);

        //其他配置
        Properties properties = new Properties();
        PropertiesTool.loadByClasspath(properties, terminalConfigPath);
        PropertiesTool.loadByNullSafe(properties, MClassLoaderUtil.getResourceFile("config-local.properties"));
        System.setProperty(CfgKeyCons.ESB_COMMAND_PORT, properties.getProperty(CfgKeyCons.ESB_COMMAND_PORT));
        System.setProperty("java.rmi.server.hostname", Terminal.getInstance().getIp());
    }

    public static void initSystemPropertyByBoot() throws IOException {
        System.out.println("License验证开始");
        System.setProperty("com.bis.install_path", "../bin");
        System.out.println("License验证成功");
        try {
            deploy_path = new File(MClassLoaderUtil.getBaseResourcePath(), "../").getCanonicalPath();
            String rootPath = MClassLoaderUtil.getBaseResourcePath();
            if (rootPath.indexOf(".jar") != -1) {
                File rootFile = new File(rootPath);
                while (rootFile.getName().indexOf(".jar") != -1) {
                    rootFile = rootFile.getParentFile();
                    if (log.isInfoEnabled()) {
                        log.info("rootFile is:" + rootFile);
                    }
                }
                if (log.isInfoEnabled()) {
                    log.info("rootFile is:" + rootFile);
                }
                rootFile = rootFile.getParentFile();
                if (log.isInfoEnabled()) {
                    log.info("rootFile is:" + rootFile);
                }
                deploy_path = rootFile.getPath().replace("file:", "");
                if (log.isInfoEnabled()) {
                    log.info("deploy_path is:" + deploy_path);
                }
            }
        } catch (IOException e) {
            deploy_path = System.getProperty(CfgKeyCons.ESB_DEPLOY_PATH);
            if (deploy_path == null) {
                System.out.println("Please set system property $ESB_DEPLOY_PATH");
                System.exit(1);
            }
        }
        String configRoot = deploy_path + File.separator + "config" + File.separator;
        try {
            String terminalConfigPath = configRoot + "config-local.properties";
            if (!new File(terminalConfigPath).exists()) {
                terminalConfigPath = configRoot + "config.properties";
            }
            System.setProperty(CfgKeyCons.ESC_TERMINAL_CONF_FILEPATH, terminalConfigPath);
            config_root_path = configRoot + "esc_config";
            System.setProperty(CfgKeyCons.ESC_TERMINAL_CONFIG_PATH, config_root_path);
            if (log.isInfoEnabled()) {
                log.info("rootPath is:" + terminalConfigPath);
            }
            Properties properties = new Properties();
            PropertiesTool.loadByNullSafe(properties, new File(terminalConfigPath));
            PropertiesTool.loadByNullSafe(properties, MClassLoaderUtil.getResourceFile("config-local.properties"));
            System.setProperty(CfgKeyCons.ESB_COMMAND_PORT, properties.getProperty(CfgKeyCons.ESB_COMMAND_PORT));
            System.setProperty("java.rmi.server.hostname", Terminal.getInstance().getIp());

        } catch (Exception e) {
            if (log.isInfoEnabled()) {
                log.info("error is :" + e);
            }
        }
    }
}
