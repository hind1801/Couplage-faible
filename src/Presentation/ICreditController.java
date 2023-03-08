package Presentation;

import Metier.CreditException;

public interface ICreditController {
    void afficherMentualite(Long idCredit) throws CreditException;
}
