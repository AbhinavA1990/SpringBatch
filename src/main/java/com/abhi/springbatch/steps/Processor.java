package com.abhi.springbatch.steps;

import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<String, String> {

	public String process(String data) throws Exception {
		return data.toUpperCase();
	}

}
