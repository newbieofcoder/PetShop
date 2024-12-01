package hoanglv.fpoly.petshop.DTO;

public class ResponeGHN<T> {
    private int code;
    private String message;
    private T data;

    public ResponeGHN(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponeGHN() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
