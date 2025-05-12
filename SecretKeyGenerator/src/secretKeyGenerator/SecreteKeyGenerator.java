package secretKeyGenerator;
import java.security.SecureRandom;
import java.util.Base64;

public class SecreteKeyGenerator {
    public static void main(String[] args) {
        byte[] key = new byte[64]; // 64 bytes = 512 bits
        new SecureRandom().nextBytes(key);
        String secret = Base64.getEncoder().encodeToString(key);
        System.out.println("Secure JWT Secret:");
        System.out.println(secret);
    }
}
