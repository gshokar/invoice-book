/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-28  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.TimeEnityDao;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeEntry;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author GShokar
 */

@Stateless
@LocalBean
public class TimeEntryService {

    @EJB
    TimeEnityDao dao;
    
    public TimeEntry newEntity() {
        return new TimeEntry();
    }

    public TimeEntry find(String guid) {
        return dao.find(guid);
    }

    public void validate(TimeEntry entity) throws DataValidationException {
        boolean rtnValue = true;

        StringBuilder sb = new StringBuilder();

        if (entity != null) {

            if (entity.getDate() == null) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the time entry date.");
            } 
            
            if (entity.getTimeCode() == null) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("Select the time code.");
            } 
            
            if (entity.getStartTime() == null) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the start time.");
            } 
            
            if (entity.getEndTime() == null) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the end time.");
            } 
            
            if (entity.getStartTime() != null 
                    && entity.getEndTime() == null
                    && entity.getStartTime().after(entity.getEndTime())) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the end time should not be before start time.");
            } 
            
        }

        if (!rtnValue) {
            throw new DataValidationException(sb.toString());
        }
    }

    public void save(TimeEntry timeEntry) throws Exception{
        
        beforeSave(timeEntry);
        
        dao.save(timeEntry);
    }

    private void beforeSave(TimeEntry entity) throws DataValidationException {
        validate(entity);

        if (entity.getId() == null) {

            entity.setAddedBy(entity.getLastUpdatedBy());
            entity.setAddedDate(entity.getLastUpdatedDate());
            entity.getGuid();
            
        }
    }

    public List<TimeEntry> list() throws Exception{
        return dao.list();
    }

    public List<TimeEntry> find(String employeeNumber, Date yearMonthDate) throws Exception{
        return dao.find(employeeNumber, yearMonthDate);
    }
}
