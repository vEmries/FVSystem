package App.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String country;
    private String province;
    private String city;
    private String zip;
    private String street;

    public Address (String country, String province, String city, String zip, String street) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.zip = zip;
        this.street = street;
    }
}