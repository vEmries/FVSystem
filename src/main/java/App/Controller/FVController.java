package App.Controller;

import App.Model.FV;
import App.Model.FVRepo;
import App.Service.FVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.Date;

@RestController
public class FVController {

    @Autowired
    FVService fvService;

    @RequestMapping(value = "/fv", method = RequestMethod.GET)
    public Iterable<FV> getFVs() {
        return fvService.getAllFVs();
    }

    @RequestMapping(value = "/fv/{ID}", method = RequestMethod.GET)
    public FV getFV(@PathVariable(value = "ID") Integer fvID) {
        return fvService.getFV(fvID);
    }

//    @RequestMapping(value = "/fv", method = RequestMethod.POST)
//    public void createFV(@RequestParam String fvNumber,
//                         @RequestParam Integer contractor,
//                         @RequestParam Date issueDate,
//                         @RequestParam Date dueDate,
//                         @RequestParam Double sum,
//                         @RequestParam String note) {
//        fvService.createNewFV(fvNumber, contractor, issueDate, dueDate, sum, note);
//    }

    @RequestMapping(value = "/fv", method = RequestMethod.POST)
    public void createFV(@RequestBody FV tmpFV) {
        fvService.createNewFV(tmpFV.getFvnumber(), tmpFV.getContractor(), tmpFV.getIssuedate(), tmpFV.getDuedate(), tmpFV.getSum(), tmpFV.getNote());
    }

//    @RequestMapping(value = "/fv", method = RequestMethod.PUT)
//    public void updateFV(@RequestParam Integer fvID,
//                         @RequestParam String fvNumber,
//                         @RequestParam Integer contractor,
//                         @RequestParam Date dueDate,
//                         @RequestParam Double sum,
//                         @RequestParam String note) {
//        fvService.updateFV(fvID, fvNumber, contractor, dueDate, sum, note);
//    }

    @RequestMapping(value = "/fv", method = RequestMethod.PUT)
    public void updateFV(@RequestBody FV tmpFV) {
        fvService.updateFV(tmpFV.getId(), tmpFV.getFvnumber(), tmpFV.getContractor(), tmpFV.getDuedate(), tmpFV.getSum(), tmpFV.getNote());
    }

    @RequestMapping(value = "/fv/{ID}", method = RequestMethod.DELETE)
    public void deleteFV(@PathVariable(name = "ID") Integer fvID) {
        fvService.deleteFV(fvID);
    }
}
