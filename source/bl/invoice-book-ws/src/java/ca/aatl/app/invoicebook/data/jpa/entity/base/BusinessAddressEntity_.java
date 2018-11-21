package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BusinessAddressEntity.class)
public class BusinessAddressEntity_ extends BaseEntity_ {

    public static volatile SingularAttribute<BusinessAddressEntity, Integer> id;
    public static volatile SingularAttribute<BusinessAddressEntity, Date> fromDate;
    public static volatile SingularAttribute<BusinessAddressEntity, Boolean> primary;
    public static volatile SingularAttribute<BusinessAddressEntity, Date> toDate;
    public static volatile SingularAttribute<BusinessAddressEntity, String> comments;

}