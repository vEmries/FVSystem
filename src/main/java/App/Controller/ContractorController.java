package App.Controller;

import App.Model.Address;
import App.Model.Contractor;
import App.Service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContractorController {

    @Autowired
    ContractorService contractorService;

    @RequestMapping(value = "/contractor", method = RequestMethod.GET)
    public List<Contractor> getAllContractors() {
        return contractorService.getAllContractors();
    }

    @RequestMapping(value = "/contractor/{ID}", method = RequestMethod.GET)
    public Contractor getContractor(@PathVariable(name = "ID") Integer ID) {
        return contractorService.getContractor(ID);
    }

    @RequestMapping(value = "/contractor/address/{ID}", method = RequestMethod.GET)
    public Address getAddressForContractor(@PathVariable(name = "ID") Integer addressID) {
        return contractorService.getAddressForContractor(addressID);
    }

    @RequestMapping(value = "/contractor", method = RequestMethod.POST)
    public void createContractor(@RequestParam String company,
                                 @RequestParam String NIP,
                                 @RequestParam String bank,
                                 @RequestParam String account,
                                 @RequestParam String contactnr,
                                 @RequestParam String mail,
                                 @RequestParam String note,
                                 @RequestParam String country,
                                 @RequestParam String province,
                                 @RequestParam String city,
                                 @RequestParam String zip,
                                 @RequestParam String street) {
        contractorService.createNewContractor(company, NIP, bank, account, contactnr, mail, note, country, province, city, zip, street);
    }

    // @RequestBody -> potrzeba 2 obiektów, address i contractor, sprawdzić czy zrobi 2 lub jakąś nową klasę

    @RequestMapping(value = "/contractor", method = RequestMethod.PUT)
    public void updateContractor(@RequestParam Integer ID,
                                 @RequestParam String company,
                                 @RequestParam String NIP,
                                 @RequestParam String bank,
                                 @RequestParam String account,
                                 @RequestParam String contactnr,
                                 @RequestParam String mail,
                                 @RequestParam String note) {
        contractorService.updateContractor(ID, company, NIP, bank, account, contactnr, mail, note);
    }

    @RequestMapping(value = "/contractor/address", method = RequestMethod.PUT)
    public void updateAddress(@RequestParam Integer ID,
                              @RequestParam String country,
                              @RequestParam String province,
                              @RequestParam String city,
                              @RequestParam String zip,
                              @RequestParam String street) {
        contractorService.updateAddress(ID, country, province, city, zip, street);
    }
}
