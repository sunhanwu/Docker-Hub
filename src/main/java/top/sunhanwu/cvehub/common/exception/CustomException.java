package top.sunhanwu.cvehub.common.exception;

public class CustomException extends RuntimeException{

    private int code;

    public CustomException(int code, String msg)
    {
        super(msg);
        this.setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
