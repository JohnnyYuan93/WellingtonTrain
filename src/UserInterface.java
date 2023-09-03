import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ecs100.UI;

public class UserInterface {

	private HashMap<String, Station> trainStationMap;
	private HashMap<String, TrainLine> trainLineMap;
	private dataReader dataReader;
	private Trip trip;

	// List stations along a given train line
	public void listStations() {
		clear();
		for (String s : trainStationMap.keySet()) {
			UI.print(s + "\n");
		}
	}

	// List All the trainLines
	public void listTrainLines() {
		clear();
		for (String s : trainLineMap.keySet()) {
			UI.print(s + "\n");
		}
	}

	// List train line that go through a certain station
	public void searchByStation() {
		clear();
		String station = UI.askString("Typing a station name to check Train Lines");
		System.out.print(station + "\n");
		Station s = trainStationMap.get(station.trim());
		if (s == null) {
			UI.print("Please type corrcet station name");
		} else {
			Set<TrainLine> tl = s.getTrainLines();
			System.out.print(tl + "\n");
			for (TrainLine t : tl) {
				UI.print(t.getName() + "\n");
			}
		}
	}

	// List the stations along a given train line
	public void listTrainLineStations() {
		clear();
		String tline = UI.askString("Typing a Train Line to show stations");
		TrainLine tl = trainLineMap.get(tline.trim());
		if (tl == null) {
			UI.print("Please type corrcet Train Line name");
		} else {
			List<Station> staList = tl.getStations();
			for (Station s : staList) {
				UI.print(s.getName() + "\n");
			}
		}
	}

	// print train line from a station to a destination station
	public void findLine() {
		clear();
		String start = UI.askString("Typing a start station");
		String destination = UI.askString("Typing a destination station");
		Station startStation = trainStationMap.get(start.trim());
		Station endStation = trainStationMap.get(destination.trim());
		// get possible train lines
		int flag = 0;
		if (startStation == null) {
			UI.print("Please check the name of stat station");
		} else {
			Set<TrainLine> trainline = startStation.getTrainLines();
			for (TrainLine t : trainline) {
				if (t.getStations().contains(endStation)) {
					if (t.getStations().indexOf(endStation) > t.getStations().indexOf(startStation)) {
						UI.print(t.getName() + "\n");
						flag++;
					}
				}
			}
			if (flag == 0) {
				UI.print("There is no train line between " + start + "and " + destination);
			}
		}
	}

	// Find the next train service for each line at a station immediately after a
	// user-specified time
	public void findNextTrain() {
		clear();
		String name = UI.askString("Name of the Station?");
		int time = UI.askInt("Specifies a time (like 630, 2300)?");
		Station station = trainStationMap.get(name.trim());
		if (station == null) {
			UI.print("Please check the name of Station");
		} else {
			int index = -1;
			Set<TrainLine> trainlines = station.getTrainLines();
			for (TrainLine tl : trainlines) {
				List<TrainService> tsIntl = tl.getTrainServices();
				List<Station> staIntl = tl.getStations();
				// find the index of this station
				for (int i = 0; i < staIntl.size(); i++) {
					Station s = staIntl.get(i);
					if (s.getName().equals(name)) {
						index = i;
					}
				}
				// based on this index, check the time in time list
				for (TrainService ts : tsIntl) {
					List<Integer> tim = ts.getTimes();
					if (tim.get(index) > time) {
						UI.print(ts.toString() + "\n");
						break;
					}
				}
			}
		}

	}

	// Find the trip between two given stations (on the same line), immediately
	// after a user specified time.
	public void findTrip() {
		clear();
		String startname = UI.askString("Name of the Start Station?");
		String destination_name = UI.askString("Name of the destination?");
		Station startStation = trainStationMap.get(startname);
		Station destination = trainStationMap.get(destination_name);
		if (startStation == null || destination == null) {
			UI.print("Please check the spelling of the station");
		} else {
			int time = UI.askInt("Specifies a time (like 630, 2300)?");
			Integer zone = 0;
			// get the train lines from start station
			Set<TrainLine> startLine = startStation.getTrainLines();
			for (TrainLine trainline : startLine) {
				if (trainline.getStations().contains(destination)) {
					if (trainline.getStations().indexOf(startStation) < trainline.getStations().indexOf(destination)) {
						// get the index of the start station and destination
						int index = trainline.getStations().indexOf(startStation);
						int indexEnd = trainline.getStations().indexOf(destination);
						// get trip's train service
						List<TrainService> trainSer = trainline.getTrainServices();
						for (TrainService ts : trainSer) {
							Integer leavetime = ts.getTimes().get(index);
							if (leavetime > time) {
								Integer startzone = startStation.getZone();
								Integer endzone = destination.getZone();
								if (startzone >= endzone) {
									zone = startzone - endzone;
								} else {
									zone = endzone - startzone;
								}
								// New a Trip object
								trip = new Trip(trainline, startStation, destination, zone);
								trip.setStartTime(leavetime);
								// get destination time
								Integer arriveTime = ts.getTimes().get(indexEnd);
								trip.setArriveTime(arriveTime);
								UI.print(trip.toString());
								break;
							}
						}
					}
				}
			}
		}
	}

	
	
