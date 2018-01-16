package App.Controller;

import App.Exception.InvalidDataException;
import App.Model.FV;
import App.Model.FVRepo;
import App.Model.FVRevision;
import App.Service.FVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.Date;
import java.util.List;

@RestController
public class FVController {

    private static Logger logger = LoggerFactory.getLogger(FVController.class);

//    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    FVService fvService;

    // Mapowania dla FV

    @RequestMapping(value = "/fv", method = RequestMethod.GET)
    public List<FV> getFVs() { return fvService.getAllFVs(); }

    @RequestMapping(value = "/fv/{ID}", method = RequestMethod.GET)
    public FV getFV(@PathVariable(value = "ID") Integer fvID) { return fvService.getFV(fvID); }

    @RequestMapping(value = "/fv", method = RequestMethod.POST)
    public ResponseEntity createFV(@RequestBody FV tmpFV) {
        try {
            fvService.createNewFV(tmpFV.getFvnumber(), tmpFV.getContractor(), tmpFV.getIssuedate(), tmpFV.getDuedate(), tmpFV.getValue(), tmpFV.getNote());
        } catch (InvalidDataException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            logger.error("Błąd dodania faktury '" + tmpFV.getFvnumber() + "'. Wymagane pole wynosi null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Dodano fakturę '" + tmpFV.getFvnumber() + "'");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/fv", method = RequestMethod.PUT)
    public ResponseEntity updateFV(@RequestBody FV tmpFV) {
        try {
            fvService.updateFV(tmpFV.getId(), tmpFV.getFvnumber(), tmpFV.getContractor(), tmpFV.getIssuedate(), tmpFV.getDuedate(), tmpFV.getValue(), tmpFV.getNote());
        } catch (InvalidDataException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Edytowano fakturę '" + tmpFV.getFvnumber() + "'");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/fv/{ID}", method = RequestMethod.DELETE)
    public void deleteFV(@PathVariable(name = "ID") Integer fvID) {
        String deletedFV = fvService.getFV(fvID).getFvnumber();
        fvService.deleteFV(fvID);

        logger.info("Usunięto fakturę '" + deletedFV + "'");
    }

    // Mapowania dla FVRevision

    @RequestMapping(value = "/fvr", method = RequestMethod.GET)
    public List<FVRevision> getRevisions() { return fvService.getAllRevisions(); }

    @RequestMapping(value = "/fvr/{ID}", method = RequestMethod.GET)
    public FVRevision getRevision(@PathVariable(value = "ID") Integer ID) { return fvService.getRevision(ID); }

    @RequestMapping(value = "/fvr", method = RequestMethod.POST)
    public ResponseEntity createRevision(@RequestBody FVRevision tmpRevision) {
        try {
            fvService.createNewRevision(tmpRevision.getFvnumber(), tmpRevision.getFv(), tmpRevision.getIssuedate(), tmpRevision.getQuota(), tmpRevision.getNote());
        } catch (InvalidDataException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            logger.error("Błąd dodania korekty '" + tmpRevision.getFvnumber() + "'. Wymagane pole wynosi null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Dodano korektę '" + tmpRevision.getFvnumber() +"'");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/fvr", method = RequestMethod.PUT)
    public ResponseEntity updateRevision(@RequestBody FVRevision tmpRevision) {
        try {
            fvService.updateRevision(tmpRevision.getId(), tmpRevision.getFvnumber(), tmpRevision.getFv(), tmpRevision.getIssuedate(), tmpRevision.getQuota(), tmpRevision.getNote());
        } catch (InvalidDataException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Edytowano korektę '" + tmpRevision.getFvnumber() + "'");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/fvr/{ID}", method = RequestMethod.DELETE)
    public void deleteRevision(@PathVariable(name = "ID") Integer ID) {
        String deletedFV = fvService.getRevision(ID).getFvnumber();
        fvService.deleteRevision(ID);

        logger.info("Usunięto korektę '" + deletedFV + "'");
    }
}
