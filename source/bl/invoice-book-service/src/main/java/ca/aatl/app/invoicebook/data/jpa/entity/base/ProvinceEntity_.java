package ca.aatl.app.invoicebook.data.jpa.entity.base;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ProvinceEntity.class)
public class ProvinceEntity_ { 

    public static volatile SingularAttribute<ProvinceEntity, Integer> id;
    public static volatile SingularAttribute<ProvinceEntity, String> guid;
    public static volatile SingularAttribute<ProvinceEntity, String> name;
    public static volatile SingularAttribute<ProvinceEntity, String> code;

}