	// Find the earliest arrival trip between two stations that are on two different
	// lines immediately after a user-specified time
	public Trip returnTrip(String startname, String destination_name, Integer time) {
		Station startStation = trainStationMap.get(startname);
		Station destination = trainStationMap.get(destination_name);
		Integer zone = 0;
		// get the train lines from start station
		Set<TrainLine> startLine = startStation.getTrainLines();
		for (TrainLine trainline : startLine) {
			if (trainline.getStations().contains(destination)) {
				if (trainline.getStations().indexOf(startStation) < trainline.getStations().indexOf(destination)) {
					// get the index of the start station and destination
					int index = trainline.getStations().indexOf(startStation);
					int indexEnd = trainline.getStations().indexOf(destination);
					// get trip's train service
					List<TrainService> trainSer = trainline.getTrainServices();
					for (TrainService ts : trainSer) {
						Integer leavetime = ts.getTimes().get(index);
						if (leavetime > time) {
							Integer startzone = startStation.getZone();
							Integer endzone = destination.getZone();
							if (startzone >= endzone) {
								zone = startzone - endzone;
							} else {
								zone = endzone - startzone;
							}
							// New a Trip object
							trip = new Trip(trainline, startStation, destination, zone);
							trip.setStartTime(leavetime);
							// get destination time
							Integer arriveTime = ts.getTimes().get(indexEnd);
							trip.setArriveTime(arriveTime);
							break;
						}
					}
				}
			}
		}
		return trip;
	}

	public void findBestTrip() {
		clear();
		String startname = UI.askString("Name of the Start Station?");
		String destination_name = UI.askString("Name of the destination?");
		Station startStation = trainStationMap.get(startname);
		Station destination = trainStationMap.get(destination_name);
		Integer finaltime =10000;
		Trip firsttrip=null;
		Trip secondtrip =null;
		if (startStation == null || destination == null) {
			UI.print("Please check the spelling of the station");
		} else {
			int time = UI.askInt("Specifies a time (like 630, 2300)?");
			// get all the stations related with start station
			// get all the stations related with destination
			List<String> startList = startStation.getRelatedStationName();
			List<String> endList = destination.getRelatedStationName();
			// get the intersection
			startList.retainAll(endList);
			for (String name : startList) {
				if ((!name.equals(startname)) && (!name.equals(destination_name))) {
					Trip trip1 = returnTrip(startname,name,time);
					Integer time1=trip1.getArriveTime();
					Trip trip2 = returnTrip(name,destination_name,time1);
					Integer time2=trip2.getArriveTime();
					if (time2<finaltime && time2!=-1) {
						finaltime=time2;
						firsttrip=trip1;
						secondtrip=trip2;
						
					}
				}
			}
			UI.print("The best trip is the following trip :"+"\n");
			UI.print("#######################"+"\n");
			UI.print(firsttrip.toString()+"\n");
			UI.print(secondtrip.toString()+"\n");
			UI.print("Total number of zones:"+(Math.abs(destination.getZone()-startStation.getZone())));
			
		}
	}

	public void clear() {
		UI.clearGraphics();
		UI.clearText();
	}

	public UserInterface() {
		dataReader = new dataReader();
		trainStationMap = dataReader.getStations();
		trainLineMap = dataReader.getTrainLines();
		UI.initialise();
		UI.addButton("Clear All", this::clear);
		UI.addButton("List All Stations", this::listStations);
		UI.addButton("List All Train Lines", this::listTrainLines);
		UI.addButton("List train line that go through a certain station", this::searchByStation);
		UI.addButton("List the stations along a given train line", this::listTrainLineStations);
		UI.addButton("Print train line that goes from a station to destination", this::findLine);
		UI.addButton("Find next train-service for each line at a station after user-specified time ",
				this::findNextTrain);
		UI.addButton("Find trip between two given stations, immediately after user specified time.", this::findTrip);
		UI.addButton("Find earliest arrival trip between two stations that are on two different Lines ", this::findBestTrip);

		// UI.addSlider("set RGB Blue", 0, 255, this::setBlue);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UserInterface();
//		for(Entry<String, TrainLine> s:trainLines.entrySet()) {
//			System.out.print(s.getValue());
//		}
	}

}
