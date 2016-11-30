package com.ickovitz.operating_systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ShortestProcessNextProcessor extends SchedulingProcessor {

	public ShortestProcessNextProcessor(
			HashMap<String, List<Integer>> processesInfo) {
		super(processesInfo);

		createSchedule();
		printTimingOrder();

		System.out.println("Norm Turn: " + calculateAvgNormalizedTurnaroundTime());
	}

	@Override
	public void createSchedule() {
		HashMap<String, List<Integer>> tempList = new HashMap<String, List<Integer>>(
				processesInfo);

		int time = 0;

		// as long as there are still processes waiting
		while (!tempList.isEmpty()) {
			ArrayList<Entry<String, List<Integer>>> currentArrivals = new ArrayList<Entry<String, List<Integer>>>();

			// loop through all remaining processes
			for (Entry<String, List<Integer>> e : tempList.entrySet()) {
				if (e.getValue().get(1) <= time) {
					// add to ready process list
					currentArrivals.add(e);
				}
			}
			

			Entry<String, List<Integer>> shortestReadyProcess = null;
			
			// if no processes are ready
			if(currentArrivals.size() < 1){
				timingOrder.add("WAIT");
				time++;
				continue;
			}
			else{
				for(Entry<String, List<Integer>> e : currentArrivals){
					if(shortestReadyProcess == null){
						shortestReadyProcess = e;
					}
					else if(e.getValue().get(0) < shortestReadyProcess.getValue().get(0)){
						shortestReadyProcess = e;
					}
				}
			}

			// add the shortest process to the schedule
			// once for every unit of time
			for (int i = 0; i < shortestReadyProcess.getValue().get(0); i++) {
				timingOrder.add(shortestReadyProcess.getKey());
				time++;
			}
			// remove process that was scheduled
			tempList.remove(shortestReadyProcess.getKey());
		}

	}
	
	private void printTimingOrder() {
		for (String s : timingOrder) {
			System.out.println(s);
		}
	}

	@Override
	public String getName() {
		return "Shortest Process Next";
	}

}