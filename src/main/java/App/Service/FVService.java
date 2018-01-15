package App.Service;

import App.Exception.InvalidDataException;
import App.Model.*;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    ArchiveService archiveService;

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

    // Sprawdzenie unikalności nr faktury

    public boolean isFVNumberUnique(String fvNumber, Integer contractorID) {
        for (FV fv : fvRepo.findAll()) {
            if (fv.getFvnumber().equals(fvNumber.trim()) && fv.getContractor().equals(contractorID)) {
                return false;
            }
        }
        for (ArchiveFV fv: archiveService.getAllArchiveFVs()) {
            if (fv.getFvnumber().equals(fvNumber.trim()) && fv.getContractor().equals(contractorID)) {
                return false;
            }
        }
        return true;
    }

    public boolean isRevisionFVNumberUnique(String fvNumber, Integer fvID) {
        for (FVRevision r : fvRevisionRepo.findAll()) {
            if (r.getFvnumber().equals(fvNumber.trim()) && r.getFv().equals(fvID)) {
                return false;
            }
        }
        return true;
    }

    // Metody dla FV.class

    @Transactional
    @Modifying
    public void createNewFV(String fvNumber, Integer contractorID, Date issueDate, Date dueDate, Double value, String note) throws InvalidDataException {
        if (fvNumber.trim().equals("") || value <= 0) {
            throw new InvalidDataException("Błąd dodania faktury '" + fvNumber + "'. Niepoprawna wartość nr faktury lub kwoty");
        }
        if (!isFVNumberUnique(fvNumber, contractorID)) {
            throw new InvalidDataException("Błąd dodania faktury '" + fvNumber + "'. Istnieje już faktura o tym numerze dla kontrahenta o ID '" + contractorID +"'");
        }

        FV newFV = new FV(fvNumber, contractorID, issueDate, dueDate, value, note);
        fvRepo.save(newFV);
    }

    @Transactional
    @Modifying
    public void updateFV(Integer fvID, String fvNumber, Integer contractorID, Date issueDate, Date dueDate, Double value, String note) throws InvalidDataException {
        if (fvNumber.trim().equals("") || value <= 0) {
            throw new InvalidDataException("Błąd edycji faktury '" + fvRepo.findById(fvID).getFvnumber() + "'. Niepoprawna wartość nr faktury lub kwoty");
        }
        if (!isFVNumberUnique(fvNumber, contractorID)) {
            throw new InvalidDataException("Błąd edycji faktury '" + fvNumber + "'. Istnieje już faktura o tym numerze dla kontrahenta o ID '" + contractorID +"'");
        }

        FV updatedFV = fvRepo.findById(fvID);
        updatedFV.setFvnumber(fvNumber);
        updatedFV.setContractor(contractorID);
        updatedFV.setIssuedate(issueDate);
        updatedFV.setDuedate(dueDate);
        updatedFV.setValue(value);
        if (!note.trim().equals("")) {
            updatedFV.setNote(note);
        }

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
    public void createNewRevision(String fvNumber, Integer fvID, Date issueDate, Double quota, String note) throws InvalidDataException {
        if (fvNumber.trim().equals("") || quota == 0) {
            throw new InvalidDataException("Błąd dodania korekty '" + fvNumber + "'. Niepoprawna wartość nr faktury lub kwoty");
        }
        if (!isRevisionFVNumberUnique(fvNumber, fvID)) {
            throw new InvalidDataException("Błąd dodania korekty '" + fvNumber + "'. Istnieje już korekta o tym numerze dla faktury o ID '" + fvID +"'");
        }

        FVRevision newRevision = new FVRevision(fvNumber, fvID, issueDate, quota, note);
        fvRevisionRepo.save(newRevision);

        updateFVSum(fvID);
        updatePaidStatus(fvID);
    }

    @Transactional
    @Modifying
    public void updateRevision(Integer ID, String fvNumber, Integer fvID, Date issueDate, Double quota, String note) throws InvalidDataException {
        if (fvNumber.trim().equals("") || quota == 0) {
            throw new InvalidDataException("Błąd edycji korekty '" + fvRevisionRepo.findById(ID).getFvnumber() + "'. Niepoprawna wartość nr faktury lub kwoty");
        }
        if (!isRevisionFVNumberUnique(fvNumber, fvID)) {
            throw new InvalidDataException("Błąd edycji korekty '" + fvNumber + "'. Istnieje już korekta o tym numerze dla faktury o ID '" + fvID +"'");
        }

        FVRevision updatedRevision = fvRevisionRepo.findById(ID);
        updatedRevision.setFvnumber(fvNumber);
        updatedRevision.setFv(fvID);
        updatedRevision.setIssuedate(issueDate);
        updatedRevision.setQuota(quota);
        if (!note.trim().equals("")) {
            updatedRevision.setNote(note);
        }

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
