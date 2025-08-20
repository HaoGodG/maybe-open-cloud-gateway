package gateway.esc.runner;

import gateway.esc.startup.ESCStartup;
import org.springframework.boot.CommandLineRunner;

/**
 * author Hao
 * date 2025/8/19 14:30
 */
public class ESCStartUpRunner implements CommandLineRunner {
    public static final String STOP="STOP";

    public void run (String... args) throws Exception {
        if (args == null || args.length == 0) {
            System.out.println("ESC Terminal args is null or empty");
            System.exit(1);
        }
        ESCStartup.initSystemPropertyByBoot();

        if (STOP.equalsIgnoreCase(args[0])) {
            System.out.println("ESC Terminal is stopping...");
            ESCStartup.stop();
        }else {
            System.out.println("============ESC Terminal is starting...===============");
            ESCStartup.start(args);
            System.out.println("============ESC Terminal started successfully.==============");
        }
    }
}
