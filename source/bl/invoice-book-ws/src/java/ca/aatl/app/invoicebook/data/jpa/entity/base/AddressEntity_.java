package ca.aatl.app.invoicebook.data.jpa.entity.base;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AddressEntity.class)
public class AddressEntity_ extends BaseEntity_ {

    public static volatile SingularAttribute<AddressEntity, Integer> id;
    public static volatile SingularAttribute<AddressEntity, String> postalCode;
    public static volatile SingularAttribute<AddressEntity, String> address1;
    public static volatile SingularAttribute<AddressEntity, String> address2;
    public static volatile SingularAttribute<AddressEntity, String> city;

}