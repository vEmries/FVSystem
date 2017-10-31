package App.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "fv")
@Data
@NoArgsConstructor
public class FV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fvnumber;
    private Integer contractor;
    private Date issuedate;
    private Date duedate;
    private Double sum;
    private Double paid;
    private Integer status;
    private String note;

    public FV(String fvnumber, Integer contractor, Date issuedate, Date duedate, Double sum, String note) {
        this.fvnumber = fvnumber.trim();
        this.contractor = contractor;
        this.issuedate = issuedate;
        this.duedate = duedate;
        this.sum = sum;
        this.paid = 0.0;
        this.status = -1;

        if (!StringUtils.hasLength(note.trim())) {
            this.note = "Brak dodatkowego opisu";
        } else {
            this.note = note.trim();
        }
    }

}
