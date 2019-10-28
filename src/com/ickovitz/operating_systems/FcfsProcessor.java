package com.ickovitz.operating_systems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class FcfsProcessor extends SchedulingProcessor {

	public FcfsProcessor(HashMap<String, ProcessInfo> processesInfo) {
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
		// create a map of processes ordered by arrival time
		TreeMap<Integer, List<String>> orderedProcesses = new TreeMap<>();
		for(Entry<String, ProcessInfo> proc : processesInfo.entrySet())
		{
			orderedProcesses.putIfAbsent(proc.getValue().arrival, new ArrayList<String>());
			orderedProcesses.get(proc.getValue().arrival).add(proc.getKey());
		}
		
		int time = 0;

		// for each process, by arrival time
		for(Entry<Integer, List<String>> proc : orderedProcesses.entrySet()) {

			// keep incrementing time as long as the process arrival time is after curr time
			while(proc.getKey() > time){
				timingOrder.add("WAIT");
				time++;
			}
			
			// for each process
			for (String pName:  proc.getValue()) {
				for(int i = 0; i < processesInfo.get(pName).length; i++)
				{
					timingOrder.add(pName);
					time++;
				}
			}
		}
	}

	@Override
	public String getName() {
		return "First Come First Serve";
	}
}