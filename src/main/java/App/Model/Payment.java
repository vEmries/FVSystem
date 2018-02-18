package App.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer fv;
    private Date issuedate;
    private Double quota;
    private String note;

    public Payment(Integer fv, Date issuedate, Double quota, String note) {
        this.fv = fv;
        this.issuedate = issuedate;
        this.quota = quota;

        if (!StringUtils.hasLength(note.trim())) {
            this.note = "Standardowy przelew";
        } else {
            this.note = note.trim();
        }
    }
}