
package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GShokar
 */

@MappedSuperclass
public class TypeEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeId", unique = true, nullable = false)
    protected Integer id;
    @Column(name = "Name", nullable = false, length = 40)
    protected String name;
    @Column(name = "TerminatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date terminatedDate;

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

    public Date getTerminatedDate() {
        return terminatedDate;
    }

    public void setTerminatedDate(Date terminatedDate) {
        this.terminatedDate = terminatedDate;
    }
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TypeEntity)) {
            return false;
        }
        TypeEntity other = (TypeEntity) object;
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
        return name != null ? name : "New";
    }
}
