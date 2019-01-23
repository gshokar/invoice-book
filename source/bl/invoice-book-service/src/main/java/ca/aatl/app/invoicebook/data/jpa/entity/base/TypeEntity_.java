package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TypeEntity.class)
public class TypeEntity_ extends BaseEntity_ {

    public static volatile SingularAttribute<TypeEntity, Integer> id;
    public static volatile SingularAttribute<TypeEntity, String> name;
    public static volatile SingularAttribute<TypeEntity, Date> terminatedDate;

}