package App.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "archivefv")
@Data
@NoArgsConstructor
public class ArchiveFV {

    @Id
    private Integer id;
    private String fvnumber;
    private String contractor;
    private Date issuedate;
    private Date duedate;
    private Double value;
    private Double sum;
    private Double paid;
    private Integer status;
    private String note;

    public ArchiveFV(FV toArchive) {
        this.id = toArchive.getId();
        this.fvnumber = toArchive.getFvnumber();
        this.contractor = toArchive.getContractor().toString();
        this.issuedate = toArchive.getIssuedate();
        this.duedate = toArchive.getDuedate();
        this.value = toArchive.getValue();
        this.sum = toArchive.getSum();
        this.paid = toArchive.getPaid();
        this.status = toArchive.getStatus();
        this.note = toArchive.getNote();
    }
}
