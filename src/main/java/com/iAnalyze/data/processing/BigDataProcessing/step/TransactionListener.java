package com.iAnalyze.data.processing.BigDataProcessing.step;

import com.iAnalyze.data.processing.BigDataProcessing.dao.TransactionRecordDao;
import com.iAnalyze.data.processing.BigDataProcessing.model.TransactionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import java.util.List;

public class TransactionListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(TransactionListener.class);

    private final TransactionRecordDao transactionRecordDao;

    public TransactionListener(TransactionRecordDao transactionRecordDao) {
        this.transactionRecordDao = transactionRecordDao;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job is complete, Check window for results!");

            List<TransactionRecord> transactionRecordList = transactionRecordDao.loadAllTransactionRecords();

            for (TransactionRecord transactionRecord : transactionRecordList) {
                log.info("Found < " + transactionRecord + "> in the database.");
            }
        }
    }
}
