package com.ickovitz.operating_systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class SchedulingProcessor {
	protected List<String> timingOrder;
	protected HashMap<String, ProcessInfo> processesInfo;
	public abstract void createSchedule();
	public abstract String getName();
	public SchedulingProcessor(HashMap<String, ProcessInfo> processesInfo) {
		this.processesInfo = processesInfo;
		this.timingOrder = new ArrayList<String>();
	}

	public double calculateAvgNormalizedTurnaroundTime() {
		double normTurnaroundTime = 0;
		for (Entry<String, ProcessInfo> e : processesInfo.entrySet()) {
			int procEndTime = 0;
			for (int i = 0; i < timingOrder.size(); i++) {
				if (e.getKey().equals(timingOrder.get(i))) {
					procEndTime = i + 1;
				}
			}

			// got the end time, need to calculate the turnaround time
			int turnaroundTime = procEndTime - e.getValue().arrival;

			// normalized turnaround time = turnaround time / service time
			normTurnaroundTime += ((double) turnaroundTime / (double) e
					.getValue().length);
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

		for (Entry<String, ProcessInfo> e : processesInfo.entrySet()) {
			int procEndTime = timingOrder.lastIndexOf(e.getKey()) +1;
			int turnaroundTime = procEndTime - e.getValue().arrival;

			// add to runningTotal
			avgTurnaroundTime += turnaroundTime;
		}
		return (double) avgTurnaroundTime / (double) processesInfo.size();
	}

	private double calcAvgServiceTime() {

		int runningTotal = 0;
		for (ProcessInfo p : processesInfo.values()) {
			runningTotal += p.length;
		}
		return (double) runningTotal / (double) processesInfo.size();
	}

	private int calcMinServiceTime() {
		int minServiceTime = Integer.MAX_VALUE;
		for (ProcessInfo p : processesInfo.values()) {
			minServiceTime = Math.min(minServiceTime, p.length);
		}
		return minServiceTime;
	}
	
	private int calcMaxServiceTime() {

		int maxServiceTime = 0;
		for (ProcessInfo p : processesInfo.values()) {
			maxServiceTime = Math.max(maxServiceTime, p.length);
		}
		return maxServiceTime;
	}
}