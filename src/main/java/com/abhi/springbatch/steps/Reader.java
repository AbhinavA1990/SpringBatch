package com.abhi.springbatch.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class Reader implements ItemReader<String> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private String[] messages = { "SPRING BOOT", "Standalone app", "Abhinav Verma" };

	private int count = 0;

	/***
	 * Reader method, we can return the read content
	 */
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (count < messages.length) {
			return messages[count++];
		} else {
			count = 0;
		}
		return null;
	}

}
