package com.iAnalyze.data.processing.BigDataProcessing.dao;

import com.iAnalyze.data.processing.BigDataProcessing.model.TransactionRecord;
import java.util.List;

public interface TransactionRecordDao  {
    void insert(List<? extends TransactionRecord> transactionRecords);

    List<TransactionRecord> loadAllTransactionRecords();
}
