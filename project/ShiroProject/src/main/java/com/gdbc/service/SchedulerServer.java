package com.gdbc.service;

import org.springframework.stereotype.Service;

@Service("schedulerServer")
public class SchedulerServer {

	public void schedulerTest()
	{
		for (int i = 0; i < 100; i++) {
			System.out.println("test+++"+i);
		}
	}
}
