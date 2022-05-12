package csci5308.fall21.appHub.service.payment;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import csci5308.fall21.appHub.model.payment.PaypalPayment;

import java.util.List;

public interface IPaymentService {
     Payment createPaymentOb(PaypalPayment paypalPayment);

     List<Links> getPaymentLinks(Payment payment) throws PayPalRESTException;
}
