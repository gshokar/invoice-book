package ca.aatl.app.invoicebook.data.jpa.entity.base;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CountryEntity.class)
public class CountryEntity_ { 

    public static volatile SingularAttribute<CountryEntity, Integer> id;
    public static volatile SingularAttribute<CountryEntity, String> guid;
    public static volatile SingularAttribute<CountryEntity, String> name;
    public static volatile SingularAttribute<CountryEntity, String> code;

}