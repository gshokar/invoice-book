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

import ca.aatl.app.invoicebook.data.jpa.dao.UserDao;
import ca.aatl.app.invoicebook.data.jpa.entity.AppUser;
import ca.aatl.app.invoicebook.exception.DataValidationException;
import ca.aatl.app.invoicebook.util.CryptoUtils;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
public class UserService {
    
    @EJB
    private UserDao userDao;

    public AppUser validateLogin(String loginId, String password) throws GeneralSecurityException, DataValidationException, Exception {

        beforeValidateLogin();
        
        AppUser user = userDao.find(loginId);

        if (user == null) {

            throw new DataValidationException("Invalid loginid or password.");

        } else if (user.getId() < 1
                || !password.trim().equals(
                        CryptoUtils.decrypt(user.getLoginPassword()))) {

            throw new DataValidationException("Invalid loginid or password.");

        } else if (!user.isActive()) {
            throw new DataValidationException("Inactive user, please contact to system administrator or manager.");
        }

        return user;
    }

    private void beforeValidateLogin() throws GeneralSecurityException, Exception {
        
        long  userCount = userDao.count(AppUser.class);
        
        // if there are no user then its first time app login after install
        // Add the default user to let login
        if(userCount == 0){
            
            AppUser user = new AppUser();
            
            user.setActive(true);
            user.setLastUpdatedBy(0);
            user.setLastUpdatedDate(Calendar.getInstance().getTime());
            user.setLoginId("admin");
            user.setLoginPassword(CryptoUtils.encrypt("admin"));
            user.setName("Administrator");
            user.setPasswordChangedDate(user.getLastUpdatedDate());
            user.getGuid();
        
            userDao.save(user);
        }
    }
}
