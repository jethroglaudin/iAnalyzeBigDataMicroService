package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.dao;

import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionsImpl  extends JdbcDaoSupport implements TransactionsDao {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void insert(List<? extends Transaction> TransactionRecords) {
        String sqlCommand = String.format("INSERT INTO transactions (id, step, type, amount, name_orig, old_balance_orig, new_balance_orig," +
                "name_dest, old_balance_dest, new_balance_dest, is_fraud, is_flagged_fraud) VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        assert getJdbcTemplate() != null;
        getJdbcTemplate().batchUpdate(sqlCommand, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Transaction transaction = TransactionRecords.get(i);
                ps.setInt(1, transaction.getStep());
                ps.setString(2, transaction.getType());
                ps.setFloat(3, transaction.getAmount());
                ps.setString(4, transaction.getNameOrig());
                ps.setFloat(5, transaction.getOldBalanceOrig());
                ps.setFloat(6, transaction.getNewBalanceOrig());
                ps.setString(7, transaction.getNameDest());
                ps.setFloat(8, transaction.getOldBalanceDest());
                ps.setFloat(9, transaction.getNewBalanceDest());
                ps.setInt(10, transaction.getIsFraud());
                ps.setInt(11, transaction.getIsFlaggedFraud());
            }

            @Override
            public int getBatchSize() {
              return TransactionRecords.size();
            }
        });
    }

//    @Override
//    public void analyzeInsert(List<? extends AnalyzedTransactions> analyzedTransactions) {
//        String sqlCommand = String.format("INSERT INTO analyzedTransactions (step, ");
//
//        assert getJdbcTemplate() != null;
//
//    }

    @Override
    public List<Transaction> loadAllTransactions() {
        String sqlCommand = String.format("SELECT * FROM transactions");
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sqlCommand);

        List<Transaction> result = new ArrayList<Transaction>();
        for(Map<String, Object> row : rows) {
            Transaction transactionRecord = new Transaction();
            transactionRecord.setStep((Integer) row.get("step"));
            transactionRecord.setType((String) row.get("type"));
            transactionRecord.setAmount((Float) row.get("amount"));
            transactionRecord.setNameOrig((String) row.get("name_orig"));
            transactionRecord.setOldBalanceOrig((Float) row.get("old_balance_orig"));
            transactionRecord.setNewBalanceOrig((Float) row.get("new_balance_orig"));
            transactionRecord.setNameDest((String) row.get("name_dest"));
            transactionRecord.setOldBalanceDest((Float) row.get("old_balance_dest"));
            transactionRecord.setNewBalanceDest((Float) row.get("new_balance_dest"));
            transactionRecord.setIsFraud((Integer) row.get("is_fraud"));
            transactionRecord.setIsFlaggedFraud((Integer) row.get("is_flagged_fraud"));
            result.add(transactionRecord);
        }
        return result;
    }
}
