package App.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArchiveFVRevisionRepo extends CrudRepository<ArchiveFVRevision, Integer> {

    List<ArchiveFVRevision> findAll();
    ArchiveFVRevision findById(Integer ID);
    List<ArchiveFVRevision> findAllByFv(Integer fvID);

    @Query("select fvR.id from ArchiveFVRevision fvR")
    List<Integer> getArchiveFVRevisionIDs();
}