package ca.aatl.app.invoicebook.data.jpa.entity.base;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserEntity.class)
public class UserEntity_ extends BaseEntity_ {

    public static volatile SingularAttribute<UserEntity, Integer> id;
    public static volatile SingularAttribute<UserEntity, String> loginId;
    public static volatile SingularAttribute<UserEntity, Date> passwordChangedDate;
    public static volatile SingularAttribute<UserEntity, Date> lastDeactivatedDate;
    public static volatile SingularAttribute<UserEntity, String> name;
    public static volatile SingularAttribute<UserEntity, Boolean> active;
    public static volatile SingularAttribute<UserEntity, String> loginPassword;
    public static volatile SingularAttribute<UserEntity, String> comments;

}