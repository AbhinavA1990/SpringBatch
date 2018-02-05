package com.abhi.springbatch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class BatchJobCompletionListener implements JobExecutionListener {

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println("BATCH JOB COMPLETED SUCCESSFULLY");
		}

	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("BATCH JOB ABOUT TO START");
	}

}
