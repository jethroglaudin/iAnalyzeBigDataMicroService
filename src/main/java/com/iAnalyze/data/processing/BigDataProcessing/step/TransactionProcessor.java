package com.iAnalyze.data.processing.BigDataProcessing.step;

import com.iAnalyze.data.processing.BigDataProcessing.model.TransactionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Random;

public class TransactionProcessor  implements ItemProcessor<TransactionRecord, TransactionRecord> {
    private static final Logger log = LoggerFactory.getLogger(Process.class);


    @Override
    public TransactionRecord process(TransactionRecord transactionRecord) throws Exception {


        final int step = transactionRecord.getStep();
        final String type = transactionRecord.getType();
        final float amount = transactionRecord.getAmount();
        final String nameOrig = transactionRecord.getNameOrig();
        final float oldBalanceOrig = transactionRecord.getOldBalanceOrig();
        final float newBalanceOrig = transactionRecord.getNewBalanceOrig();
        final String nameDest = transactionRecord.getNameDest().toUpperCase();
        final float oldBalanceDest = transactionRecord.getOldBalanceDest();
        final float newBalanceDest = transactionRecord.getNewBalanceDest();
        final int isFraud = transactionRecord.getIsFraud();
        final int isFlaggedFraud = transactionRecord.getIsFlaggedFraud();

        final TransactionRecord fixedTransactionRecord = new TransactionRecord(step, type, amount, nameOrig, oldBalanceOrig,
                newBalanceOrig, nameDest, oldBalanceDest, newBalanceDest, isFraud, isFlaggedFraud);

        log.info("Converting " + transactionRecord + " into " + fixedTransactionRecord + ".");
        return fixedTransactionRecord;
    }
}
