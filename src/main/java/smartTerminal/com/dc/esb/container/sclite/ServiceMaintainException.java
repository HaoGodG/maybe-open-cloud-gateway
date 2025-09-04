package smartTerminal.com.dc.esb.container.sclite;

/**
 * author Hao
 * date 2025/9/4 22:01
 */
public class ServiceMaintainException extends Exception{

    public ServiceMaintainException(String message){
        super(message);
    }

    public ServiceMaintainException(Exception e){
        super(e);
    }
}
