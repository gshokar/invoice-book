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

import java.nio.file.Files;
import java.nio.file.Paths;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author GShokar
 */
public class ReportManagerTest {
    
    public ReportManagerTest() {
    }
   
   
    /**
     * Test of createTimeSheet method, of class ReportManager.
     * @throws java.lang.Exception
     */
    @Test
    public void testTimeSheetPdf() throws Exception {
        System.out.println("createTimeSheet");
        String timeSheetJson = "{\"company\":{\"name\":\"TEST CORPORATION\",\"address\":{\"address1\":\"55 Test Drive\",\"address2\":\"\",\"city\":\"TEST\",\"province\":\"TS\",\"postalCode\":\"X0X 0X0\"},\"contact\":{\"phone\":\"000-000-0000\"}},\"employee\":{\"name\":\"Test Singh\"},\"month\":\"2019-01-01\",\"client\":{\"name\":\"Test Client Corp.\"},\"timeEntries\":[{\"date\":\"2019-01-02\",\"timeCode\":{\"name\":\"Springdale\"},\"startTime\":\"17:00\",\"endTime\":\"19:00\",\"hours\":\"2.00\"},{\"date\":\"2019-01-03\",\"timeCode\":{\"name\":\"Springdale\"},\"startTime\":\"15:00\",\"endTime\":\"19:15\",\"hours\":\"4.25\"},{\"date\":\"2019-01-05\",\"timeCode\":{\"name\":\"RayLawson\"},\"startTime\":\"10:00\",\"endTime\":\"16:15\",\"hours\":\"6.25\"}]}";
        ReportManager instance = new ReportManager();
        byte[] pdf = instance.timeSheetPdf(timeSheetJson);
        
        assertTrue(pdf != null);
    }
    
}
