package com.ickovitz.operating_systems;

public class ProcessInfo {
	public ProcessInfo(int length, int arrival)
	{
		this.length = length;
		this.arrival = arrival;
	}
	
	int arrival;
	int length;
}
