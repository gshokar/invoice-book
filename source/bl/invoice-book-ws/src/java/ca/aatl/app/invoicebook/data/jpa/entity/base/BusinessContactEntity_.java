package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BusinessContactEntity.class)
public class BusinessContactEntity_ extends BaseEntity_ {

    public static volatile SingularAttribute<BusinessContactEntity, Integer> id;
    public static volatile SingularAttribute<BusinessContactEntity, Date> fromDate;
    public static volatile SingularAttribute<BusinessContactEntity, Boolean> primary;
    public static volatile SingularAttribute<BusinessContactEntity, Date> toDate;
    public static volatile SingularAttribute<BusinessContactEntity, String> comments;

}