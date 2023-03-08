package Metier;

import Model.Credit;

public interface ICreditMetier {
    Credit CalculerMentualite(Long mLIDCredit) throws CreditException;
}
