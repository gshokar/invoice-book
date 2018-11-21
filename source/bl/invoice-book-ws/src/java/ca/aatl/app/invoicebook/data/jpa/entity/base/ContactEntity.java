
package ca.aatl.app.invoicebook.data.jpa.entity.base;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author GShokar
 */

@MappedSuperclass
public class ContactEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ContactId", nullable = false)
    protected Integer id;
    @Basic(optional = false)
    @Column(name = "Phone", nullable = false, length = 15)
    protected String phone;
    @Column(name = "MobilePhone", length = 15)
    protected String mobilePhone;
    @Column(name = "Fax", length = 15)
    protected String fax;
    @Column(name = "Tollfree", length = 15)
    protected String tollfree;
    @Column(name = "Extension", length = 6)
    protected String extension;
    @Column(name = "Email", length = 100)
    protected String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTollfree() {
        return tollfree;
    }

    public void setTollfree(String tollfree) {
        this.tollfree = tollfree;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ContactEntity)) {
            return false;
        }
        ContactEntity other = (ContactEntity) object;
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
        return "[ ContactId=" + id + " ]";
    }
}
