
public class Trip {
	private TrainLine trainLine;
	private Station startStation;
	private Station destiontion;
	private int startTime;
	private int arriveTime;
	private int zones;
	
	
	public TrainLine getTrainLine() {
		return trainLine;
	}
	public void setTrainLine(TrainLine trainLine) {
		this.trainLine = trainLine;
	}
	public Station getStartStation() {
		return startStation;
	}
	public void setStartStation(Station startStation) {
		this.startStation = startStation;
	}
	public Station getDestiontion() {
		return destiontion;
	}
	public void setDestiontion(Station destiontion) {
		this.destiontion = destiontion;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}
	public int getZones() {
		return zones;
	}
	public void setZones(int zones) {
		this.zones = zones;
	}
	
	public Trip(TrainLine trainLine, Station startStation, Station destiontion,int zones) {
		super();
		this.trainLine = trainLine;
		this.startStation = startStation;
		this.destiontion = destiontion;
		this.zones=zones;
	}
	
	public String toString() {
		return "train line: " + trainLine.getName() + "\n"
				+"start station: " + startStation.getName() + "\n"
				+"destination: " + destiontion.getName() + "\n"
				+"number of zones: " + zones + "\n"
				+"time to leave the start station: " + startTime + "\n"
				+"time to arrive at the destination: " + arriveTime;
	}
	

}
