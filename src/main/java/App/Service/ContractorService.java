package App.Service;

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
                                    String country, String province, String city, String zip, String street) {

        Address newAddress = new Address(country, province, city, zip, street);
        addressRepo.save(newAddress);
        Contractor newContractor = new Contractor(company, NIP, bank, account, newAddress.getId(), contactnr, mail, note);
        contractorRepo.save(newContractor);
    }

    @Transactional
    @Modifying
    public void updateContractor(Integer ID, String company, String NIP, String bank, String account, String contactnr, String mail, String note) {
        Contractor updatedContractor = contractorRepo.findById(ID);
        updatedContractor.setCompany(company);
        updatedContractor.setNip(NIP);
        updatedContractor.setBank(bank);
        updatedContractor.setAccount(account);
        updatedContractor.setContactnr(contactnr);
        updatedContractor.setMail(mail);
        updatedContractor.setNote(note);
    }

    @Transactional
    @Modifying
    public void updateAddress(Integer ID, String counry, String province, String city, String zip, String street) {
        Address updatedAddress = addressRepo.findById(ID);
        updatedAddress.setCountry(counry);
        updatedAddress.setProvince(province);
        updatedAddress.setCity(city);
        updatedAddress.setZip(zip);
        updatedAddress.setStreet(street);
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
    public Address getAddressForContractor(Integer addressID) {
        return addressRepo.findById(addressID);
    }

}
