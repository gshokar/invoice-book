/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-23  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.TimeCodeDao;
import ca.aatl.app.invoicebook.data.jpa.entity.TimeCode;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.util.AppUtils;
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
public class TimeCodeService {

    @EJB
    TimeCodeDao dao;
    
    public TimeCode newEntity() {
        TimeCode timeCode = new TimeCode();
        
        timeCode.setActive(true);
        timeCode.setChargeable(true);

        return timeCode;
    }

    public void validate(TimeCode entity) throws DataValidationException {
        boolean rtnValue = true;

        StringBuilder sb = new StringBuilder();

        if (entity != null) {

            if (AppUtils.isNullOrEmpty(entity.getName())) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("Enter the time code name.");
            } else if (dao.isExists(entity.getId(), entity.getGuid(), entity.getName())) {
                rtnValue = false;
                sb.append(System.lineSeparator());
                sb.append("This time code name already exists.");
            }
            
        }

        if (!rtnValue) {
            throw new DataValidationException(sb.toString());
        }

    }

    public TimeCode find(String guid) throws Exception {
        return dao.find(guid);
    }

    public void save(TimeCode timeCode) throws Exception {
        
        beforeSave(timeCode);
        
        dao.save(timeCode);
    }

    private void beforeSave(TimeCode entity) throws DataValidationException {
        validate(entity);

        if (entity.getId() == null) {

            entity.setAddedBy(entity.getLastUpdatedBy());
            entity.setAddedDate(entity.getLastUpdatedDate());
            entity.getGuid();
            
        }
    }

    public List<TimeCode> list() throws Exception{
        return dao.list();
    }
    
}
