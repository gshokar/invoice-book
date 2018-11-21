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
 * @author GSHOKAR
 */
@MappedSuperclass
public class PersonEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EntityId", unique = true, nullable = false)
    protected Integer id;
    @Basic(optional = false)
    @Column(name = "Name", nullable = false, length = 150)
    protected String name;
    @Basic(optional = false)
    @Column(name = "LastName", nullable = false, length = 40)
    private String lastName;
    @Basic(optional = false)
    @Column(name = "FirstName", nullable = false, length = 40)
    private String firstName;
    @Column(name = "MiddleName", nullable = true, length = 25)
    protected String middleName;
    @Column(name = "Comments", length = 200)
    protected String comments;
    @Column(name = "Gender", nullable = true, length = 1)
    private String gender;
    @Column(name = "BirthDate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
        setName();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setName();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PersonEntity)) {
            return false;
        }
        PersonEntity other = (PersonEntity) object;
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

    private void setName() {
        StringBuilder sb = new StringBuilder();

        if (firstName != null) {
            sb.append(firstName.trim());
            sb.append(" ");
        }

        if (middleName != null) {
            sb.append(middleName.trim());
            sb.append(" ");
        }

        if (lastName != null) {
            sb.append(lastName.trim());
        }

        this.name = sb.toString().trim();
    }
}
