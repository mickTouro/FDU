package com.ickovitz.operating_systems;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

public class RoundRobinProcessor extends SchedulingProcessor {

	private int quantum;

	public RoundRobinProcessor(HashMap<String, List<Integer>> processesInfo,
			int quantum) {
		super(processesInfo);
		this.quantum = quantum;

		createSchedule();
		System.out.println("Norm Turn: " + calculateAvgNormalizedTurnaroundTime());
		printTimingOrder();
	}

	
	public void createSchedule() {
		HashMap<String, List<Integer>> tempList = new HashMap<String, List<Integer>>(
				processesInfo);

		// create a queue
		Queue<Entry<String, Integer>> rrQueue = new LinkedList<Entry<String, Integer>>();
		
		int currentTime = 0;
		Entry<String, Integer> currentlyRunning = null;
		
		while (!rrQueue.isEmpty() || !tempList.isEmpty()){
			ArrayList<Entry<String, Integer>> currentArrivals = new ArrayList<Entry<String, Integer>>();
			// find the currently arriving process
			for (Entry<String, List<Integer>> e : tempList.entrySet()) {
				
				if (e.getValue().get(1) <= currentTime) {
					if(currentArrivals.size() == 0){
						currentArrivals.add(new AbstractMap.SimpleEntry(e.getKey(), e.getValue().get(1)));
					}
					else{
						int i = 0;
						for(; i < currentArrivals.size(); i++){
							// if this processes arrival is prior to list item
							if(e.getValue().get(1) < currentArrivals.get(i).getValue())
							{
								break;
							}
						}
						// add to list in order of arrival (at index i)
						currentArrivals.add(i, new AbstractMap.SimpleEntry(e.getKey(), e.getValue().get(1)));
					}
				}
			}

			// arrival time is irrelevant once in queue, so drop the value in RR queue
			for(Entry<String, Integer> e: currentArrivals){
				
				Entry<String, Integer> entry = 
					new AbstractMap.SimpleEntry<String, Integer>
				(e.getKey(), tempList.get(e.getKey()).get(0));
				// add to RR queue
				rrQueue.add(entry);
				
				// remove from temp list
				tempList.remove(e.getKey());
			}
			
			// place at end of queue
			if(currentlyRunning != null && currentlyRunning.getValue() != 0) rrQueue.add(currentlyRunning);
			
			// get the top of the process at top of queue
			currentlyRunning = rrQueue.poll();
			
			if(currentlyRunning == null){
				timingOrder.add("WAIT");
				currentTime++;
				continue;
			}
			
			for (int i = 0; i < quantum; i++) {
				// add single time slice to schedule
				timingOrder.add(currentlyRunning.getKey());
				// decrement service time by 1
				currentlyRunning.setValue(currentlyRunning.getValue() -1);
				currentTime++;
				
				// if process completed
				if(currentlyRunning.getValue() == 0){
					break;
				}
				
			}
			

		}
		// now that queue is empty, run curr process until finished
		for(int i = 0; i < currentlyRunning.getValue(); i++){
			timingOrder.add(currentlyRunning.getKey());
		}
		
	}

	private void printTimingOrder() {
		for (String s : timingOrder) {
			System.out.println(s);
		}
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Round Robin Q=" + this.quantum;
	}
}
