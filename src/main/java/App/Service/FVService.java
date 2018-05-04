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

    public boolean isFVUnique(String fvNumber, Integer contractorID) {
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

    public boolean isFVUniqueExcept(Integer fvID, String fvNumber, Integer contractorID) {
        for (FV fv : fvRepo.findAll()) {
            if (fv.getFvnumber().equals(fvNumber.trim()) && fv.getContractor().equals(contractorID) && !fv.getId().equals(fvID)) {
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

    public boolean isRevisionUnique(String fvNumber, Integer fvID) {
        for (FVRevision r : fvRevisionRepo.findAll()) {
            if (r.getFvnumber().equals(fvNumber.trim()) && r.getFv().equals(fvID)) {
                return false;
            }
        }
        return true;
    }

    public boolean isRevisionUniqueExcept(Integer ID, String fvNumber, Integer fvID) {
        for (FVRevision r : fvRevisionRepo.findAll()) {
            if (r.getFvnumber().equals(fvNumber.trim()) && r.getFv().equals(fvID) && !r.getId().equals(ID)) {
                return false;
            }
        }
        return true;
    }

    // Metody dla FV.class

    @Transactional
    @Modifying
    public void createFV(String fvNumber, Integer contractorID, Date issueDate, Date dueDate, Double value, String note) throws InvalidDataException {
        if (fvNumber.trim().equals("") || value <= 0) {
            throw new InvalidDataException("Błąd dodania faktury '" + fvNumber + "'. Niepoprawna wartość nr faktury lub kwoty");
        }
        if (!isFVUnique(fvNumber, contractorID)) {
            throw new InvalidDataException("Błąd dodania faktury '" + fvNumber + "'. Istnieje już faktura o tym numerze dla kontrahenta o ID '" + contractorID +"'");
        }

        FV newFV = new FV(fvNumber, contractorID, issueDate, dueDate, value, note);
        fvRepo.save(newFV);
    }

    @Transactional
    @Modifying
    public void createFV(FV toAdd) throws InvalidDataException {
        if (toAdd.getFvnumber().trim().equals("") || toAdd.getValue() <= 0) {
            throw new InvalidDataException("Błąd dodania faktury '" + toAdd.getFvnumber() + "'. Niepoprawna wartość nr faktury lub kwoty");
        }
        if (!isFVUnique(toAdd.getFvnumber(), toAdd.getContractor())) {
            throw new InvalidDataException("Błąd dodania faktury '" + toAdd.getFvnumber() + "'. Istnieje już faktura o tym numerze dla kontrahenta o ID '" + toAdd.getContractor() +"'");
        }

        fvRepo.save(toAdd);
    }

    @Transactional
    @Modifying
    public void updateFV(FV toUpdate) throws InvalidDataException {
        if (toUpdate.getFvnumber().trim().equals("") || toUpdate.getValue() <= 0) {
            throw new InvalidDataException("Błąd edycji faktury '" + toUpdate.getFvnumber() + "'. Niepoprawna wartość nr faktury lub kwoty");
        }
        if (!isFVUniqueExcept(toUpdate.getId(), toUpdate.getFvnumber(), toUpdate.getContractor())) {
            throw new InvalidDataException("Błąd edycji faktury '" + toUpdate.getFvnumber() + "'. Istnieje już faktura o tym numerze dla kontrahenta o ID '" + toUpdate.getContractor() +"'");
        }

        toUpdate.setStatus(-1);
        toUpdate.setSum(0.0);
        toUpdate.setPaid(0.0);
        fvRepo.save(toUpdate);

        updateFVSum(toUpdate.getId());
        updatePaidStatus(toUpdate.getId());
    }

    @Transactional
    public List<FV> getAllFVs() {
        return fvRepo.findAll();
    }

    @Transactional
    public List<FV> getNewestFVs() { return fvRepo.getNewest(); }

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
    public void createRevision(String fvNumber, Integer fvID, Date issueDate, Double quota, String note) throws InvalidDataException {
        if (fvNumber.trim().equals("") || quota == 0) {
            throw new InvalidDataException("Błąd dodania korekty '" + fvNumber + "'. Niepoprawna wartość nr faktury lub kwoty");
        }
        if (!isRevisionUnique(fvNumber, fvID)) {
            throw new InvalidDataException("Błąd dodania korekty '" + fvNumber + "'. Istnieje już korekta o tym numerze dla faktury o ID '" + fvID +"'");
        }

        FVRevision newRevision = new FVRevision(fvNumber, fvID, issueDate, quota, note);
        fvRevisionRepo.save(newRevision);

        updateFVSum(fvID);
        updatePaidStatus(fvID);
    }



    @Transactional
    @Modifying
    public void updateRevision(FVRevision toUpdate) throws InvalidDataException {
        if (toUpdate.getFvnumber().trim().equals("") || toUpdate.getQuota() == 0) {
            throw new InvalidDataException("Błąd edycji korekty '" + toUpdate.getFvnumber() + "'. Niepoprawna wartość nr faktury lub kwoty");
        }
        if (!isRevisionUniqueExcept(toUpdate.getId(), toUpdate.getFvnumber(), toUpdate.getFv())) {
            throw new InvalidDataException("Błąd edycji korekty '" + toUpdate.getFvnumber() + "'. Istnieje już korekta o tym numerze dla faktury o ID '" + toUpdate.getFv() +"'");
        }

        fvRevisionRepo.save(toUpdate);

        updateFVSum(toUpdate.getFv());
        updatePaidStatus(toUpdate.getFv());
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
