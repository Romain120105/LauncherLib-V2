package fr.romain120105.launcher.auth;


import fr.romain120105.launcher.auth.responses.LoginResponse;

public class Credential extends LoginResponse {

    private Profile[] availableProfiles;

    public Credential(String accessToken, String clientToken, Profile selectedProfile, Profile[] availableProfiles) {
        super(accessToken, clientToken, selectedProfile);
        this.availableProfiles = availableProfiles;
    }

    public Profile[] getAvailableProfiles() {
        return availableProfiles;
    }

}
