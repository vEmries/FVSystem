package App.Service;

import App.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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
    public List<ArchiveFV> getAllArchiveFVs() {
        return archiveFVRepo.findAll();
    }

    @Transactional
    public ArchiveFV getArchiveFV(Integer ID) {
        return archiveFVRepo.findById(ID);
    }

    @Transactional
    public List<ArchiveFVRevision> getAllArchiveRevisions() { return archiveFVRevisionRepo.findAll(); }

    @Transactional
    public List<ArchiveFVRevision> getArchiveRevisionByFV(Integer fvID) { return archiveFVRevisionRepo.findAllByFv(fvID); }

    @Transactional
    public List<ArchivePayment> getAllArchivePayments() {
        return archivePaymentRepo.findAll();
    }

    @Transactional
    public List<ArchivePayment> getArchivePaymentByFV(Integer fvID) {
        return archivePaymentRepo.findAllByFv(fvID);
    }

    @Transactional
    @Modifying
    public void deleteArchiveFV(Integer ID) {
        archiveFVRepo.delete(ID);
    }
}
