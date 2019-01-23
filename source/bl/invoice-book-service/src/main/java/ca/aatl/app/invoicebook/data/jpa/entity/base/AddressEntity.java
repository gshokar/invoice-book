package ca.aatl.app.invoicebook.data.jpa.entity.base;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 *
 * @author GShokar
 */
@MappedSuperclass
public class AddressEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AddressId", nullable = false)
    protected Integer id;
    
    @Basic(optional = false)
    @Column(name = "Address1", nullable = false, length = 100)
    protected String address1;
    @Column(name = "Address2", length = 100)
    protected String address2;
    
    @Basic(optional = false)
    @Column(name = "City", nullable = false, length = 60)
    protected String city;
    
    @Column(name = "PostalCode", length = 10)
    protected String postalCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AddressEntity)) {
            return false;
        }
        AddressEntity other = (AddressEntity) object;
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
        return "[ AddressId=" + id + " ]";
    }

    @Override
    protected AddressEntity clone() {
        AddressEntity address = new AddressEntity();

        address.setAddress1(address1);
        address.setAddress2(address2);
        address.setCity(city);
        address.setPostalCode(postalCode);

        return address;
    }
}
