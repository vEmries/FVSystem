package App.Controller;

import App.Exception.InvalidDataException;
import App.Model.Address;
import App.Model.Contractor;
import App.Service.ContractorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContractorController {

    private static Logger logger = LoggerFactory.getLogger(ContractorController.class);

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

    // Mapowania dla Address

    @RequestMapping(value = "/contractor/address", method = RequestMethod.GET)
    public List<Address> getAllAdresses() { return contractorService.getAllAdresses(); }

    @RequestMapping(value = "/contractor/address/{ID}", method = RequestMethod.GET)
    public Address getAddressForContractor(@PathVariable(name = "ID") Integer addressID) {
        return contractorService.getAddressForContractor(addressID);
    }

    // Mapowanie dla setu (Contractor + Address)

    private Address toAddAddress;

    @RequestMapping(value = "/contractor/address", method = RequestMethod.POST)
    public void prepareAddress(@RequestBody Address tmpAddress) {
        toAddAddress = tmpAddress;
    }

    @RequestMapping(value = "/contractor", method = RequestMethod.POST)
    public ResponseEntity createContractor(@RequestBody Contractor tmpContractor) {
        try {
            contractorService.createContractor(tmpContractor.getCompany(), tmpContractor.getCompanyshort(), tmpContractor.getNip(), tmpContractor.getBank(), tmpContractor.getAccount(), tmpContractor.getContactnr(), tmpContractor.getMail(), tmpContractor.getNote(),
                    toAddAddress.getCountry(), toAddAddress.getProvince(), toAddAddress.getCity(), toAddAddress.getZip(), toAddAddress.getStreet());
        } catch (InvalidDataException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            logger.error("Błąd dodania kontrehenta '" + tmpContractor.getCompany() + "'. Wymagane pole wynosi null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        toAddAddress.setCountry(null);
        toAddAddress.setProvince(null);
        toAddAddress.setZip(null);
        toAddAddress.setCity(null);
        toAddAddress.setStreet(null);

        logger.info("Dodano konrahenta '" + tmpContractor.getCompany() + "'");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/contractor", method = RequestMethod.PUT)
    public ResponseEntity updateContractor(@RequestBody Contractor tmpContractor) {
        try {
            contractorService.updateContractor(tmpContractor);
        } catch (InvalidDataException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Edytowano kontrahenta '" + tmpContractor.getCompany() +"'");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/contractor/address", method = RequestMethod.PUT)
    public void updateAddress(@RequestBody Address tmpAddress) {
        contractorService.updateAddress(tmpAddress);

        logger.info("Edytowano adres kontrahenta '" + contractorService.getContractorByAddress(tmpAddress.getId()).getCompany() + "'");
    }

    @RequestMapping(value = "/contractor/{ID}", method = RequestMethod.DELETE)
    public void deleteContractor(@PathVariable(name = "ID") Integer ID) {
        String deletedContractor = contractorService.getContractor(ID).getCompany();
        contractorService.deleteContractor(ID);

        logger.info("Usunięto kontrahenta '" + deletedContractor + "'");
    }
}
