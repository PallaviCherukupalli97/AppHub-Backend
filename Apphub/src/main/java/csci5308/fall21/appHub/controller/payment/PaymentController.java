package csci5308.fall21.appHub.controller.payment;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import csci5308.fall21.appHub.model.payment.PaypalPayment;
import csci5308.fall21.appHub.service.payment.PaymentService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @PostMapping(path = "/api/makePayment")
    /**
     *
     * @author Pallavi Cherukupalli
     * */

    public ResponseEntity<Object> pay(@RequestBody PaypalPayment paypalPayment) throws PayPalRESTException {
        try
        {
            Payment payment = paymentService.createPaymentOb(paypalPayment);
            List<Links> paymentLinks = paymentService.getPaymentLinks(payment);
            Paypal paypal = new Paypal();
            String response = paypal.routeToPaypal(paymentLinks);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch(Exception ex)
        {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
        
    }

}


