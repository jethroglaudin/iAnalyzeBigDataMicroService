package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.step;

import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.dao.TransactionsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class Listener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(Listener.class);
    private final TransactionsDao transactionsDao;

    public Listener(TransactionsDao transactionsDao) {
        this.transactionsDao = transactionsDao;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("Batch Job is complete!");
        }
    }
}
