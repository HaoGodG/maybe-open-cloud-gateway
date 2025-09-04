package smartTerminal.com.dc.esb.container.sclite;

/**
 * author Hao
 * date 2025/9/4 20:24
 */
public interface IService {
    IServiceDataObject invoke(IserviceDataObject var1) throws InvokeException;

    void start() throws ServiceMaintainException;

    void stop() throws ServiceMaintainException;

    boolean isStarted();
}
