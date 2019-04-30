package com.services;

import com.models.AddressBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author dawid
 */
public class AddressAPI {

    private final String key = "qdm7hRvvgkWMdFisnI2pmw12630"; //API key from getAddress.io
    private final String api = "https://api.getAddress.io/find/"; //The API address, according to documentation post code goes on the end of this link
    private String postCode;

    public AddressAPI(String postCode) {
        this.postCode = postCode;
    }

    public ArrayList<AddressBean> lookupAddress() {
        ArrayList<AddressBean> addresses = new ArrayList<>();

        try {
            postCode = postCode.replace(" ", ""); // takes care of any whitespace
            URL theURL = new URL(api + URLEncoder.encode(postCode, "UTF-8")); // Creates new URL using the postcode and the API address
            HttpURLConnection httpCON = (HttpURLConnection) theURL.openConnection();

            httpCON.setRequestMethod("GET");
            httpCON.setRequestProperty("API-KEY", key); // required for authentication

            if (httpCON.getResponseCode() == 200) {    // 200 is a successful response. A code over 400 tends to be errors
                StringBuffer resp;
                try ( 
                        BufferedReader input = new BufferedReader(new InputStreamReader(httpCON.getInputStream()))) {
                    String currentLine;
                    resp = new StringBuffer();
                    while ((currentLine = input.readLine()) != null) {
                        resp.append(currentLine);
                    }
                }
                
                JSONObject jObject = (JSONObject) (new JSONParser()).parse(resp.toString());
                JSONArray jAddresses = (JSONArray) jObject.get("addresses");

                for (Object temp : jAddresses) {
                    String address = temp + ", " + postCode;
                    addresses.add(new AddressBean(address));
                }
            } else {
                System.out.println("No addresses found");
            }
        } catch (IOException | ParseException e) {
            System.out.println("No addresses found");

        }
        return addresses;
    } 
}