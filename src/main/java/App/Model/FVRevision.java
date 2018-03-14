package App.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "fvrevision")
@Data
@NoArgsConstructor
public class FVRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fvnumber;
    private Integer fv;
    private Date issuedate;
    private Double quota;
    private String note;

    public FVRevision(String fvNumber, Integer fvID, Date issueDate, Double quota, String note) {
        this.fvnumber = fvNumber;
        this.fv = fvID;
        this.issuedate = issueDate;
        this.quota = quota;

        if (!StringUtils.hasLength(note.trim())) {
            this.note = "Brak dodatkowego opisu";
        } else {
            this.note = note.trim();
        }
    }
}