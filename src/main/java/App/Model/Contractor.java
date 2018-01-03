package App.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contractor")
@Data
@NoArgsConstructor
public class Contractor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String company;
    private String companyshort;
    private String nip;
    private String bank;
    private String account;
    private Integer address;
    private String contactnr;
    private String mail;
    private String note;

    public void updateShortName() {
        List<String> toShorten = new ArrayList<>();
        toShorten.add("Spółka z.o.o.");
        toShorten.add("spółka z.o.o.");
        toShorten.add("Spółka jawna");
        toShorten.add("spółka jawna");
        toShorten.add("Spółka komandytowa");
        toShorten.add("spółka komandytowa");

        String newCompanyShort = this.company;
        System.out.println(newCompanyShort);

        for (String s : toShorten) {
            if (newCompanyShort.contains(s)) {
                System.out.println("Zawiera: " + s);
                newCompanyShort = newCompanyShort.replace(s, "");
            }
        }

        System.out.println(newCompanyShort);
        this.companyshort = newCompanyShort.trim();
    };

    public Contractor(String company, String nip, String bank, String account, Integer address, String contactnr, String mail, String note) {
        this.company = company;
        updateShortName();
        this.nip = nip;
        this.bank = bank;
        this.account = account;
        this.address = address;
        this.contactnr = contactnr;
        this.mail = mail;

        if (!StringUtils.hasLength(note.trim())) {
            this.note = "Brak dodatkowego opisu";
        } else {
            this.note = note.trim();
        }
    }
}
