package App.Service;

import App.Exception.InvalidDataException;
import App.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class ArchiveService {

    @Autowired
    ArchiveFVRepo archiveFVRepo;

    @Autowired
    ArchiveFVRevisionRepo archiveFVRevisionRepo;

    @Autowired
    ArchivePaymentRepo archivePaymentRepo;

    @Autowired
    FVService fvService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ContractorService contractorService;

    // Metody dla całego setu (FV + Revision + Payment)

    @Transactional
    @Modifying
    public List<FV> autoArchive() {
        Calendar calendar = Calendar.getInstance();
        java.sql.Date currentDate = new java.sql.Date(calendar.getTime().getTime());

        List<FV> archivizedFV = new ArrayList<>();
        for (FV fv : fvService.getAllFVs()) {
            if (currentDate.after(fv.getDuedate()) && fv.getStatus() > 0) {
                archiveFV(fv.getId());
                archivizedFV.add(fv);
            }
        }

        return archivizedFV;
    }

    @Transactional
    @Modifying
    public void archiveFV(Integer ID) {
        ArchiveFV fvToArchive = new ArchiveFV(fvService.getFV(ID));
        archiveFVRepo.save(fvToArchive);

        for (Payment p : paymentService.getPaymentByFV(ID)) {
            ArchivePayment paymentToArchive = new ArchivePayment(p);
            archivePaymentRepo.save(paymentToArchive);
        }

        for (FVRevision fvR : fvService.getRevisionByFV(ID)) {
            ArchiveFVRevision revisionToArchive = new ArchiveFVRevision(fvR);
            archiveFVRevisionRepo.save(revisionToArchive);
        }

        fvService.deleteFV(ID);
    }

    @Transactional
    @Modifying
    public void returnFromArchive(Integer ID) {
        ArchiveFV archiveFV = archiveFVRepo.findById(ID);
        FV toReAdd = new FV(archiveFV);
        archiveFV.setFvnumber(archiveFV.getFvnumber() + " - reAdding in progress");

        try {
            fvService.createNewFV(toReAdd);
        } catch (InvalidDataException e) {
            e.getMessage();
        }

        for (ArchiveFVRevision fvR : archiveFVRevisionRepo.findAllByFv(ID)) {
            try {
                fvService.createNewRevision(fvR.getFvnumber(), toReAdd.getId(), fvR.getIssuedate(), fvR.getQuota(), fvR.getNote());
            } catch (InvalidDataException e) {
                e.getMessage();
            }
        }

        for (ArchivePayment p : archivePaymentRepo.findAllByFv(ID)) {
            try {
                paymentService.createNewPayment(toReAdd.getId(), p.getIssuedate(), p.getQuota(), p.getNote());
            } catch (InvalidDataException e) {
                e.printStackTrace();
            }
        }

        archiveFVRepo.delete(ID);
    }

    @Transactional
    @Modifying
    public void deleteArchiveFV(Integer ID) {
        archiveFVRepo.delete(ID);
    }

    // Metody dla ArchiveFV.class

    @Transactional
    public List<ArchiveFV> getAllArchiveFVs() {
        return archiveFVRepo.findAll();
    }

    @Transactional
    public ArchiveFV getArchiveFV(Integer ID) {
        return archiveFVRepo.findById(ID);
    }

    // Metody dla ArchiveFVRevision.class

    @Transactional
    public List<ArchiveFVRevision> getAllArchiveRevisions() { return archiveFVRevisionRepo.findAll(); }

    @Transactional
    public List<ArchiveFVRevision> getArchiveRevisionByFV(Integer fvID) { return archiveFVRevisionRepo.findAllByFv(fvID); }

    // Metody dla ArchivePayment.class

    @Transactional
    public List<ArchivePayment> getAllArchivePayments() {
        return archivePaymentRepo.findAll();
    }

    @Transactional
    public List<ArchivePayment> getArchivePaymentByFV(Integer fvID) {
        return archivePaymentRepo.findAllByFv(fvID);
    }

}
