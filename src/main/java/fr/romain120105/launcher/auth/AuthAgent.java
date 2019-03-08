package fr.romain120105.launcher.auth;

public class AuthAgent {

    private String agentName;
    private String agentURL;

    public AuthAgent(String agentName, String agentURL){
        this.agentName = agentName;
        this.agentURL = agentURL;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getAgentURL() {
        return agentURL;
    }
}
