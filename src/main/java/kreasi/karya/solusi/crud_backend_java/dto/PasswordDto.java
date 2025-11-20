package kreasi.karya.solusi.crud_backend_java.dto;

public class PasswordDto {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String token;

    public PasswordDto() {
    }

    public PasswordDto(String email, String oldPassword, String newPassword, String token) {
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
