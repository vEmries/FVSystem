package App.Controller;

import App.Exception.InvalidDataException;
import App.Model.Payment;
import App.Service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class PaymentController {

    private static Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    PaymentService paymentService;

    // Mapowania dla Payment

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @RequestMapping(value = "/payment/{fvID}", method = RequestMethod.GET)
    public List<Payment> getPaymentByFV(@PathVariable(value = "fvID") Integer fvID) {
        return paymentService.getPaymentByFV(fvID);
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public ResponseEntity createPayment(@RequestBody Payment tmpPayment) {
        try {
            paymentService.createNewPayment(tmpPayment.getFv(), tmpPayment.getIssuedate(), tmpPayment.getQuota(), tmpPayment.getNote());
        } catch (InvalidDataException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            logger.error("Błąd dodania płatności dla faktury o ID '" + tmpPayment.getFv() + "'. Wymagane pole wynosi null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Dodano płatność dla faktury o ID '" + tmpPayment.getFv() + "'");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/payment", method = RequestMethod.PUT)
    public ResponseEntity updatePayment(@RequestBody Payment tmpPayment) {
        try {
            paymentService.updatePayment(tmpPayment.getId(), tmpPayment.getFv(), tmpPayment.getQuota(), tmpPayment.getNote());
        } catch (InvalidDataException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Edycja płatności '" + tmpPayment.getId() + "'");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/payment/{ID}", method = RequestMethod.DELETE)
    public void deletePayment(@PathVariable(value = "ID") Integer ID) {
        paymentService.deletePayment(ID);

        logger.info("Usunięto płatność o ID '" + ID + "'");
    }
}
