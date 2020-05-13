//package com.iAnalyze.data.processing.BigDataProcessing.step;
//
//import com.iAnalyze.data.processing.BigDataProcessing.dao.TransactionRecordDao;
//import com.iAnalyze.data.processing.BigDataProcessing.model.TransactionRecord;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.data.MongoItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class TransactionWriter extends MongoItemWriter<TransactionRecord> {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    public MongoItemWriter<TransactionRecord> writer() {
//        MongoItemWriter<TransactionRecord> writer = new MongoItemWriter<TransactionRecord>();
//        writer.setTemplate(mongoTemplate);
//        writer.setCollection("domain");
//        return writer;
//    }
//
//
//    //    private final TransactionRecordDao transactionRecordDao;
////
////    public TransactionWriter(TransactionRecordDao transactionRecordDao) {
////        this.transactionRecordDao = transactionRecordDao;
////    }
////
////    @Override
////    public void write(List<? extends TransactionRecord> transactions) throws Exception {
////        transactionRecordDao.insert(transactions);
////    }
//}
