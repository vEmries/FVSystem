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
    private Double value;
    private Double sum;
    private Double paid;
    private Integer status;
    private String note;

    public FV(String fvnumber, Integer contractor, Date issuedate, Date duedate, Double value, String note) {
        this.fvnumber = fvnumber.trim();
        this.contractor = contractor;
        this.issuedate = issuedate;
        this.duedate = duedate;
        this.value = value;
        this.sum = value;
        this.paid = 0.0;
        this.status = -1;

        if (!StringUtils.hasLength(note.trim())) {
            this.note = "Brak dodatkowego opisu";
        } else {
            this.note = note.trim();
        }
    }

    public FV(ArchiveFV archiveFV) {
        this.fvnumber = archiveFV.getFvnumber();
        this.contractor = archiveFV.getContractor();
        this.issuedate = archiveFV.getIssuedate();
        this.duedate = archiveFV.getDuedate();
        this.value = archiveFV.getValue();
        this.sum = this.value;
        this.paid = 0.0;
        this.status = -1;
        this.note = archiveFV.getNote();
    }

}
