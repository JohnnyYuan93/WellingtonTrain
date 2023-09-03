import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ecs100.UI;

public class dataReader {
	
	private HashMap<String,Station> stations =new HashMap<>();
	private HashMap<String,TrainLine> trainLines =new HashMap<>();  //key is TrainLine name
	
	public void loadDatabases() {
		try {
			Scanner scan = new Scanner(new File("./Train network data/stations.data"));
			Scanner scanLine = new Scanner(new File("./Train network data/train-lines.data"));
			while(scan.hasNext()) {
				String line = scan.nextLine();
				Scanner linsc = new Scanner(line);
				while(linsc.hasNext()) {
					String name = linsc.next();
					Integer zone =linsc.nextInt();
					double dis=linsc.nextDouble();
					Station sta =new Station(name,zone,dis);
					stations.put(name, sta);
				}
				linsc.close();
			}
			while(scanLine.hasNext()) {
				String linename=scanLine.nextLine();
				TrainLine trl =new TrainLine(linename);
				trainLines.put(linename, trl);
				//System.out.print(linename);
				String trainlineStation = linename+"-stations"+".data";
				String trainlineService = linename+"-services"+".data";
				Scanner tlsta = new Scanner(new File("./Train network data/"+trainlineStation));
				Scanner tlser = new Scanner(new File("./Train network data/"+trainlineService));
				//read TrainLine stations data
				while (tlsta.hasNext()) {
					//add station into station list in TrainLine
					String staname = tlsta.next().strip();
					Station statmp =stations.get(staname);
					//add train into this station
					statmp.addTrainLine(trl);
					trl.addStation(statmp);
					//TO DO
					//Add train services into Service list in TrainLine
				}
				tlsta.close();
				// Read services data into TrainLine ojects.service list
				while (tlser.hasNext()) {
					String timeline =tlser.nextLine();
					ArrayList<Integer> time = new ArrayList<>();
					Scanner newscan = new Scanner(timeline);
					while (newscan.hasNext()) {
						Integer ti  = newscan.nextInt();
						time.add(ti);
					}
					TrainService trser =new TrainService(trl);
					for (int i=0;i<time.size();i++) {
						if (i==0) {
						trser.addTime(time.get(i), true);
						}
						else {
							trser.addTime(time.get(i), false);
						}
					}
					//trser.setTimes(time);
					trl.addTrainService(trser);
					newscan.close();
				}
				tlser.close();
			}
			scan.close();
			scanLine.close();
		
		}catch(IOException e){
			UI.println("Error: " + e);
		}
	}

	public HashMap<String, Station> getStations() {
		return stations;
	}

	public void setStations(HashMap<String, Station> stations) {
		this.stations = stations;
	}

	public HashMap<String, TrainLine> getTrainLines() {
		return trainLines;
	}

	public void setTrainLines(HashMap<String, TrainLine> trainLines) {
		this.trainLines = trainLines;
	}

	public dataReader() {
		super();
		loadDatabases();
	}
	
	
	

}
