package gateway.esc.jmx.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * author Hao
 * date 2025/9/4 19:35
 */
@Data
public class RetStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    boolean flag = true;
    String function = null;
    String msg = null;
    Exception exception = null;
    String updateFileTime = null;

    public RetStatus(String function) {
        this.function = function;
    }

    public void setErrorFlag(String msg, Exception exception) {
        this.flag = false;
        this.msg = msg;
        this.exception = exception;
    }

    public void setSuccessFlag(String msg) {
        this.flag = true;
        this.msg = msg;
        this.exception = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("execute function flag=").append(flag).append(",msg:").append(msg).append("\n");
        if (exception != null) {
            sb.append("exception:").append(exception.getMessage()).append("\n");
        }
        return sb.toString();
    }
    public boolean isSuccessful() {
        return flag;
    }
}
