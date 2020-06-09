package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.config;

import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.dao.TransactionsDao;
import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.model.Transaction;
import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.step.Listener;
import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.step.Processor;
import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.step.Reader;
import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.step.Writer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public TransactionsDao transactionsDao;


    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .listener(new Listener(transactionsDao))
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        int CHUNK_SIZE = 200000;
        return stepBuilderFactory.get("step1").<Transaction, Transaction>chunk(CHUNK_SIZE)
                .reader(Reader.reader("transactions.csv"))
                .processor(new Processor())
                .writer(new Writer(transactionsDao))
                .taskExecutor(taskExecutor())
                .throttleLimit(20)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor("spring_batch");
        simpleAsyncTaskExecutor.setConcurrencyLimit(5);
        return simpleAsyncTaskExecutor;
    }
}
