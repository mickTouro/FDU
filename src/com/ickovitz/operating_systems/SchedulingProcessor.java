package com.ickovitz.operating_systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class SchedulingProcessor {

	protected List<String> timingOrder;
	protected HashMap<String, List<Integer>> processesInfo;

	public abstract void createSchedule();

	public abstract String getName();

	public SchedulingProcessor(HashMap<String, List<Integer>> processesInfo) {
		this.processesInfo = processesInfo;
		this.timingOrder = new ArrayList<String>();
	}

	public double calculateAvgNormalizedTurnaroundTime() {
		double normTurnaroundTime = 0;
		for (Entry<String, List<Integer>> e : processesInfo.entrySet()) {
			int procEndTime = 0;
			for (int i = 0; i < timingOrder.size(); i++) {
				if (e.getKey().equals(timingOrder.get(i))) {
					procEndTime = i + 1;
				}
			}

			// got the end time, need to calculate the turnaround time
			int turnaroundTime = procEndTime - e.getValue().get(1);
			// normalized turnaround time = turnaround time / service time
			normTurnaroundTime += ((double) turnaroundTime / (double) e
					.getValue().get(0));
		}

		// divide total turnaroundTime by num of processes
		return normTurnaroundTime / processesInfo.size();
	}

	public List<String> getTimingOrder() {
		return this.timingOrder;
	}

	public Map<String, Double> calculateMetrics() {
		Map<String, Double> metrics = new HashMap<String, Double>();
		metrics.put("Min Service Time", (double) calcMinServiceTime());
		metrics.put("Avg Service Time", calcAvgServiceTime());
		metrics.put("Max Service Time", (double) calcMaxServiceTime());
		metrics.put("Avg Turnaround Time", calcAvgTurnaroundTime());
		metrics.put("Avg Normalized Turnaround Time",
				calculateAvgNormalizedTurnaroundTime());
		return metrics;
	}

	private Double calcAvgTurnaroundTime() {
		double avgTurnaroundTime = 0;
		for (Entry<String, List<Integer>> e : processesInfo.entrySet()) {
			int procEndTime = 0;
			for (int i = 0; i < timingOrder.size(); i++) {
				if (e.getKey().equals(timingOrder.get(i))) {
					procEndTime = i + 1;
				}
			}

			// got the end time, need to calculate the turnaround time
			int turnaroundTime = procEndTime - e.getValue().get(1);
			// add to runningTotal
			avgTurnaroundTime += turnaroundTime;
		}

		return (double) avgTurnaroundTime / (double) processesInfo.size();
	}

	private double calcAvgServiceTime() {
		int runningTotal = 0;
		for (Entry<String, List<Integer>> p : processesInfo.entrySet()) {
			runningTotal += p.getValue().get(0);
		}
		return (double) runningTotal / (double) processesInfo.size();
	}

	private int calcMinServiceTime() {
		int minServiceTime = 0;
		for (Entry<String, List<Integer>> p : processesInfo.entrySet()) {
			if (minServiceTime == 0) {
				minServiceTime = p.getValue().get(0);
			} else {
				if (p.getValue().get(0) < minServiceTime) {
					minServiceTime = p.getValue().get(0);
				}
			}
		}
		return minServiceTime;
	}

	private int calcMaxServiceTime() {
		int maxServiceTime = 0;
		for (Entry<String, List<Integer>> p : processesInfo.entrySet()) {
			if (maxServiceTime == 0) {
				maxServiceTime = p.getValue().get(0);
			} else {
				if (p.getValue().get(0) > maxServiceTime) {
					maxServiceTime = p.getValue().get(0);
				}
			}
		}
		return maxServiceTime;
	}
}
