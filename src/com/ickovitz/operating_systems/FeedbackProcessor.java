package com.ickovitz.operating_systems;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;
import java.util.Map.Entry;

public class FeedbackProcessor extends SchedulingProcessor {

	private int quantum;
	private boolean isVariableLength;
	private static int NUMBER_OF_QUEUES = 20;
	List<Queue<Entry<String, Integer>>> multiLevelFeedbackQueues = new ArrayList<Queue<Entry<String, Integer>>>();

	public FeedbackProcessor(HashMap<String, ProcessInfo> processesInfo,
			int quantum, boolean isVariableLength) {

		super(processesInfo);
		this.quantum = quantum;
		this.isVariableLength = isVariableLength;

		for (int i = 0; i < NUMBER_OF_QUEUES; i++) {
			multiLevelFeedbackQueues
					.add(new LinkedList<Entry<String, Integer>>());
		}

		createSchedule();
		printTimingOrder();
		System.out.println("norm turnaround Time: "
				+ calculateAvgNormalizedTurnaroundTime());
	}

	public FeedbackProcessor(HashMap<String, ProcessInfo> processesInfo) {
		this(processesInfo, 1, false);
	}

	@Override
	public void createSchedule() {
		// create a map of processes ordered by arrival time
		TreeMap<Integer, List<String>> orderedProcesses = new TreeMap<>();
		for(Entry<String, ProcessInfo> proc : processesInfo.entrySet())
		{
			orderedProcesses.putIfAbsent(proc.getValue().arrival, new ArrayList<String>());
			orderedProcesses.get(proc.getValue().arrival).add(proc.getKey());
		}
		int currentTime = 0;
		while (!areAllQueuesEmpty() || !orderedProcesses.isEmpty()) {
			ArrayList<String> currentArrivals = new ArrayList<String>();
			// find the currently arriving process
			while(orderedProcesses.size() > 0 && orderedProcesses.firstKey() <= currentTime)
			{
				Entry<Integer, List<String>> entry = orderedProcesses.firstEntry();
				currentArrivals.addAll(entry.getValue());
				orderedProcesses.remove(orderedProcesses.firstKey());
			}

			// feedback queue
			for (String procName : currentArrivals) {
				Entry<String, Integer> entry = new AbstractMap.SimpleEntry<String, Integer>(
						procName, processesInfo.get(procName).length);

				// add name and service time to first feedback queue
				multiLevelFeedbackQueues.get(0).add(entry);
			}

			boolean foundProcess = false;

			// start with the highest level queue
			for (int i = 0; i < NUMBER_OF_QUEUES; i++) {

				// if nothing ready, go to lower level queue
				if (multiLevelFeedbackQueues.get(i).isEmpty()) {
					continue;
				} else {

					// get the process in curr queue

					Entry<String, Integer> runningProc = multiLevelFeedbackQueues
							.get(i).poll();

					// calculate the timeslice of given at curr queue level
					int currQuantum = (int) (this.isVariableLength ? (Math
							.pow(2, i)) : quantum);

					// for the current time quantum
					for (int x = 0; x < currQuantum; x++) {

						// add to schedule
						this.timingOrder.add(runningProc.getKey());
						currentTime++;
						// decrement serviceTime
						runningProc.setValue(runningProc.getValue() - 1);

						if (runningProc.getValue() <= 0) {
							break;
						}
					}

					// need to place back on next queue
					if (runningProc.getValue() > 0) {

						int placeOnQueueNumber = 0;
						if (!areAllQueuesEmpty()
								|| orderedProcesses.containsKey(currentTime))
						{
							// do a bounds check
							placeOnQueueNumber = i + 1 < NUMBER_OF_QUEUES ? i + 1 : NUMBER_OF_QUEUES -1;
						}
						
						// add to queue
						multiLevelFeedbackQueues.get(placeOnQueueNumber).add(
								runningProc);
					}

					foundProcess = true;
					break;
				}
			}

			if (!foundProcess){

				this.timingOrder.add("WAIT");
				currentTime++;
			}
		}
		
		int i = 0;
		i++;
	}

	private boolean areAllQueuesEmpty() {
		boolean isEmpty = true;
		for (Queue q : multiLevelFeedbackQueues) {

			if (!q.isEmpty()) {
				isEmpty = false;
				break;
			}
		}
		return isEmpty;
	}

	private void printTimingOrder() {
		for (String s : timingOrder) {
			System.out.println(s);
		}
	}

	@Override
	public String getName() {
		return "Feedback"
				+ (this.isVariableLength ? " Variable Quantum Q=2^n"
						: (" Standard Quantum Q=" + this.quantum));
	}
}