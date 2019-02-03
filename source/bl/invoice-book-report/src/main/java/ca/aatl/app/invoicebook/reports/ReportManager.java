/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-01  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.reports;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;

/**
 *
 * @author GShokar
 */
public class ReportManager {

    private String reportPath = "/ca/aatl/app/invoicebook/reports/templates/";
    private String jsonDatePattern = "yyyy-MM-dd";
    
    public byte[] timeSheetPdf(String timeSheetJson) throws Exception {
        byte[] pdf = null;
        
        Map parameters = new HashMap();

        parameters.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, jsonDatePattern);
        
        // Just remove the leading forward silash
        parameters.put("SUBREPORT_LOCATION", reportPath.substring(1));
        
        JsonDataSource jr = new JsonDataSource(new ByteArrayInputStream(timeSheetJson.getBytes(StandardCharsets.UTF_8)));
        jr.setDatePattern(jsonDatePattern);
        
        try (InputStream isRpt = ReportManager.class.getResourceAsStream(reportPath + "invoice-book-time-sheet.jasper")) {

            JasperPrint jp = JasperFillManager.fillReport(isRpt, parameters, jr);

            pdf = JasperExportManager.exportReportToPdf(jp);
        }
        
        return pdf;
    }
}
