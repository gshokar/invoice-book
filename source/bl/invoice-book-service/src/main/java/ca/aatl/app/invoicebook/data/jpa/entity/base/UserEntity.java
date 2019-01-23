/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gshokar
 */
@MappedSuperclass
public class UserEntity extends BaseEntity{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SysUserId", nullable = false)
    protected Integer id;
    @Basic(optional = false)
    @Column(name = "UserName", nullable = false, length = 120)
    protected String name;
    @Basic(optional = false)
    @Column(name = "LoginId", nullable = false, length = 20)
    protected String loginId;
    @Basic(optional = false)
    @Column(name = "LoginPassword", nullable = false, length = 200)
    protected String loginPassword;
    @Basic(optional = false)
    @Column(name = "Active", nullable = false)
    protected boolean active;
    @Column(name = "LastDeactivatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastDeactivatedDate;
    @Column(name = "Comments", length = 200)
    protected String comments;
    
    @Basic(optional = false)
    @Column(name = "PasswordChangedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date passwordChangedDate;
   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getLastDeactivatedDate() {
        return lastDeactivatedDate;
    }

    public void setLastDeactivatedDate(Date lastDeactivatedDate) {
        this.lastDeactivatedDate = lastDeactivatedDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getPasswordChangedDate() {
        return passwordChangedDate;
    }

    public void setPasswordChangedDate(Date passwordChangedDate) {
        this.passwordChangedDate = passwordChangedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if (!this.getGuid().equals(other.getGuid())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserEntity[ sysUserId=" + id + " ]";
    }


}
