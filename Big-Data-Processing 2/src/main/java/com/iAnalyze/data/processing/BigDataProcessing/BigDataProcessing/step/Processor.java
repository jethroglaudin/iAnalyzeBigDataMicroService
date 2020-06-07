package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.step;

import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<Transaction, Transaction> {

    private static final Logger log = LoggerFactory.getLogger(Processor.class);

    @Override
    public Transaction process(Transaction transaction) throws Exception {
        final int step = transaction.getStep();
        final String type = transaction.getType();
        final float amount = transaction.getAmount();
        final String nameOrig = transaction.getNameOrig();
        final float oldBalanceOrig = transaction.getOldBalanceOrig();
        final float newBalanceOrig = transaction.getNewBalanceOrig();
        final String nameDest = transaction.getNameDest();
        final float oldBalanceDest = transaction.getOldBalanceDest();
        final float newBalanceDest = transaction.getNewBalanceDest();
        final int isFraud = transaction.getIsFraud();
        final int isFlaggedFraud = transaction.getIsFlaggedFraud();

        final Transaction refactoredTransactions = new Transaction(step, type, amount, nameOrig, oldBalanceOrig,
                newBalanceOrig, nameDest, oldBalanceDest, newBalanceDest, isFraud, isFlaggedFraud);
//        log.info("Converting " + transaction + " into " + refactoredTransactions + ".");
        return refactoredTransactions;
    }
}
