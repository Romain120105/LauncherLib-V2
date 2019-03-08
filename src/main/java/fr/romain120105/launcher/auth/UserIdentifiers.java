package fr.romain120105.launcher.auth;

public class UserIdentifiers {

    private String email;
    private String password;

    public UserIdentifiers(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
