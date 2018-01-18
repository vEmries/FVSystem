package App.Service;

import App.Exception.InvalidDataException;
import App.Model.Address;
import App.Model.AddressRepo;
import App.Model.Contractor;
import App.Model.ContractorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ContractorService {

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    ContractorRepo contractorRepo;

    // Metody dla Contractor.class i Address.class

    @Transactional
    @Modifying
    public void createNewContractor(String company, String NIP, String bank, String account, String contactnr, String mail, String note,
                                    String country, String province, String city, String zip, String street) throws InvalidDataException {

        if (company.trim().equals("") || NIP.trim().equals("")) {
            throw new InvalidDataException("Błąd dodania nowego kontrahenta. Brak nazwy lub NIP-u");
        }

        Address newAddress = new Address(country, province, city, zip, street);
        addressRepo.save(newAddress);
        Contractor newContractor = new Contractor(company, NIP, bank, account, newAddress.getId(), contactnr, mail, note);
        contractorRepo.save(newContractor);
    }

    @Transactional
    @Modifying
    public void updateContractor(Contractor toUpdate) throws InvalidDataException {
        if (toUpdate.getCompany().trim().equals("") || toUpdate.getNip().trim().equals("")) {
            throw new InvalidDataException("Błąd edycji kontrahenta '" + toUpdate.getCompany() + "'. Nowa nazwa lub NIP są puste");
        }

        toUpdate.updateShortName();
        toUpdate.setAddress(contractorRepo.findById(toUpdate.getId()).getAddress());
        contractorRepo.save(toUpdate);
    }

//    @Transactional
//    @Modifying
//    public void updateAddress(Integer ID, String counry, String province, String city, String zip, String street) {
//        Address updatedAddress = addressRepo.findById(ID);
//        updatedAddress.setCountry(counry);
//        updatedAddress.setProvince(province);
//        updatedAddress.setCity(city);
//        updatedAddress.setZip(zip);
//        updatedAddress.setStreet(street);
//    }

    @Transactional
    @Modifying
    public void updateAddress(Address toUpdate) {
        addressRepo.save(toUpdate);
    }

    @Transactional
    @Modifying
    public void deleteContractor(Integer ID) {
        Integer addressID = getContractor(ID).getAddress();
        contractorRepo.delete(ID);
        addressRepo.delete(addressID);
    }

    @Transactional
    public List<Contractor> getAllContractors() {
        return contractorRepo.findAll();
    }

    @Transactional
    public Contractor getContractor(Integer ID) {
        return contractorRepo.findById(ID);
    }

    @Transactional
    public Contractor getContractorByAddress(Integer addressID) { return contractorRepo.findByAddress(addressID); }

    @Transactional
    public List<Address> getAllAdresses() { return addressRepo.findAll(); }

    @Transactional
    public Address getAddressForContractor(Integer addressID) {
        return addressRepo.findById(addressID);
    }

}
