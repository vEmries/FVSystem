package App.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "archivefvrevision")
@Data
@NoArgsConstructor
public class ArchiveFVRevision {

    @Id
    private Integer id;
    private String fvnumber;
    private Integer fv;
    private Date issuedate;
    private Double quota;
    private String note;

    public ArchiveFVRevision(FVRevision toArchive) {
        this.id = toArchive.getId();
        this.fvnumber = toArchive.getFvnumber();
        this.fv = toArchive.getFv();
        this.issuedate = toArchive.getIssuedate();
        this.quota = toArchive.getQuota();
        this.note = toArchive.getNote();
    }
}
