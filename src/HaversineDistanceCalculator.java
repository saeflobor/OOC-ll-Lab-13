public class HaversineDistanceCalculator implements DistanceCalculator {
    @Override
    public String[] calculate(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double distance = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        distance = Math.acos(distance);
        distance = Math.toDegrees(distance);
        distance = distance * 60 * 1.1515;
        return new String[]{
                String.format("%.2f", distance * 0.8684),
                String.format("%.2f", distance * 1.609344)
        };
    }
}
