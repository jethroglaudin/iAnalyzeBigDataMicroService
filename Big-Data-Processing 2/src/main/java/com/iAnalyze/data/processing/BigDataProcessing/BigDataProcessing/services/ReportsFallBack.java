package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.services;

import org.springframework.stereotype.Component;

@Component
public class ReportsFallBack implements  ImportServiceProxy{

    @Override
    public String generatePDFHtmlReports() {
        return "Sorry! Unable to generate reports at this time.";
    }
}
