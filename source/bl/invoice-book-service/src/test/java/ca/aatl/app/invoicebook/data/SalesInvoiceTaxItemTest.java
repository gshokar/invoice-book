/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Apr-05  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author GShokar
 */
public class SalesInvoiceTaxItemTest {
    
    public SalesInvoiceTaxItemTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
   
    /**
     * Test of codeAndRateText method, of class SalesInvoiceTaxItem.
     */
    @org.junit.jupiter.api.Test
    public void testCodeAndRateText() {
        System.out.println("codeAndRateText");
        SalesInvoiceTaxItem instance = new SalesInvoiceTaxItem();
        instance.setCode("HST");
        instance.setRate(13);
        String expResult = "HST (13.00%)";
        String result = instance.codeAndRateText();
        assertEquals(expResult, result);
    }
     
}
