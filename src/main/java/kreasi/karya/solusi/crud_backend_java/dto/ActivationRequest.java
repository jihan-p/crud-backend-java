package kreasi.karya.solusi.crud_backend_java.dto;

public class ActivationRequest {
    private String email;
    private String token;

    public ActivationRequest() {
    }

    public ActivationRequest(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
