/**
 *
 * @author Pallavi Cherukupalli
 * */

package csci5308.fall21.appHub.database.dao.payment;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.util.List;

public interface IPaymentDao {
     List<Links> getAPILinks(Payment payment) throws PayPalRESTException;
}
