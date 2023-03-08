package DAO.DsVolatile;

import DAO.IDao;
import Model.Credit;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component("dao")
public class CreditDao implements IDao<Credit,Long> {
    @Override
    public Credit trouverParID(Long IdCredit) {
        return DBCredits().stream()
                .filter(credit -> credit.getId() == IdCredit)
                .findFirst()
                .orElse(null);
    }

    static Set<Credit> DBCredits(){
        return new HashSet<Credit>(
                Arrays.asList(
                        new Credit(1L,800000.0,240,2.0,"Hind", 0.0),
                        new Credit(2L,500000.0,150,1.5,"Nadir", 0.0),
                        new Credit(3L,600000.0,180,2.2,"Maya", 0.0),
                        new Credit(4L,200000.0,200,1.8,"Khadija", 0.0)
                )
        );
    }
}
