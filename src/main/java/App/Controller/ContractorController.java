package App.Controller;

import App.Model.Address;
import App.Model.Contractor;
import App.Service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import java.util.List;

@RestController
public class ContractorController {

    @Autowired
    ContractorService contractorService;

    // Mapowania dla Contractor

    @RequestMapping(value = "/contractor", method = RequestMethod.GET)
    public List<Contractor> getAllContractors() {
        return contractorService.getAllContractors();
    }

    @RequestMapping(value = "/contractor/{ID}", method = RequestMethod.GET)
    public Contractor getContractor(@PathVariable(name = "ID") Integer ID) {
        return contractorService.getContractor(ID);
    }

    // Mapowanie dla Address

    @RequestMapping(value = "/contractor/address", method = RequestMethod.GET)
    public List<Address> getAllAdresses() { return contractorService.getAllAdresses(); }

    @RequestMapping(value = "/contractor/address/{ID}", method = RequestMethod.GET)
    public Address getAddressForContractor(@PathVariable(name = "ID") Integer addressID) {
        return contractorService.getAddressForContractor(addressID);
    }

    private Address toAddAddress;

    @RequestMapping(value = "/contractor/address", method = RequestMethod.POST)
    public void prepareAddress(@RequestBody Address tmpAddress) {
        toAddAddress = tmpAddress;
    }

    @RequestMapping(value = "/contractor", method = RequestMethod.POST)
    public void createContractor(@RequestBody Contractor tmpContractor) {
        contractorService.createNewContractor(tmpContractor.getCompany(), tmpContractor.getNip(), tmpContractor.getBank(), tmpContractor.getAccount(), tmpContractor.getContactnr(), tmpContractor.getMail(), tmpContractor.getNote(),
                                                toAddAddress.getCountry(), toAddAddress.getProvince(), toAddAddress.getCity(), toAddAddress.getZip(), toAddAddress.getStreet());

        toAddAddress.setCountry(null);
        toAddAddress.setProvince(null);
        toAddAddress.setZip(null);
        toAddAddress.setCity(null);
        toAddAddress.setStreet(null);
    }

    @RequestMapping(value = "/contractor", method = RequestMethod.PUT)
    public void updateContractor(@RequestBody Contractor tmpContractor) {
        contractorService.updateContractor(tmpContractor.getId(), tmpContractor.getCompany(), tmpContractor.getNip(), tmpContractor.getBank(), tmpContractor.getAccount(), tmpContractor.getContactnr(), tmpContractor.getMail(), tmpContractor.getNote());
    }

    @RequestMapping(value = "/contractor/address", method = RequestMethod.PUT)
    public void updateAddress(@RequestBody Address tmpAddress) {
        contractorService.updateAddress(tmpAddress.getId(), tmpAddress.getCountry(), tmpAddress.getProvince(), tmpAddress.getCity(), tmpAddress.getZip(), tmpAddress.getStreet());
    }

    @RequestMapping(value = "/contractor/{ID}", method = RequestMethod.DELETE)
    public void deleteContractor(@PathVariable(name = "ID") Integer ID) {
        contractorService.deleteContractor(ID);
    }
}
