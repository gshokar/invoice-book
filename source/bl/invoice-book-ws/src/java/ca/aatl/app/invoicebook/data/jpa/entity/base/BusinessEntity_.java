package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BusinessEntity.class)
public class BusinessEntity_ extends BaseEntity_ {

    public static volatile SingularAttribute<BusinessEntity, Integer> id;
    public static volatile SingularAttribute<BusinessEntity, String> name;
    public static volatile SingularAttribute<BusinessEntity, Boolean> active;
    public static volatile SingularAttribute<BusinessEntity, String> number;
    public static volatile SingularAttribute<BusinessEntity, Date> terminatedDate;
    public static volatile SingularAttribute<BusinessEntity, String> comments;

}