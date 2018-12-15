/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-01  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.util;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author GShokar
 */
public final class AppUtils {
    
    public static String getGUID(){
        return UUID.randomUUID().toString().toUpperCase();
    }
    
    public static Date currentDate(){
        
        return java.sql.Date.valueOf(LocalDate.now());
    }
    
    public static boolean isNullOrEmpty(String value){
        return value == null || value.trim().length() == 0;
    }
}
