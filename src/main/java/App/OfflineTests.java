package App;

import App.Config.BasicConfig;
import App.Model.*;
import App.Service.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Date;

public class OfflineTests {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BasicConfig.class);

        FVRepo fvRepo = ctx.getBean(FVRepo.class);
        FVRevisionRepo fvRevisionRepo = ctx.getBean(FVRevisionRepo.class);
        FVService fvService = ctx.getBean(FVService.class);
        PaymentRepo paymentRepo = ctx.getBean(PaymentRepo.class);
        PaymentService paymentService = ctx.getBean(PaymentService.class);
        AddressRepo addressRepo = ctx.getBean(AddressRepo.class);
        ContractorRepo contractorRepo = ctx.getBean(ContractorRepo.class);
        ContractorService contractorService = ctx.getBean(ContractorService.class);
        ArchiveFVRepo archiveFVRepo = ctx.getBean(ArchiveFVRepo.class);
        ArchivePaymentRepo archivePaymentRepo = ctx.getBean(ArchivePaymentRepo.class);
        ArchiveService archiveService = ctx.getBean(ArchiveService.class);
        UserRepo userRepo = ctx.getBean(UserRepo.class);
        UserService userService = ctx.getBean(UserService.class);

//        ---------------------------------------------------------------------------------------------------------------

//        System.out.println(fvRepo.getFVIDs());
//        System.out.println(fvRepo.findAll());

//        fvService.createFV("22-DDDD", 1, new Date(2015,02,03), new Date(2015,02,22), 20.0, "    ");
//        fvService.updateFV(2, "AA-111", 1, new Date(2015,04,01), 15.00, "Nowa notatka");
//        fvService.updatePaidStatus(2);

//        System.out.println(contractorRepo.getContractorIDs());

//        contractorService.createContractor("Michex", "23-2323", "Nazwa banku", "123123123","111222333", "mail", "",
//                "Poland", "woj", "miasto", "kod pocztowy", "ulica i nr");
//        contractorService.updateAddress(4, "Poland", "woj", "B-B", "11-111", "Ulica i nr");
//        contractorService.updateContractor(2 ,"Zbyszex", "11-22-80", "Nazwa banku", "123123123", "111222333", "mail", "Nowa notatka");

//        System.out.println(paymentRepo.getPaymentIDs());
//        System.out.println(paymentRepo.findById(3));

//        paymentService.createPayment(6, new Date(115, 03,02), 12.50, "Wpłata nr yy");
//        paymentService.updatePayment(4, 21, 3, "Wpłata nr zz");
//        paymentService.deletePayment(4);

//        archiveService.archiveFV(69);
//        archiveService.autoArchive();

//        fvService.createRevision("TT-300RR", 82, new Date(2015,01,01), -37.00, "Note");

//        userService.checkUser("user1");
//        userService.addUser("user10", "pw10", "ROLE_user");


        ctx.close();

    }
}
