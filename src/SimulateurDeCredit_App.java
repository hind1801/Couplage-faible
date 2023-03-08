import DAO.DsVolatile.CreditDao;
import DAO.IDao;
import Metier.CreditException;
import Metier.CreditMetier;
import Metier.ICreditMetier;
import Model.Credit;
import Presentation.CreditController;
import Presentation.ICreditController;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SimulateurDeCredit_App {

    static ICreditController controleur;

    /**
     * Tester l'app avec une injection de dependence statique
     */

    static Scanner clavier = new Scanner(System.in);
    private static boolean estUnNombre(String input){
        try {
            Integer.parseInt(input); return true;
        }catch (Exception e){
            return false;
        }
    }
    public static void test1() throws CreditException {
        //Fabrique
        controleur = new CreditController();
        var service = new CreditMetier();
        var dao = new CreditDao();

        //Injection des dépendences
        ((CreditMetier) service).setCreditDao(dao);
        ((CreditController) controleur).setCreditService(service);
        String reponse = "";
        do {
            System.out.println("=> [Test 1] : Calculs de mentualité de crédit <=\n");
            try {
                String input = "";
                while (true) {
                    System.out.print("=> Entrer l'id du crédit : ");
                    input = clavier.nextLine();
                    if (estUnNombre(input)) break;
                    System.err.println("Entrée non valide !!");
                }
                Long id = Long.parseLong(input);
                controleur.afficherMentualite(id);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Voulez-vous quitter (oui/non)? ");
            reponse = clavier.nextLine();
        } while (!reponse.equalsIgnoreCase("oui"));
        System.out.println("Au revoir ^_-");
    }
    public static void test2() throws Exception {

        //Fabrique
        controleur                      = null;
        ICreditMetier service           = null;
        IDao<Credit,Long> dao           = null;

        String daoClass, serviceCLass, controllerClass;

        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        var config = cl.getResourceAsStream("config.properties");

        Properties propreties = new Properties();

        try {
            propreties.load(config);
            daoClass = propreties.getProperty("DAO");
            serviceCLass = propreties.getProperty("SERVICE");
            controllerClass = propreties.getProperty("CONTROLLER");
            config.close();

        }catch (IOException e){
            throw new Exception("Problème de chargement des propriétes du fichier config");
        }finally {
            propreties.clear();
        }
        try {
            Class cDAO          = Class.forName(daoClass);
            Class cService      = Class.forName(serviceCLass);
            Class cController   = Class.forName(controllerClass);

            controleur = (ICreditController)cController.getDeclaredConstructor().newInstance();
            service    = (ICreditMetier)cService.getDeclaredConstructor().newInstance();
            dao        = (IDao<Credit, Long>)cDAO.getDeclaredConstructor().newInstance();

            Method setDao   = cService.getMethod("setCreditDao", IDao.class);
            setDao.invoke(service, dao);

            Method setService= cController.getMethod("setCreditService",ICreditMetier.class);
                   setService.invoke(controleur, service);

            controleur.afficherMentualite(3L);
        }catch (Exception e){
            e.printStackTrace();
        } catch (CreditException e) {
            throw new RuntimeException(e);
        }
    }
    public static void test3() throws CreditException {
        controleur = null;
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-ioc.xml");
                           controleur = (ICreditController)context.getBean("controller");
                           controleur.afficherMentualite(1L);
    }
    public static void test4() throws CreditException {
        controleur = null;
        ApplicationContext context      = new AnnotationConfigApplicationContext("DAO.DsVolatile","Metier","Presentation");
                           controleur   = (ICreditController) context.getBean(ICreditController.class);
                           controleur.afficherMentualite(3L);
    }

    public static void main(String[] args) throws CreditException, Exception {
        test4();
    }
}
