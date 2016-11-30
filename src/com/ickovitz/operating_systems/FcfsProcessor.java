package com.ickovitz.operating_systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class FcfsProcessor extends SchedulingProcessor {

	public FcfsProcessor(HashMap<String, List<Integer>> processesInfo) {
		super(processesInfo);
		createSchedule();
		printTimingOrder();
		System.out.println("avg normalized turnaround time: "
				+ calculateAvgNormalizedTurnaroundTime());
	}

	private void printTimingOrder() {
		for (String s : timingOrder) {
			System.out.println(s);
		}
	}

	public void createSchedule() {
		HashMap<String, List<Integer>> tempList = new HashMap<String, List<Integer>>(
				processesInfo);

		int time = 0;

		// as long as there are still processes waiting
		while (!tempList.isEmpty()) {
			Entry<String, List<Integer>> earliestArrival = null;

			// loop through all remaining processes
			for (Entry<String, List<Integer>> e : tempList.entrySet()) {
				if (e.getValue().get(1) <= time) {
					// if no earliest, set current to earliest
					if (earliestArrival == null) {
						earliestArrival = e;
					} else if (e.getValue().get(1) < earliestArrival.getValue()
							.get(1)) {
						// set earlier
						earliestArrival = e;
					}
				}
			}
			
			if(earliestArrival == null){
				timingOrder.add("WAIT");
				time++;
				continue;
			}

			// add the earliest process to the schedule
			// once for every unit of time
			for (int i = 0; i < earliestArrival.getValue().get(0); i++) {
				timingOrder.add(earliestArrival.getKey());
				time++;
			}
			// remove process that was scheduled
			tempList.remove(earliestArrival.getKey());
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "First Come First Serve";
	}
}
