package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class AnalyzedTransactions {
    private int step;
    private int totalFraudlentPerStep;
    private double avgFraudPerStep;

    public AnalyzedTransactions() {

    }

    public AnalyzedTransactions(int step, int totalFraudlentPerStep, double avgFraudPerStep) {
        this.step = step;
        this.totalFraudlentPerStep = totalFraudlentPerStep;
        this.avgFraudPerStep = avgFraudPerStep;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getTotalFraudlentPerStep() {
        return totalFraudlentPerStep;
    }

    public void setTotalFraudlentPerStep(int totalFraudlentPerStep) {
        this.totalFraudlentPerStep = totalFraudlentPerStep;
    }

    public double getAvgFraudPerStep() {
        return avgFraudPerStep;
    }

    public void setAvgFraudPerStep(double avgFraudPerStep) {
        this.avgFraudPerStep = avgFraudPerStep;
    }
}
