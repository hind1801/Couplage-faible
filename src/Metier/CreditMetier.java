package Metier;

import DAO.IDao;
import Model.Credit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("metier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditMetier implements ICreditMetier{
    @Autowired
    @Qualifier("dao")
    IDao<Credit,Long> creditDao;

    /**
     *
     * @param idCredit
     * @return (capitale * taux)/(1-(1+taux)^^nbrMois) avec taux => tauxMensuel/1200
     * @throws CreditException
     */

    @Override
    public Credit CalculerMentualite(Long idCredit) throws CreditException{

        var credit = creditDao.trouverParID(idCredit);
        if(credit == null) throw new CreditException("Credit non trouve !!!");
        else{
            Double capital = credit.getCapitale_emprunte();
            Integer nbrMois    = credit.getNbr_mois();
            Double taux    = credit.getTaux_mensuel()/1200;
            Double ment    = (capital*taux)/(1-Math.pow((1+taux),-1*nbrMois));
                   ment   = (double) (Math.round(ment*100)/100);
            credit.setMensualité(ment);
            return credit;
        }
    }
}
