package me.angelvc.misaes.login.Events;

public class LoginEvent {

    public enum Type {
        CAPTCHA_IMAGE_DOWNLOADED,
        LOGIN_SUCCESSFUL,
        LOGIN_ERROR
    }

    private Type type;
    private String message;
    private byte[] captchaImage;
    private String user;
    private String password;
    private boolean rememberMe;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMeChecked() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
