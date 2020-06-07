package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.dao;

import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.model.Transaction;

import java.util.List;

public interface TransactionsDao {
    public void insert(List<? extends Transaction> transactions);
    List<Transaction> loadAllTransactions();
}
