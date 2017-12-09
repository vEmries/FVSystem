package App.Controller;

import App.Model.Payment;
import App.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @RequestMapping(value = "/payment/{fvID}", method = RequestMethod.GET)
    public List<Payment> getPaymentByFV(@PathVariable(value = "fvID") Integer fvID) {
        return paymentService.getPaymentByFV(fvID);
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public void createPayment(@RequestBody Payment tmpPayment) {
        paymentService.createNewPayment(tmpPayment.getFv(), tmpPayment.getIssuedate(), tmpPayment.getQuota(), tmpPayment.getNote());
    }

    @RequestMapping(value = "/payment", method = RequestMethod.PUT)
    public void updatePayment(@RequestBody Payment tmpPayment) {
        paymentService.updatePayment(tmpPayment.getId(), tmpPayment.getFv(), tmpPayment.getQuota(), tmpPayment.getNote());
    }

    @RequestMapping(value = "/payment/{ID}", method = RequestMethod.DELETE)
    public void deletePayment(@PathVariable(value = "ID") Integer ID) {
        paymentService.deletePayment(ID);
    }
}
