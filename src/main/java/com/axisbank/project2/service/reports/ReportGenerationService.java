package com.axisbank.project2.service.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.axisbank.project2.report.model.ReportModel;
import com.axisbank.project2.repository.ReportModelRepository;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Service
public class ReportGenerationService {
	
	@Autowired
	private ReportModelRepository repository;
	
	private ResourceLoader resourceLoader;
	
	@Inject
	public ReportGenerationService(ResourceLoader r) {
		this.resourceLoader=r;
	}
	
	public File exportReport(String username) throws JRException, IOException {
		List<ReportModel> audit1 =  repository.findAll();
		Resource r=resourceLoader.getResource("classpath:auditreport.jrxml");
		InputStream auditR = r.getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(auditR);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(audit1);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "audit user");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        String fileName= System.currentTimeMillis()+"_"+"audit.";
        File report = File.createTempFile(fileName, ".pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(report));
        return report;
    }

}
