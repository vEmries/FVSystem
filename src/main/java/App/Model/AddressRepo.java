package App.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepo extends CrudRepository<Address, Integer> {

    List<Address> findAll();
    Address findById(Integer ID);
    List<Address> findAllByCountry(String country);
    List<Address> findAllByProvince(String province);
    List<Address> findAllByCity(String city);
    List<Address> findAllByZip(String zip);
    List<Address> findAllByStreetContaining(String street);

    @Query("select a.id from Address a")
    List<Integer> getAddressIDs();
}
