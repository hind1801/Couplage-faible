package Presentation;

import Metier.CreditException;
import Metier.ICreditMetier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
@Data @AllArgsConstructor @NoArgsConstructor
public class CreditController implements ICreditController {

    @Autowired
    @Qualifier("metier")
    ICreditMetier creditService;

    @Override
    public void afficherMentualite(Long idCredit) throws CreditException {
        var credit = creditService.CalculerMentualite(idCredit);
        System.out.println(credit.toString());
    }
}
