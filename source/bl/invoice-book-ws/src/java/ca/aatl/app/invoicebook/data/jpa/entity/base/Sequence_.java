package ca.aatl.app.invoicebook.data.jpa.entity.base;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Sequence.class)
public class Sequence_ { 

    public static volatile SingularAttribute<Sequence, Integer> id;
    public static volatile SingularAttribute<Sequence, String> preFix;
    public static volatile SingularAttribute<Sequence, Integer> maxLength;
    public static volatile SingularAttribute<Sequence, Integer> lastNo;
    public static volatile SingularAttribute<Sequence, String> name;
    public static volatile SingularAttribute<Sequence, String> postFix;
    public static volatile SingularAttribute<Sequence, String> generator;
    public static volatile SingularAttribute<Sequence, Character> filler;

}