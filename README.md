# WellingtonTrain
The aim of Wellington Trains is to develop a program to answer queries about Wellington train stations, train lines and the timetable for the train services on those lines.
# About the Data
Train Stations
Name of real-world stations in the Wellington area
Train Lines: 
A sequence of stations on a route. 
For example, the Wellington_Johnsonville line, which starts at Wellington station, and ends at Johnsonville station, with 7 stations in between. To make things easier, we treat the inbound and outbound as two separate train lines, so the Wellington_Melling line is different from the Melling_Wellington line. 
They have the same stations, but the sequence in one is the reverse of the sequence in the other.
Train Service: 
a schedule/timetable for a particular train running on a given train line. 
For example, the 11:32 train on the Wellington_Johnsonville line that leaves Wellington station at 11:32, leaves CroftonDowns at 11:40, ... gets in to Johnsonville at 11:55. 
A Train Service is specified by a sequence of times - the time that the train leaves the first station, followed by the times that the train gets to each of the remaining stations on the line. Note that some train services don't stop at every station on the line. If a train doesn't stop at a station, the corresponding time will be -1.

![image](https://github.com/JohnnyYuan93/WellingtonTrain/assets/77047081/d5ba979f-3424-417c-a63d-aa3a5a3fd813)

