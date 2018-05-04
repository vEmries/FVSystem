package App.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FVRepo extends CrudRepository<FV, Integer> {

    List<FV> findAll();
    FV findById(Integer ID);
    List<FV> findByContractor(Integer contractorID);

    @Query("select fv.id from FV fv")
    List<Integer> getFVIDs();

//    @Query("select fv from FV fv order by fv.id desc")
//    List<FV> getNewest();
    @Query(value = "SELECT * FROM fv ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<FV> getNewest();

}
