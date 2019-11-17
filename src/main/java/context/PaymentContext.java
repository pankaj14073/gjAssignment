package context;

import models.payment.CreditCardPayment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PaymentContext {

    String dataFilePath = "./src/main/resources/creditCardPaymentData.txt";

    public CreditCardPayment getCreditCardPaymentDetails(String cardNumber) throws IOException {
        CreditCardPayment creditCardPayment= new CreditCardPayment();
        BufferedReader br = new BufferedReader(new FileReader(dataFilePath));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains(cardNumber)) {
                String data[] = line.split(",");
                creditCardPayment.cardNumber=cardNumber;
                creditCardPayment.expiringDate=data[1];
                creditCardPayment.cvv=data[2];
                creditCardPayment.otp=data[3];
            }
        }
        return creditCardPayment;
    }
}
