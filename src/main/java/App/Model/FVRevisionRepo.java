package App.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FVRevisionRepo extends CrudRepository<FVRevision, Integer> {

    List<FVRevision> findAll();
    FVRevision findById(Integer ID);
    List<FVRevision> findAllByFv(Integer fvID);

    @Query("select fvr.id from FVRevision fvr")
    List<Integer> getFVRevisionIDs();
}