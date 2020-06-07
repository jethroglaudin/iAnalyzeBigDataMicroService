package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.step;

import com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.model.Transaction;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class Reader {
    public static FlatFileItemReader<Transaction> reader(String path) {
        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<Transaction>();
        reader.setResource(new ClassPathResource(path));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Transaction>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] {"step", "type", "amount", "nameOrig", "oldbalanceOrig", "newbalanceOrig",
                                "nameDest", "oldbalanceDest", "newbalanceDest", "isFraud", "isFlaggedFraud"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Transaction>() {
                    {
                        setTargetType(Transaction.class);
                    }
                });
            }
        });
        return  reader;
    }
}
