package me.angelvallejo.misaes.login.Events;

public class LoginEvent {

    public enum Type {
        CAPTCHA_IMAGE_DOWNLOADED,
        LOGIN_SUCCESSFUL,
        LOGIN_ERROR
    }

    private Type type;
    private String message;
    private byte[] captchaImage;

    public LoginEvent(Type type, String message, byte[] captchaImage) {
        this.type = type;
        this.message = message;
        this.captchaImage = captchaImage;
    }

    public byte[] getCaptchaImage() {
        return captchaImage;
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
