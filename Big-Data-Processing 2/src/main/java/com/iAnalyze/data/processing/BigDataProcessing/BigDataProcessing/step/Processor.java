package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.step;

import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.model.AnalyzedTransactions;
import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class Processor implements ItemProcessor<Transaction, Transaction> {

    private static final Logger log = LoggerFactory.getLogger(Processor.class);

    @Autowired
    AnalyzedTransactions analyzedTransactions;

//    @Override
//    public Transaction process(Transaction transaction) throws Exception {
//        final int step = transaction.getStep();
//        final String type = transaction.getType();
//        final float amount = transaction.getAmount();
//        final String nameOrig = transaction.getNameOrig();
//        final float oldBalanceOrig = transaction.getOldBalanceOrig();
//        final float newBalanceOrig = transaction.getNewBalanceOrig();
//        final String nameDest = transaction.getNameDest();
//        final float oldBalanceDest = transaction.getOldBalanceDest();
//        final float newBalanceDest = transaction.getNewBalanceDest();
//        final int isFraud = transaction.getIsFraud();
//        final int isFlaggedFraud = transaction.getIsFlaggedFraud();
//
//        final Transaction refactoredTransactions = new Transaction(step, type, amount, nameOrig, oldBalanceOrig,
//                newBalanceOrig, nameDest, oldBalanceDest, newBalanceDest, isFraud, isFlaggedFraud);
////        log.info("Converting " + transaction + " into " + refactoredTransactions + ".");
//        return null;
//    }

    @Override
    public Transaction process(Transaction transaction) throws Exception {
        analyze(transaction);
        return null;
    }

//    private boolean needsToBeReturned(Transaction transaction) {
//        if()
//    }

    public void analyze(Transaction transaction) throws Exception {
        final int MAX_STEP = 745;
        int lastStep = 1;
        int total = 0;
        int stepTotal = 0;
        float avgFraud = 0;
        HashMap<Integer, List<? extends AnalyzedTransactions>> map = new HashMap<Integer, List<? extends AnalyzedTransactions>>();
        AnalyzedTransactions analyzedTransaction = new AnalyzedTransactions();
        while (true) {
            int step = transaction.getStep();
            if(step < MAX_STEP) {
                while (true) {
                    if (step == lastStep) {
                        stepTotal++;
                        total = total + transaction.getIsFraud();
                        avgFraud = (float)total / stepTotal;
                        lastStep = step;
                    }
                    else {
                        analyzedTransaction.setTotalFraudlentPerStep(total);
                        analyzedTransaction.setAvgFraudPerStep(avgFraud);
                        map.put(lastStep, List.of(analyzedTransaction));
                        total = 0;
                        avgFraud = 0;
                        stepTotal = 0;
                        break;
                    }
                }
            }
            else
                break;
        }
    }
}
