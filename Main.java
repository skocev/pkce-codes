import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * https://www.rfc-editor.org/rfc/rfc6749 The OAuth 2.0 Authorization Framework
 * https://www.rfc-editor.org/rfc/rfc7636 Proof Key for Code Exchange(PKCE) by OAuth Public Clients
 * https://www.keycloak.org/ Open Source Identity and Access Management
 */
public class Main {
    // [A-Z] / [a-z] / [0-9] / "-" / "." / "_" / "~"
    private static final String[] POOL = new String[] {
            "A", "a", "B", "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g",
            "H", "h", "I", "i", "J", "j", "K", "k", "L", "l", "M", "m", "N", "n",
            "O", "o", "P", "p", "Q", "q", "R", "r", "S", "s", "T", "t", "U", "u",
            "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z", "0", "1", "2", "3",
            "4", "5", "6", "7", "8", "9", "-", ".", "_", "~"
    };

    /**
     * code_verifier = high-entropy cryptographic random STRING
     * using the unreserved characters [A-Z] / [a-z] / [0-9] / "-" / "." / "_" / "~"
     * with a minimum length of 43 characters and a maximum length of 128 characters
     */
    private static String generateCodeVerifier() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            int random = (int) Math.floor(Math.random() * POOL.length);
            builder.append(POOL[random]);
        }
        return builder.toString();
    }

    private static byte[] sha256(String str) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256")
                .digest(str.getBytes(StandardCharsets.UTF_8));
    }

    private static String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(bytes);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String codeVerifier = generateCodeVerifier();
        // code_challenge = BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
        String codeChallenge = base64UrlEncode(sha256(codeVerifier));
        System.out.println("code_verifier: " + codeVerifier);
        System.out.println("code_challenge: " + codeChallenge);
    }
}
