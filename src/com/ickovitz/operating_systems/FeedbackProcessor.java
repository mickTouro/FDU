package com.ickovitz.operating_systems;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Map.Entry;

public class FeedbackProcessor extends SchedulingProcessor {

	private int quantum;
	private boolean isVariableLength;
	private static int NUMBER_OF_QUEUES = 20;
	List<Queue<Entry<String, Integer>>> multiLevelFeedbackQueues = new ArrayList<Queue<Entry<String, Integer>>>();

	public FeedbackProcessor(HashMap<String, List<Integer>> processesInfo,
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

	public FeedbackProcessor(HashMap<String, List<Integer>> processesInfo) {
		this(processesInfo, 1, false);
	}

	@Override
	public void createSchedule() {

		HashMap<String, List<Integer>> tempList = new HashMap<String, List<Integer>>(
				processesInfo);

		int currentTime = 0;

		while (!areAllQueuesEmpty() || !tempList.isEmpty()) {
			ArrayList<Entry<String, Integer>> currentArrivals = new ArrayList<Entry<String, Integer>>();
			// find the currently arriving process
			for (Entry<String, List<Integer>> e : tempList.entrySet()) {

				if (e.getValue().get(1) <= currentTime) {
					if (currentArrivals.size() == 0) {
						currentArrivals.add(new AbstractMap.SimpleEntry(e
								.getKey(), e.getValue().get(1)));
					} else {
						int i = 0;
						for (; i < currentArrivals.size(); i++) {
							// if this processes arrival is prior to list item
							if (e.getValue().get(1) < currentArrivals.get(i)
									.getValue()) {
								break;
							}
						}
						// add to list in order of arrival (at index i)
						currentArrivals.add(i,
								new AbstractMap.SimpleEntry(e.getKey(), e
										.getValue().get(1)));
					}
				}
			}

			// arrival time is irrelevant once in queue, so drop the value in
			// feedback queue
			for (Entry<String, Integer> e : currentArrivals) {

				Entry<String, Integer> entry = new AbstractMap.SimpleEntry<String, Integer>(
						e.getKey(), tempList.get(e.getKey()).get(0));
				// add name and service time to first feedback queue
				multiLevelFeedbackQueues.get(0).add(entry);

				// remove from temp list
				tempList.remove(e.getKey());
			}

			boolean foundProcess = false;

			// start with the highest level queue
			for (int i = 0; i < NUMBER_OF_QUEUES; i++) {
				// if nothing ready, go to lower level queue
				if (multiLevelFeedbackQueues.get(i).isEmpty()) {
					continue;
				} else {
					// get the process
					Entry<String, Integer> runningProc = multiLevelFeedbackQueues
							.get(i).poll();

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
					// need to place back on a queue
					if (runningProc.getValue() > 0) {
						int placeOnQueueNumber = 0;
						if (!areAllQueuesEmpty()
								|| isIncomingProcess(tempList, currentTime))
							placeOnQueueNumber = i + 1 < NUMBER_OF_QUEUES ? i + 1 : NUMBER_OF_QUEUES -1;
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
	}

	private boolean isIncomingProcess(HashMap<String, List<Integer>> tempList,
			int i) {
		boolean isReady = false;
		for (Entry<String, List<Integer>> e : tempList.entrySet()) {
			if (e.getValue().get(1) == i) {
				isReady = true;
				break;
			}
		}

		return isReady;
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
		// TODO Auto-generated method stub
		return "Feedback"
				+ (this.isVariableLength ? " Variable Quantum Q=2^n"
						: (" Standard Quantum Q=" + this.quantum));
	}
}