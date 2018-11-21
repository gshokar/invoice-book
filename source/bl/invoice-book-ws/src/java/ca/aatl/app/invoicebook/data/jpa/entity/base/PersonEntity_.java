package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PersonEntity.class)
public class PersonEntity_ extends BaseEntity_ {

    public static volatile SingularAttribute<PersonEntity, Integer> id;
    public static volatile SingularAttribute<PersonEntity, String> middleName;
    public static volatile SingularAttribute<PersonEntity, String> lastName;
    public static volatile SingularAttribute<PersonEntity, String> name;
    public static volatile SingularAttribute<PersonEntity, String> gender;
    public static volatile SingularAttribute<PersonEntity, Date> birthDate;
    public static volatile SingularAttribute<PersonEntity, String> firstName;
    public static volatile SingularAttribute<PersonEntity, String> comments;

}