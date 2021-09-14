package top.sunhanwu.cvehub.bean.response;

public class RetrieveResponse {

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "RetrieveResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
