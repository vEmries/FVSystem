package App.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContractorRepo extends CrudRepository<Contractor, Integer> {

    List<Contractor> findAll();
    Contractor findById(Integer ID);
    Contractor findByCompanyContaining(String name);
    Contractor findByAddress(Integer addressID);

    @Query("select c.id from Contractor c")
    List<Integer> getContractorIDs();
}