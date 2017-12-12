package App.Controller;

import App.Model.FV;
import App.Model.FVRepo;
import App.Model.FVRevision;
import App.Service.FVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.Date;
import java.util.List;

@RestController
public class FVController {

    @Autowired
    FVService fvService;

    // Mapowania dla FV

    @RequestMapping(value = "/fv", method = RequestMethod.GET)
    public List<FV> getFVs() { return fvService.getAllFVs(); }

    @RequestMapping(value = "/fv/{ID}", method = RequestMethod.GET)
    public FV getFV(@PathVariable(value = "ID") Integer fvID) { return fvService.getFV(fvID); }

    @RequestMapping(value = "/fv", method = RequestMethod.POST)
    public void createFV(@RequestBody FV tmpFV) {
        fvService.createNewFV(tmpFV.getFvnumber(), tmpFV.getContractor(), tmpFV.getIssuedate(), tmpFV.getDuedate(), tmpFV.getValue(), tmpFV.getNote());
    }

    @RequestMapping(value = "/fv", method = RequestMethod.PUT)
    public void updateFV(@RequestBody FV tmpFV) {
        fvService.updateFV(tmpFV.getId(), tmpFV.getFvnumber(), tmpFV.getContractor(), tmpFV.getIssuedate(), tmpFV.getDuedate(), tmpFV.getValue(), tmpFV.getNote());
    }

    @RequestMapping(value = "/fv/{ID}", method = RequestMethod.DELETE)
    public void deleteFV(@PathVariable(name = "ID") Integer fvID) {
        fvService.deleteFV(fvID);
    }

    // Mapowania dla FVRevision

    @RequestMapping(value = "/fvr", method = RequestMethod.GET)
    public List<FVRevision> getRevisions() { return fvService.getAllRevisions(); }

    @RequestMapping(value = "/fvr/{ID}", method = RequestMethod.GET)
    public FVRevision getRevision(@PathVariable(value = "ID") Integer ID) { return fvService.getRevision(ID); }

    @RequestMapping(value = "/fvr", method = RequestMethod.POST)
    public void createRevision(@RequestBody FVRevision tmpRevision) {
        fvService.createNewRevision(tmpRevision.getFvnumber(), tmpRevision.getFv(), tmpRevision.getIssuedate(), tmpRevision.getQuota(), tmpRevision.getNote());
    }

    @RequestMapping(value = "/fvr", method = RequestMethod.PUT)
    public void updateRevision(@RequestBody FVRevision tmpRevision) {
        fvService.updateRevision(tmpRevision.getId(), tmpRevision.getFvnumber(), tmpRevision.getFv(), tmpRevision.getIssuedate(), tmpRevision.getQuota(), tmpRevision.getNote());
    }

    @RequestMapping(value = "/fvr/{ID}", method = RequestMethod.DELETE)
    public void deleteRevision(@PathVariable(name = "ID") Integer ID) { fvService.deleteRevision(ID); }
}
