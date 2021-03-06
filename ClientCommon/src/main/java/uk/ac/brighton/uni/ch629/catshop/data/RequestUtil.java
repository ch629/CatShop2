package uk.ac.brighton.uni.ch629.catshop.data;

import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import uk.ac.brighton.uni.ch629.catshop.JsonHelper;
import uk.ac.brighton.uni.ch629.catshop.Response;

import java.util.List;

public class RequestUtil {
    public static final String serverURL = "http://localhost:8090/"; //TODO: Make a configuration file for this, could use Java Properties.

    //TODO: Downloading Images & Caching them.
    static {
        Unirest.setObjectMapper(new ObjectMapper() {
            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                return JsonHelper.jsonToObject(value, valueType);
            }

            @Override
            public String writeValue(Object value) {
                return JsonHelper.objectToString(value);
            }
        });
    }

    public static String sendStringGet(String url) throws UnirestException {
        return Unirest.get(serverURL + url).asObject(String.class).getBody();
    }

    public static Response sendGet(String url) throws UnirestException { //TODO: Response may be redundant, check this properly.
        return Unirest.get(serverURL + url).asObject(Response.class).getBody();
    }

    public static Response sendPost(Request request, String url) throws UnirestException {
        return Unirest.post(serverURL + url).body(request).asObject(Response.class).getBody();
    }

    public static Response sendDelete(Request request, String url) throws UnirestException {
        return Unirest.delete(serverURL + url).body(request).asObject(Response.class).getBody();
    }

    public static <T> T getType(Class<T> clazz, int identifier, String accessUrl) throws UnirestException {
        return Unirest.get(serverURL + "/" + String.format(accessUrl, identifier)).asObject(clazz).getBody();
    }

    public static <T> T getTypeNoException(Class<T> clazz, int identifier, String accessUrl) {
        try {
            return getType(clazz, identifier, accessUrl);
        } catch (UnirestException e) {
            e.printStackTrace(); //Connection error.
        }
        return null;
    }

    public static Product getProduct(int productNumber) {
        return getTypeNoException(Product.class, productNumber, "product/%d");
    }

    public static List<Product> getAllProducts() {
        return getTypeNoException(List.class, 0, "product/all"); //This should be the right type of List.
    }

    public static Order getOrder(int orderID) {
        return getTypeNoException(Order.class, orderID, "order/%d");
    }

    public static int addOrder(Basket basket) {
        try {
            return Unirest.post(serverURL + "/order").body(basket).asObject(Integer.class).getBody(); //TODO: Check this
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void pickOrder(int orderID) {
        try {
            Unirest.post(serverURL + String.format("order/%d/pick", orderID)).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public static void collectOrder(int orderID) {
        Unirest.post(serverURL + String.format("order/%d/collect", orderID));
    }
}
