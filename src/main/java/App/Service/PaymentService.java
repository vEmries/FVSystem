package App.Service;

import App.Model.Payment;
import App.Model.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Component
public class PaymentService {

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    FVService fvService;

    @Transactional
    @Modifying
    public void createNewPayment(Integer fvID, Date issuedate, Double quota, String note) {
        Payment newPayment = new Payment(fvID, issuedate, quota, note);
        paymentRepo.save(newPayment);
        fvService.updatePaidStatus(fvID);
    }

    @Transactional
    @Modifying
    public void updatePayment(Integer ID, Integer fvID, Double quota, String note) {
        Payment updatedPayment = paymentRepo.findById(ID);
        updatedPayment.setFv(fvID);
        updatedPayment.setQuota(quota);
        updatedPayment.setNote(note);

        fvService.updatePaidStatus(fvID);
    }

    @Transactional
    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    @Transactional
    public List<Payment> getPaymentByFV(Integer FV) {
        return paymentRepo.findAllByFv(FV);
    }

    @Transactional
    @Modifying
    public void deletePayment(Integer ID) {
        Integer fvID = paymentRepo.findById(ID).getFv();
        paymentRepo.delete(paymentRepo.findById(ID));
        fvService.updatePaidStatus(fvID);
    }

}
