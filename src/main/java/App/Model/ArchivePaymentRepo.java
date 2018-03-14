package App.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArchivePaymentRepo extends CrudRepository<ArchivePayment, Integer> {

    List<ArchivePayment> findAll();
    ArchivePayment findById(Integer ID);
    List<ArchivePayment>findAllByFv(Integer FV);

    @Query("select ap.id from ArchivePayment ap")
    List<Integer> getArchivePaymentIDs();
}