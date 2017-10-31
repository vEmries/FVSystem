package App.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentRepo extends CrudRepository<Payment, Integer> {

    List<Payment> findAll();
    Payment findById(Integer ID);
    List<Payment> findAllByFv(Integer FV);

    @Query("select p.id from Payment p")
    List<Integer> getPaymentIDs();
}
