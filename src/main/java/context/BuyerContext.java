package context;

import models.Buyer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BuyerContext {

    String dataFilePath = "./../../resources/buyerData.txt";

    public Buyer getBuyerByEmail(String email) throws IOException {
        Buyer buyer = new Buyer();
        BufferedReader br = new BufferedReader(new FileReader(dataFilePath));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains(email)) {
                String data[] = line.split(",");
                buyer.name = data[0];
                buyer.email = data[1];
                buyer.phoneNumber = data[2];
                buyer.city = data[3];
                buyer.address = data[4];
                buyer.pinCode = data[5];

            }
        }
        return buyer;
    }
}
