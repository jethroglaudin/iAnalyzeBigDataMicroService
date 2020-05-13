package com.iAnalyze.data.processing.BigDataProcessing.config;

import com.iAnalyze.data.processing.BigDataProcessing.model.TransactionRecord;
import com.iAnalyze.data.processing.BigDataProcessing.step.TransactionProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<TransactionRecord, TransactionRecord>chunk(100)
                .reader(reader())
                .processor(new TransactionProcessor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .throttleLimit(20)
                .build();
    }

    @Bean
    public FlatFileItemReader<TransactionRecord> reader() {
        FlatFileItemReader<TransactionRecord> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("transactions.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(csvLineMapper());
        return reader;
    }

    @Bean
    public LineMapper<TransactionRecord> csvLineMapper() {
        DefaultLineMapper<TransactionRecord> lineMapper = new DefaultLineMapper<TransactionRecord>();
        lineMapper.setLineTokenizer(csvLineTokenizer());
        lineMapper.setFieldSetMapper(csvFieldSetMapper());
        return lineMapper;
    }

    @Bean
    public LineTokenizer csvLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] {"step", "type", "amount", "nameOrig", "oldBalanceOrig", "newBalanceOrig",
                "nameDest", "oldBalanceDest", "newBalanceDest", "isFraud", "isFlaggedFraud"});
        return lineTokenizer;
    }

    @Bean
    public FieldSetMapper csvFieldSetMapper() {
        BeanWrapperFieldSetMapper<TransactionRecord> fieldSetMapper = new BeanWrapperFieldSetMapper<TransactionRecord>();
        fieldSetMapper.setTargetType(TransactionRecord.class);
//        fieldSetMapper.setPrototypeBeanName("TransactionRecord");
        return fieldSetMapper;
    }

    @Bean
    public MongoItemWriter<TransactionRecord> writer() {
        MongoItemWriter<TransactionRecord> writer = new MongoItemWriter<TransactionRecord>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("transactions");
        return writer;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(20);
        return simpleAsyncTaskExecutor;
    }

//    @Bean
//    public TaskExecutor taskExecutor(){
////        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//////        executor.setCorePoolSize(2);
//////        executor.setMaxPoolSize(2);
//////        executor.setQueueCapacity(100);
//////        executor.setThreadNamePrefix("transactionThread -");
////        executor.initialize();
////
////        return executor;
//        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor("spring_batch");
//        asyncTaskExecutor.setConcurrencyLimit(5);
//        return asyncTaskExecutor;
//    }
}
