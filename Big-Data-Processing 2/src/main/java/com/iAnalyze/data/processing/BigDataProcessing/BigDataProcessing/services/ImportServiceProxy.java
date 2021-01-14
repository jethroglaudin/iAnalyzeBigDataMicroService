package com.iAnalyze.data.processing.BigDataProcessing.BigDataProcessing.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="bigdata-reports-service", fallback = ReportsFallBack.class)
public interface ImportServiceProxy {

    @GetMapping("/api/report/fraud")
    String generatePDFHtmlReports();
}
