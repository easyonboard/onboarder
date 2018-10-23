package config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class Constants {
    private static Map<String, String> signinKeysForUser;
    public static final String AUTHENTICATION_REQUEST_PREFIX = "Bearer ";
    private static StringBuilder alphabet = new StringBuilder();

    static {
        for (char c = 32; c <= 126; c++) {
            alphabet.append(c);
        }
    }

    public static void addToSigninKeysForUserMap(String username, String signinKey) {
        if (null == signinKeysForUser) {
            signinKeysForUser = new LinkedHashMap<>();
        }
        signinKeysForUser.put(username, signinKey);
    }

    public static String getSigninKeyForUser(String token) {
        return signinKeysForUser.get(token);
    }

    public static String generateSigningKey() {
        StringBuilder signInKey = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            signInKey.append(ThreadLocalRandom.current().nextInt(alphabet.length()+1)); //+1 because nextInt method excludes the upper bound
        }
        return signInKey.toString();
    }

}
