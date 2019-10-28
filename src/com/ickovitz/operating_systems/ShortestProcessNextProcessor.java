package com.ickovitz.operating_systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ShortestProcessNextProcessor extends SchedulingProcessor {

	public ShortestProcessNextProcessor(HashMap<String, ProcessInfo> processesInfo) {
		super(processesInfo);
		createSchedule();
		printTimingOrder();
		System.out.println("Norm Turn: " + calculateAvgNormalizedTurnaroundTime());
	}

	@Override
	public void createSchedule() {
		// create a map of processes ordered by arrival time
		TreeMap<Integer, List<String>> orderedProcesses = new TreeMap<>();
		for (Entry<String, ProcessInfo> proc : processesInfo.entrySet()) {
			orderedProcesses.putIfAbsent(proc.getValue().arrival, new ArrayList<String>());
			orderedProcesses.get(proc.getValue().arrival).add(proc.getKey());
		}
		int currentTime = 0;

		while (!orderedProcesses.isEmpty()) {
		ArrayList<String> currentArrivals = new ArrayList<String>();
		// find the currently arriving process
		while (orderedProcesses.size() > 0 && orderedProcesses.firstKey() <= currentTime) {
			Entry<Integer, List<String>> entry = orderedProcesses.firstEntry();
			currentArrivals.addAll(entry.getValue());
			orderedProcesses.remove(orderedProcesses.firstKey());
		}

			// if no processes are ready
			if (currentArrivals.size() < 1) {
				timingOrder.add("WAIT");
				currentTime++;
			} else {

				while (!currentArrivals.isEmpty()) {
					String shortestReadyProcess = currentArrivals.get(0);
					for (String s : currentArrivals) {
						if (processesInfo.get(s).length < processesInfo.get(shortestReadyProcess).length) {
							shortestReadyProcess = s;
						}
					}
					currentArrivals.remove(shortestReadyProcess);

					// add the shortest process to the schedule
					// once for every unit of time
					for (int i = 0; i < processesInfo.get(shortestReadyProcess).length; i++) {
						timingOrder.add(shortestReadyProcess);
						currentTime++;
					}
				}
			}
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