package App.Service;

import App.Exception.InvalidDataException;
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

    // Metody dla Payment.class

    @Transactional
    @Modifying
    public void createNewPayment(Integer fvID, Date issuedate, Double quota, String note) throws InvalidDataException {
        if (quota <= 0 || quota > fvService.getFV(fvID).getSum() - fvService.getFV(fvID).getPaid()) {
            throw new InvalidDataException("Błąd dodania płatnośći dla faktury '" + fvService.getFV(fvID).getFvnumber() + "'. Niepoprawna wartość kwoty");
        }

        Payment newPayment = new Payment(fvID, issuedate, quota, note);
        paymentRepo.save(newPayment);

        fvService.updatePaidStatus(fvID);
    }

    @Transactional
    @Modifying
    public void updatePayment(Integer ID, Integer fvID, Double quota, String note) throws InvalidDataException {
        if (quota <= 0) {
            throw new InvalidDataException("Błąd edycji płatności o ID '" + ID + "'. Niepoprawna wartość kwoty");
        }

        Payment updatedPayment = paymentRepo.findById(ID);
        updatedPayment.setFv(fvID);
        updatedPayment.setQuota(quota);
        if (!note.trim().equals("")) {
            updatedPayment.setNote(note);
        }

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
