package gateway.esc.client.factory.listener;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import gateway.esc.client.factory.router.NotifyTool;
import gateway.esc.client.terminal.Terminal;
import gateway.esc.client.terminal.TerminalUri;
import gateway.esc.startup.TokenApplyTask;

/**
 * author Hao
 * date 2025/9/3 19:44
 */
public class ApolloConfigListener {
    public static final String Jar_PARSER_PATH = System.getProperty("user.dir")+"/jar/parser.jar/"+"Parser_";
    public static final String Jar_PACKER_PATH = System.getProperty("user.dir")+"/jar/PACKER.jar/"+"Packer_";

    static TerminalUri uri = Terminal.getInstance().getUri();

    public static void start(){
        Config MSPConfig= ConfigService.getConfig(NotifyTool.MSP_COMMON);
        MSPConfig.addChangeListener(changeEvent->{
            for (String key:changeEvent.changedKeys()){
                new TokenApplyTask().loadConfig(key,MSPConfig.getProperty(key,""));
            }
        });
    }


}
