package csci5308.fall21.appHub.config;

import java.util.HashMap;
import java.util.Map;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {
    @Value("${paypal.clientId}")
    private String clientId;
    @Value("${paypal.clientSecret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

     @Bean
     public APIContext getContext() throws PayPalRESTException
     {
         Map<String,String> modeMap = new HashMap<>();
         modeMap.put("mode", mode);
         OAuthTokenCredential credential = new OAuthTokenCredential(clientId, clientSecret,modeMap);
         APIContext context = new APIContext(credential.getAccessToken());
         context.setConfigurationMap(modeMap);
         return context;
     }
}
