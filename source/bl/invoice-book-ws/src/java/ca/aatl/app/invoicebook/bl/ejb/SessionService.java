/*
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-31  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.SessionDao;
import ca.aatl.app.invoicebook.data.jpa.entity.AppSession;
import ca.aatl.app.invoicebook.data.jpa.entity.AppUser;
import ca.aatl.app.invoicebook.util.AppUtils;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author GShokar
 */

@Stateless
@LocalBean
public class SessionService {

    @EJB
    private SessionDao sessionDao;

    public String getSessionId(AppUser user, String clientIP) throws Exception {
       
        AppSession session = sessionDao.find(user.getId());
        
        if(session == null){
            
            session = create(user, clientIP);
        }else{
            
            session.setClientIP(clientIP);
            
            sessionDao.save(session);
        }

        return session.getId();
    }

    private AppSession create(AppUser user, String clientIP) throws Exception {
        
        AppSession session = new AppSession();
        
        session.setClientIP(clientIP);
        session.setId(AppUtils.getGUID().replace('-', 'G'));
        session.setUser(user);
        session.setLastUpdatedBy(user.getId());
        session.setLastUpdatedDate(Calendar.getInstance().getTime());
        
        try {
            sessionDao.save(session);
        } catch (Exception ex) {
            Logger.getLogger(SessionService.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
        return session;
    }

    public boolean isExists(String sessionId) {
         return find(sessionId) != null;
    }
    
    public AppSession find(String sessionId) {
        return sessionDao.find(sessionId);
    }
}
