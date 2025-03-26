import java.util.Random;

// Extract Class: Utility for generating random IDs
public class RandomGenerator {
    public static String generateRandomID() {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(1000000));
    }
}
