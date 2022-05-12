/**
 *
 * @author Pallavi Cherukupalli
 * */

package csci5308.fall21.appHub.model.payment;

public class PaypalPayment {
    private String price;
    private String currency;
    private String intent;
    private String description;
    private String method;
    private String subtotal;
    private String tax;
    private String shipping;

    public PaypalPayment(String price, String currency, String intent, String description, String method, String subtotal, String tax, String shipping) {
        this.price = price;
        this.currency = currency;
        this.intent = intent;
        this.description = description;
        this.method = method;
        this.subtotal = subtotal;
        this.tax = tax;
        this.shipping = shipping;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getSubtotal()
    {
        return subtotal;
    }

    public void setSubtotal(String subtotal)
    {
        this.subtotal = subtotal;
    }

    public String getTax()
    {
        return tax;
    }

    public void setTax(String tax)
    {
        this.tax = tax;
    }

    public String getShipping()
    {
        return shipping;
    }

    public void setShipping(String shipping)
    {
        this.shipping = shipping;
    }
    
}
