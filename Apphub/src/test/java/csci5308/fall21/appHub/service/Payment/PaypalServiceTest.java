package csci5308.fall21.appHub.service.Payment;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import csci5308.fall21.appHub.database.dao.payment.IPaymentDao;
import csci5308.fall21.appHub.model.payment.PaypalPayment;
import csci5308.fall21.appHub.service.payment.IPaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PaypalServiceTest {

    public final String INTENT = "ORDER";
    public final String HREF1 = "\"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd\\u003d_express-checkout\\u0026token\\u003dEC-22T05746UV629521A\"";
    public final String HREF2 = "https://api.sandbox.paypal.com/v1/payments/payment/PAYID-MGYSSHY89S83489B3668625J/execute";

    @Autowired
    IPaymentService paymentService;


    @MockBean
    IPaymentDao paymentDao;

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    public PaypalPayment createPaymentModel(String intent){
        PaypalPayment pp = new PaypalPayment("5", "USD", intent, "Sub", "Paypal", "4", "0.5", "0.5");
        return pp;
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    @Test
    void paymentServiceWorks() {
        assertNotNull(paymentService);
    }

    /**
     *
     * @author Pallavi Cherukupalli
     * */
    @Test
    void testGetPaymentLinks() throws PayPalRESTException {
        List<Links> links = new ArrayList<>();
        Links link1 = new Links();
        Links link2 = new Links();
        link1.setHref(HREF1);
        link2.setHref(HREF2);
        links.add(link1);
        links.add(link2);
        Payment payment = paymentService.createPaymentOb(createPaymentModel(INTENT));
        when(paymentDao.getAPILinks(payment)).thenReturn(links);
        assertEquals(links, paymentService.getPaymentLinks(payment));
    }


}
