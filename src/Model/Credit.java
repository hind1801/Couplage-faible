package Model;

import lombok.*;

import java.security.PrivateKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    private Long id;
    private Double capitale_emprunte;
    private Integer nbr_mois;
    private Double taux_mensuel;
    private String nom_demandeur;
    private Double mensualité;


    @Override
    public String toString()
    {
        String message ="";
        message += "====================================================================\n";
        message += "=> Crédit n°"+getId();
        message += "\n=> Nom du demmandeur de crédit  : "+nom_demandeur;
        message += "\n--------------------------------------------------------------------\n";
        message += "=> Capitale Emprunté            : "+capitale_emprunte+"DH\n";
        message += "=> Nombre de mois               : "+nbr_mois+" mois\n";
        message += "=> Taux mensuel                 : "+taux_mensuel+"%\n";
        message += "--------------------------------------------------------------------\n";
        message += "=> Mensualité                   : "+mensualité+"DH/mois\n";
        message += "====================================================================\n";
        return message;

    }

}
