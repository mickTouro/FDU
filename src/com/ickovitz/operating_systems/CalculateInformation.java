package com.ickovitz.operating_systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class CalculateInformation {
	HashMap<String, List<Integer>> processInfo;
	
	List<SchedulingProcessor> schedulingAlgorithms;

	public CalculateInformation(HashMap<String, List<Integer>> processInfo) {
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
		for(Entry<String, List<Integer>> e: processInfo.entrySet()){
			System.out.println(e.getKey() + ":");
			System.out.println("    service: "+ e.getValue().get(0));
			System.out.println("    arrival: "+ e.getValue().get(1));
		}
	}
}
