package fr.romain120105.launcher.auth;

import fr.romain120105.launcher.Launcher;
import fr.romain120105.launcher.auth.exceptions.AuthenticationUnavailableException;
import fr.romain120105.launcher.auth.exceptions.InvalidCredentialsException;
import fr.romain120105.launcher.auth.exceptions.RequestException;
import fr.romain120105.launcher.auth.exceptions.UserMigratedException;
import fr.romain120105.launcher.auth.responses.ErrorResponse;
import fr.romain120105.launcher.auth.responses.RequestResponse;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.Proxy;
import java.net.URL;
import java.util.Map;

public class Auth {

    public static AuthAgent MINECRAFT_AUTH_AGENT = new AuthAgent("Minecraft", "https://authserver.mojang.com/");


    public static Credential autorize(Launcher launcher, UserIdentifiers identifiers) throws RequestException, AuthenticationUnavailableException{
        AuthAgent agent = launcher.getAuthAgent();

        RequestResponse result = sendJsonPostRequest(getRequestUrl(agent, "authenticate"), JsonUtils.credentialsToJson(identifiers.getEmail(), identifiers.getPassword(), null), Proxy.NO_PROXY);
        if (result.isSuccessful()) {
            String accessToken = (String) result.getData().get("accessToken");
            String rClientToken = (String) result.getData().get("clientToken");
            Profile selectedProfile = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData().get("selectedProfile")), Profile.class);
            Profile[] availableProfiles = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData().get("availableProfiles")), Profile[].class);
            return new Credential(accessToken, rClientToken, selectedProfile, availableProfiles);
        } else {
            ErrorResponse errorResponse = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData()), ErrorResponse.class);
            if (result.getData().get("cause") != null && ((String) (result.getData().get("cause"))).equalsIgnoreCase("UserMigratedException")) {
                throw new UserMigratedException(errorResponse);
            } else {
                throw new InvalidCredentialsException(errorResponse);
            }
        }

    }

    private static URL getRequestUrl(AuthAgent agent, String request) {
        try {
            return new URL(agent.getAgentURL() + request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static RequestResponse sendJsonPostRequest(URL requestUrl, String payload, Proxy proxy) throws AuthenticationUnavailableException {
        HttpsURLConnection connection = null;
        try {
            byte[] payloadBytes = payload.getBytes("UTF-8");
            connection = (HttpsURLConnection) (proxy != null ? requestUrl.openConnection(proxy) : requestUrl.openConnection());
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(payloadBytes.length));
            connection.setUseCaches(false);
            OutputStream out = connection.getOutputStream();
            out.write(payloadBytes, 0, payloadBytes.length);
            out.close();

            int responseCode = connection.getResponseCode();
            String line;
            BufferedReader reader = null;
            String response;
            switch (responseCode) {
                case 200:
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    response = reader.readLine();
                    break;
                case 204:
                    response = "";
                    break;
                default:
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));
                    response = reader.readLine();
                    break;
            }
            if (reader != null) {
                reader.close();
            }
            Map<String, Object> map = JsonUtils.gson.fromJson(response, JsonUtils.stringObjectMap);
            return new RequestResponse(responseCode, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationUnavailableException(null);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static AuthAgent createAuthAgent(String name, String url){
        return new AuthAgent(name, url);
    }

}
