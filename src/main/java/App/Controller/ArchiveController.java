package App.Controller;

import App.Model.ArchiveFV;
import App.Model.ArchiveFVRevision;
import App.Model.ArchivePayment;
import App.Model.FV;
import App.Service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArchiveController {

    @Autowired
    ArchiveService archiveService;

    // Mapowania dla ArchiveFV

    @RequestMapping(value = "/archive/fv", method = RequestMethod.GET)
    public List<ArchiveFV> getArchiveFVs() {
        return archiveService.getAllArchiveFVs();
    }

    @RequestMapping(value = "/archive/fv/{ID}", method = RequestMethod.GET)
    public ArchiveFV getArchiveFV(@PathVariable(name = "ID") Integer ID) {
        return archiveService.getArchiveFV(ID);
    }

    // Mapowanie dla ArchiveFVRevision

    @RequestMapping(value = "/archive/fvr", method = RequestMethod.GET)
    public List<ArchiveFVRevision> getArchiveRevisions() { return archiveService.getAllArchiveRevisions(); }

    @RequestMapping(value = "/archive/fvr/{fvID}", method = RequestMethod.GET)
    public List<ArchiveFVRevision> getArchiveRevisionByFV(@PathVariable(name = "fvID") Integer fvID) {
        return archiveService.getArchiveRevisionByFV(fvID);
    }

    // Mapowania dla ArchivePayment

    @RequestMapping(value = "/archive/payment", method = RequestMethod.GET)
    public List<ArchivePayment> getArchivePayments() {
        return archiveService.getAllArchivePayments();
    }

    @RequestMapping(value = "/archive/payment/{fvID}", method = RequestMethod.GET)
    public List<ArchivePayment> getArchivePaymentsByFV(@PathVariable(name = "fvID") Integer fvID) {
        return archiveService.getArchivePaymentByFV(fvID);
    }

    // Mapowania dla całego setu (FV + Revision + Payment)

    @RequestMapping(value = "/archive", method = RequestMethod.POST)
    public List<FV> autoArchive() { return archiveService.autoArchive(); }

    @RequestMapping(value = "/archive/{ID}", method = RequestMethod.POST)
    public void archiveFV(@PathVariable(name = "ID") Integer ID) {
        archiveService.archiveFV(ID);
    }


    @RequestMapping(value = "/archive/{ID}", method = RequestMethod.DELETE)
    public void deleteArchiveFV(@PathVariable(name = "ID") Integer ID) {
        archiveService.deleteArchiveFV(ID);
    }
}
