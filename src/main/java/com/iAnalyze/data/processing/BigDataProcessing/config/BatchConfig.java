//package com.iAnalyze.data.processing.BigDataProcessing.config;
//
//import com.iAnalyze.data.processing.BigDataProcessing.dao.TransactionRecordDao;
//import com.iAnalyze.data.processing.BigDataProcessing.model.TransactionRecord;
//import com.iAnalyze.data.processing.BigDataProcessing.step.TransactionListener;
//import com.iAnalyze.data.processing.BigDataProcessing.step.TransactionProcessor;
//import com.iAnalyze.data.processing.BigDataProcessing.step.TransactionReader;
//import com.iAnalyze.data.processing.BigDataProcessing.step.TransactionWriter;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
//
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.core.task.TaskExecutor;
//
//import java.io.Reader;
//import java.io.Writer;
//
//@Configuration
//@EnableBatchProcessing
//public class BatchConfig extends DefaultBatchConfigurer {
//
//    @Override
//    protected JobRepository createJobRepository() throws Exception {
//        MapJobRepositoryFactoryBean factoryBean = new MapJobRepositoryFactoryBean();
//        factoryBean.afterPropertiesSet();
//        return factoryBean.getObject();
//    }
//
//    @Autowired
//    public JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    public StepBuilderFactory stepBuilderFactory;
//
//    @Autowired
//    public TransactionRecordDao transactionRecordDao;
//
//    @Bean
//    public Job job() {
//        return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new TransactionListener(transactionRecordDao))
//                .flow(step1()).end().build();
//    }
//
//    private Step step1() {
//        return stepBuilderFactory.get("step1").<TransactionRecord, TransactionRecord>chunk(5)
//                .reader(TransactionReader.reader("transactionRecords.csv"))
//                .processor(new TransactionProcessor())
//                .writer(new TransactionWriter(transactionRecordDao))
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor(){
//        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
//        simpleAsyncTaskExecutor.setConcurrencyLimit(5);
//        return simpleAsyncTaskExecutor;
//    }
//
//
//}
