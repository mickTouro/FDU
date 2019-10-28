package com.ickovitz.operating_systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class CalculateInformation {

	HashMap<String, ProcessInfo> processInfo;
	List<SchedulingProcessor> schedulingAlgorithms;

	public CalculateInformation(HashMap<String, ProcessInfo> processInfo) {

		this.processInfo = processInfo;
		this.schedulingAlgorithms = new ArrayList<SchedulingProcessor>();
	

		System.out.println("*****FCFS*****");
		schedulingAlgorithms.add(new FcfsProcessor(processInfo));
		System.out.println("*****RR Q=1*****");
		schedulingAlgorithms.add(new RoundRobinProcessor(processInfo, 1));
		System.out.println("*****RR Q=4*****");
		schedulingAlgorithms.add(new RoundRobinProcessor(processInfo, 4));
		System.out.println("*****SPN*****");
		schedulingAlgorithms.add(new ShortestProcessNextProcessor(processInfo));
		System.out.println("*****Feedback Q=1*****");
		schedulingAlgorithms.add(new FeedbackProcessor(processInfo));
		System.out.println("*****Feedback Q=2^n*****");
		schedulingAlgorithms.add(new FeedbackProcessor(processInfo, 1, true));
	}

	public List<SchedulingProcessor> getSchedulingAlgorithms(){
		return this.schedulingAlgorithms;
	}

	// for debugging
	void printProcessInfo(){
		for(Entry<String, ProcessInfo> e: processInfo.entrySet()){
			System.out.println(e.getKey() + ":");
			System.out.println("    service: "+ e.getValue().length);
			System.out.println("    arrival: "+ e.getValue().arrival);
		}
	}
}