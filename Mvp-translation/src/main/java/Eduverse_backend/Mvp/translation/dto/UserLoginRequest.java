package Eduverse_backend.Mvp.translation.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String identifier;
    private String password;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String email) {
        this.identifier = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
