package csci5308.fall21.appHub.service.payment;

import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import csci5308.fall21.appHub.database.dao.payment.IPaymentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static csci5308.fall21.appHub.util.PaymentConstants.*;
import csci5308.fall21.appHub.model.payment.PaypalPayment;

@Service
public class PaymentService implements IPaymentService{

    @Autowired
    private IPaymentDao paymentDao;

    @Override
    public Payment createPaymentOb(PaypalPayment paypalPayment) {
        Amount amount = new Amount();
        Transaction transaction = new Transaction();
        Payer payer = new Payer();
        List<Transaction> transactions = new ArrayList<>();
        Payment payment = new Payment();
        Details details = new Details();
        RedirectUrls redirectUrls = new RedirectUrls();
        amount.setCurrency(paypalPayment.getCurrency());
        amount.setTotal(paypalPayment.getPrice());
        transaction.setDescription(paypalPayment.getDescription());
        transaction.setAmount(amount);
        details.setSubtotal(paypalPayment.getSubtotal());
        details.setTax(paypalPayment.getTax());
        details.setShipping(paypalPayment.getShipping());
        amount.setDetails(details);
        transactions.add(transaction);
        payment.setTransactions(transactions);
        payer.setPaymentMethod(paypalPayment.getMethod());
        payment.setIntent(paypalPayment.getIntent());
        payment.setPayer(payer);
        redirectUrls.setCancelUrl(cancelURL);
        redirectUrls.setReturnUrl(successURL);
        payment.setRedirectUrls(redirectUrls);
        return payment;
    }

    public List<Links> getPaymentLinks(Payment payment) throws PayPalRESTException{
        return paymentDao.getAPILinks(payment);
    }

}
