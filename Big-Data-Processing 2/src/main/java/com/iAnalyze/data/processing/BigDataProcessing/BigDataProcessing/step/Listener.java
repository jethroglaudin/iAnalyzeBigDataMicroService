package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.step;

import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.dao.TransactionsDao;
import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.model.Transaction;
import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.services.ImportServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

public class Listener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(Listener.class);
    private final TransactionsDao transactionsDao;

    public Listener(TransactionsDao transactionsDao) {
        this.transactionsDao = transactionsDao;
    }

    @Autowired
    ThreadPoolTaskExecutor taskExcutor;

    @Autowired
    private ImportServiceProxy proxy;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("JOB FINISHED! Time to call Reports Service");

            List<Transaction> transactionRecordsList = transactionsDao.loadAllTransactions();

            for (Transaction transactionRecord : transactionRecordsList) {
                log.info("Found < " + transactionRecord + "> in the database.");
            }
//
            //Call Report microservice
            String response = proxy.generatePDFHtmlReports();
            log.info("Report Service -> {}", response);
        }
        taskExcutor.shutdown();

    }
}
