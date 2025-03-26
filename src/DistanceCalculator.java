// Replace Conditional with Polymorphism: Strategy interface for distance calculation
public interface DistanceCalculator {
    String[] calculate(double lat1, double lon1, double lat2, double lon2);
}
