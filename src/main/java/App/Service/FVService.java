package App.Service;

import App.Model.FV;
import App.Model.FVRepo;
import App.Model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Component
public class FVService {

    @Autowired
    FVRepo fvRepo;

    @Autowired
    PaymentService paymentService;

    @Transactional
    @Modifying
    public void updatePaidStatus(Integer fvID) {
        FV checkedFV = fvRepo.findById(fvID);
        Double paidSum = 0.0;

        for (Payment p : paymentService.getPaymentByFV(fvID)) {
            paidSum += p.getQuota();
        }

        checkedFV.setPaid(paidSum);

        if (!paymentService.getPaymentByFV(fvID).isEmpty()) {
            if (paidSum < checkedFV.getSum()) {
                checkedFV.setStatus(0);
            } if (paidSum >= checkedFV.getSum()) {
                checkedFV.setStatus(1);
            }
        } else {
            checkedFV.setStatus(-1);
        }
    }

    @Transactional
    @Modifying
    public void createNewFV(String fvNumber, Integer contractorID, Date issueDate, Date dueDate, Double sum, String note) {
        FV newFV = new FV(fvNumber, contractorID, issueDate, dueDate, sum, note);
        fvRepo.save(newFV);
    }

    @Transactional
    @Modifying
    public void updateFV(Integer fvID, String fvNumber, Integer contractorID, Date dueDate, Double sum, String note) {
        FV updatedFV = fvRepo.findById(fvID);
        updatedFV.setFvnumber(fvNumber);
        updatedFV.setContractor(contractorID);
        updatedFV.setDuedate(dueDate);
        updatedFV.setSum(sum);
        updatedFV.setNote(note);

        updatePaidStatus(fvID);
    }

    @Transactional
    public List<FV> getAllFVs() {
        return fvRepo.findAll();
    }

    @Transactional
    public FV getFV(Integer ID) {
        return fvRepo.findById(ID);
    }

    @Transactional
    @Modifying
    public void deleteFV(Integer ID) {
        fvRepo.delete(ID);
    }

    
}
