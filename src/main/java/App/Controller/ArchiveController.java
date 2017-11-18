package App.Controller;

import App.Model.ArchiveFV;
import App.Model.ArchivePayment;
import App.Service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArchiveController {

    @Autowired
    ArchiveService archiveService;

    @RequestMapping(value = "/archive/fv", method = RequestMethod.GET)
    public List<ArchiveFV> getArchiveFVs() {
        return archiveService.getAllArchiveFVs();
    }

    @RequestMapping(value = "/archive/fv/{ID}", method = RequestMethod.GET)
    public ArchiveFV getArchiveFV(@PathVariable(name = "ID") Integer ID) {
        return archiveService.getArchiveFV(ID);
    }

    @RequestMapping(value = "/archive/payment", method = RequestMethod.GET)
    public List<ArchivePayment> getArchivePayments() {
        return archiveService.getAllArchivePayments();
    }

    @RequestMapping(value = "/archive/payment/{fvID}", method = RequestMethod.GET)
    public List<ArchivePayment> getArchivePaymentsByFV(@PathVariable(name = "fvID") Integer fvID) {
        return archiveService.getArchivePaymentByFV(fvID);
    }

    @RequestMapping(value = "/archive", method = RequestMethod.POST)
    public void archiveFV(@RequestParam Integer ID) {
        archiveService.archiveFV(ID);
    }

    //Param nie zadziała, dać RBody / FV / getID

    @RequestMapping(value = "/archive/{ID}", method = RequestMethod.DELETE)
    public void deleteArchiveFV(@PathVariable(name = "ID") Integer ID) {
        archiveService.deleteFV(ID);
    }
}
