package App.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "contractor")
@Data
@NoArgsConstructor
public class Contractor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String company;
    private String nip;
    private String bank;
    private String account;
    private Integer address;
    private String contactnr;
    private String mail;
    private String note;

    public Contractor(String company, String nip, String bank, String account, Integer address, String contactnr, String mail, String note) {
        this.company = company;
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
