package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.step;

import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.dao.TransactionsDao;
import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.model.Transaction;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Writer implements ItemWriter<Transaction> {

    private final TransactionsDao transactionsDao;

    public Writer(TransactionsDao transactionsDao) {
        this.transactionsDao = transactionsDao;
    }

    @Override
    public void write(List<? extends Transaction> transactions) throws Exception {
        transactionsDao.insert(transactions);
    }
}
