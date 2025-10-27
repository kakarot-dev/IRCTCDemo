public class Train {
    private int trainId;
    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private int totalSeats;
    private double fare;
    private String departureTime;
    private String arrivalTime;
    private String journeyDuration;

    // Getters and Setters
    public int getTrainId() { return trainId; }
    public void setTrainId(int trainId) { this.trainId = trainId; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getJourneyDuration() { return journeyDuration; }
    public void setJourneyDuration(String journeyDuration) { this.journeyDuration = journeyDuration; }

    @Override
    public String toString() {
        return trainNumber + " - " + trainName + " (" + source + " to " + destination + ")";
    }
}
