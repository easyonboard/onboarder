package config;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public final class Constants {
    private static Map<String, String> signinKeysForUser;
    public static final String AUTHENTICATION_REQUEST_PREFIX = "Bearer ";

    public static void addToSigninKeysForUserMap(String username, String signinKey) {
        if (null == signinKeysForUser) {
            signinKeysForUser = new LinkedHashMap<String, String>();
        }
        signinKeysForUser.put(username, signinKey);
    }

    public static String getSigninKeyForUser(String token) {
        return signinKeysForUser.get(token);
    }

    public static String generateSigningKey(String username) {

//        byte[] array = new byte[2];
//        new Random().nextBytes(array);
//        String signinKey = new String(array, Charset.forName("UTF-8"));
        String signinKey = "blabla";
        return signinKey;
    }


}
