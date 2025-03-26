// Abstract class extended by Flight (no changes in logic)
public abstract class FlightDistance {

    // g
    public double distanceBetweenCities(String fromCity, String toCity) {
        return Distance.getCalculator().calculate(fromCity, toCity);
    }
}