package ca.aatl.app.invoicebook.data.jpa.entity.base;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ContactEntity.class)
public class ContactEntity_ extends BaseEntity_ {

    public static volatile SingularAttribute<ContactEntity, Integer> id;
    public static volatile SingularAttribute<ContactEntity, String> extension;
    public static volatile SingularAttribute<ContactEntity, String> phone;
    public static volatile SingularAttribute<ContactEntity, String> fax;
    public static volatile SingularAttribute<ContactEntity, String> mobilePhone;
    public static volatile SingularAttribute<ContactEntity, String> email;
    public static volatile SingularAttribute<ContactEntity, String> tollfree;

}