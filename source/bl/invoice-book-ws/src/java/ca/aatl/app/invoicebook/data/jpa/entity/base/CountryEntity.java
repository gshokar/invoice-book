package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author GShokar
 */
@MappedSuperclass
public class CountryEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CountryId", nullable = false)
    protected Integer id;
    @Basic(optional = false)
    @Column(name = "Country", nullable = false, length = 40)
    protected String name;
    @Basic(optional = false)
    @Column(name = "CountryCode", nullable = false, length = 3)
    protected String code;
    @Basic(optional = false)
    @Column(name = "GUID", nullable = false, length = 50)
    protected String guid;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGuid() {
        if (guid == null) {
            guid = java.util.UUID.randomUUID().toString().toUpperCase();
        }
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CountryEntity)) {
            return false;
        }
        CountryEntity other = (CountryEntity) object;
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
        return name == null ? "New" : name;
    }
}
