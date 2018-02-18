package App.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "archivepayment")
@Data
@NoArgsConstructor
public class ArchivePayment {

    @Id
    private Integer id;
    private Integer fv;
    private Date issuedate;
    private Double quota;
    private String note;

    public ArchivePayment(Payment toArchive) {
        this.id = toArchive.getId();
        this.fv = toArchive.getFv();
        this.issuedate = toArchive.getIssuedate();
        this.quota = toArchive.getQuota();
        this.note = toArchive.getNote();
    }
}