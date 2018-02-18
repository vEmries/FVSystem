package App.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArchiveFVRepo extends CrudRepository<ArchiveFV, Integer> {

    List<ArchiveFV> findAll();
    ArchiveFV findById(Integer ID);
    ArchiveFV findByFvnumber(String fvNumber);

    @Query("select fv.id from ArchiveFV fv")
    List<Integer> getArchiveFVIDs();
}