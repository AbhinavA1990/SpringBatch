package com.abhi.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;

import com.abhi.springbatch.listener.BatchJobCompletionListener;
import com.abhi.springbatch.steps.Processor;
import com.abhi.springbatch.steps.Reader;
import com.abhi.springbatch.steps.Writer;
import com.abhi.springbatch.tasklet.CustomTasklet;

@Configuration
@EnableBatchProcessing
@Import(AdditionalBatchConfig.class)
@PropertySource("classpath:batch.properties")
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobLauncher jobLauncher;

	/***
	 * Define the Job based on steps 1 to 2
	 * 
	 * @return
	 */
	@Bean
	public Job job() {
		return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(listener()).flow(step1())
				.from(step1()).on("*").to(step2()).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<String, String>chunk(1).reader(new Reader()).processor(new Processor())
				.writer(new Writer()).build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").tasklet(customTasklet()).build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new BatchJobCompletionListener();
	}

	@Scheduled(fixedRate = 5000) // 5 secs
	public void perform() {
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(job(), jobParameters);
			System.out.println("I have been scheduled with Spring scheduler");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public CustomTasklet customTasklet() {
		CustomTasklet tasklet = new CustomTasklet();
		return tasklet;
	}

	@Bean
	public ResourcelessTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

}
