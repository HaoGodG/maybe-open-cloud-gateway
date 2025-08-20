package gateway.esc.startup;

import gateway.esc.eureka.EurekaClientExecutor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * author Hao
 * date 2025/8/19 15:09
 */
@Slf4j
public class CommandRunnable implements Runnable {
    ServerSocket server = null;
    boolean started = true;

    public CommandRunnable(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("CommandRunnable started on port " + port);
    }

    public void abort() throws IOException {
        started = false;
        server.close();
    }

    public void run() {
        while (started) {
            Socket socket = null;

            try {
                server.accept();
                InputStream is = socket.getInputStream();
                byte[] cache = new byte[100];
                int readLen = is.read(cache);
                if (readLen != -1) {
                    String cmd = new String(cache, 0, readLen);
                    if ("IsEureka".equalsIgnoreCase(cmd.trim())) {
                        String status = EurekaClientExecutor.getInstance().getStatus();
                        socket.getOutputStream().write(status.getBytes());
                    } else {
                        processCommand(cmd);
                    }
                } else {

                }
            } catch (IOException e) {
                log.error("scoket链接异常", e);

            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        log.error("socket关闭异常", e);
                    }
                }
            }
        }
    }

    private void processCommand(String cmd) throws IOException {
        if ("STOP".equalsIgnoreCase(cmd)) {
            abort();
            System.exit(0);
            return;
        }
        System.out.println("Unknown command: " + cmd);
    }
}
