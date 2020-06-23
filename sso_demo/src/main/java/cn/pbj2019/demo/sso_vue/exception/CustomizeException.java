package cn.pbj2019.demo.sso_vue.exception;


public class CustomizeException extends RuntimeException {
    public CustomizeException() {
        super();
    }

    public CustomizeException(String message) {
        super(message);
    }

    public CustomizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomizeException(Throwable cause) {
        super(cause);
    }

    protected CustomizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
