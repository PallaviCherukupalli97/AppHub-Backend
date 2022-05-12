/**
 *
 * @author Pallavi Cherukupalli
 * */

package csci5308.fall21.appHub.database.dao.payment;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentDao implements IPaymentDao{

    @Autowired
    private APIContext context;

    /**
     *
     * @author Pallavi Cherukupalli
     * */

    public List<Links> getAPILinks(Payment payment) throws PayPalRESTException {
       return payment.create(context).getLinks();
    }
}
