package App.Service;

import App.Model.*;
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
    FVRevisionRepo fvRevisionRepo;

    @Autowired
    PaymentService paymentService;

    // Metody aktualizujące sumę do zapłaty i stan płatności

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
    public void updateFVSum(Integer fvID) {
        FV checkedFV = fvRepo.findById(fvID);
        Double sum = checkedFV.getValue();

        for (FVRevision fvR : fvRevisionRepo.findAllByFv(fvID)) {
            sum = sum + fvR.getQuota();
        }

        checkedFV.setSum(sum);
    }

    // Metody dla FV.class

    @Transactional
    @Modifying
    public void createNewFV(String fvNumber, Integer contractorID, Date issueDate, Date dueDate, Double value, String note) {
        FV newFV = new FV(fvNumber, contractorID, issueDate, dueDate, value, note);
        fvRepo.save(newFV);
    }

    @Transactional
    @Modifying
    public void updateFV(Integer fvID, String fvNumber, Integer contractorID, Date issueDate, Date dueDate, Double value, String note) {
        FV updatedFV = fvRepo.findById(fvID);
        updatedFV.setFvnumber(fvNumber);
        updatedFV.setContractor(contractorID);
        updatedFV.setIssuedate(issueDate);
        updatedFV.setDuedate(dueDate);
        updatedFV.setValue(value);
        updatedFV.setNote(note);

        updateFVSum(fvID);
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

    // Metody dla FVRevision.class

    @Transactional
    @Modifying
    public void createNewRevision(String fvNumber, Integer fvID, Date issueDate, Double quota, String note) {
        FVRevision newRevision = new FVRevision(fvNumber, fvID, issueDate, quota, note);
        fvRevisionRepo.save(newRevision);

        updateFVSum(fvID);
        updatePaidStatus(fvID);
    }

    @Transactional
    @Modifying
    public void updateRevision(Integer ID, String fvNumber, Integer fvID, Date issueDate, Double quota, String note) {
        FVRevision updatedRevision = fvRevisionRepo.findById(ID);
        updatedRevision.setFvnumber(fvNumber);
        updatedRevision.setFv(fvID);
        updatedRevision.setIssuedate(issueDate);
        updatedRevision.setQuota(quota);
        updatedRevision.setNote(note);

        updateFVSum(fvID);
        updatePaidStatus(fvID);
    }

    @Transactional
    public List<FVRevision> getAllRevisions() { return fvRevisionRepo.findAll(); }

    @Transactional
    public FVRevision getRevision(Integer ID) { return fvRevisionRepo.findById(ID); }

    @Transactional
    public List<FVRevision> getRevisionByFV(Integer fvID) { return fvRevisionRepo.findAllByFv(fvID); }

    @Transactional
    @Modifying
    public void deleteRevision(Integer ID) {
        Integer fvID = fvRevisionRepo.findById(ID).getFv();
        fvRevisionRepo.delete(ID);
        updateFVSum(fvID);
        updatePaidStatus(fvID);
    }

    
}
