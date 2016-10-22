package uk.ac.brighton.uni.ch629.catshop.database.tables.records;

import com.google.gson.JsonObject;
import uk.ac.brighton.uni.ch629.catshop.database.CatShop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A record of the Product Table, containing all the data in an object.
 */
public class Product {
    private static CatShop database = new CatShop();
    private int productNumber = -1; //NOTE: Not final because of Auto Increment on the table
    private String description, image;
    private double price;
    private int stock;

    private Product() {
    }

    public Product(int productNumber, String description, double price, int stock, String image) {
        this(description, price, stock, image);
        this.productNumber = productNumber;
    }

    public Product(String description, double price, int stock, String image) {
        this.description = description;
        this.image = image;
        this.price = price;
        this.stock = stock;
    }

    public Product(String description, double price, String image) {
        this(description, price, 0, image);
    }

    /**
     * Delete all records in this table.
     */
    public static void deleteAll() { //NOTE: Only really for testing
        String sql = "DELETE FROM Product;";
        database.executeUpdate(sql);
    }

    /**
     * Get a Product from the database.
     *
     * @param productNumber The ID of the Product
     * @return The product if exists else null
     */
    public static Product getProduct(int productNumber) {
        String sql = String.format("SELECT * FROM Product WHERE ProductNumber=%d;", productNumber);
        try (Connection c = database.createConnection(); Statement stmt = c.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) { //TODO: Could use getAll and only get the first Product, but may take more time because of the loop.
                return new Product(
                        rs.getInt("ProductNumber"),
                        rs.getString("ProductDescription"),
                        rs.getFloat("ProductPrice"),
                        rs.getInt("ProductStock"),
                        rs.getString("ProductImage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; //Didn't find it.
    }

    /**
     * Get all Products in the database.
     * @return A list of all Products
     */
    public static List<Product> getAll() {
        String sql = "SELECT * FROM Product ORDER BY ProductNumber;";
        List<Product> productSet = new ArrayList<>();
        try (Connection c = database.createConnection(); Statement stmt = c.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                productSet.add(new Product(
                        rs.getInt("ProductNumber"),
                        rs.getString("ProductDescription"),
                        rs.getFloat("ProductPrice"),
                        rs.getInt("ProductStock"),
                        rs.getString("ProductImage")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productSet; //Didn't find it.
    }

    /**
     * Create the Product Table
     */
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Product(" +
                "ProductNumber INT(11) PRIMARY KEY AUTO_INCREMENT, " +
                "ProductDescription VARCHAR(45) NOT NULL, " +
                "ProductImage VARCHAR(45) NOT NULL, " +
                "ProductStock INT(11), " +
                "ProductPrice DECIMAL(12, 2));";
        database.executeUpdate(sql);
    }

    /**
     * Drop the Product Table
     */
    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS Product;";
        database.executeUpdate(sql);
    }

    public static Product fromJsonObject(JsonObject object) { //TODO: Some sort of checks on this? Probably don't need it as only data from code should be creating the object
        Product product = new Product();
        product.productNumber = object.get("productNumber").getAsInt();
        product.description = object.get("description").getAsString();
        product.image = object.get("image").getAsString();
        product.stock = object.get("stock").getAsInt();
        product.price = object.get("price").getAsDouble();
        return product;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Insert a Product into the database using the fields in this object.
     */
    //NOTE: https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-last-insert-id.html
    public void create() {
        String sql = "INSERT INTO Product(ProductDescription, ProductImage, ProductPrice, ProductStock) VALUES('%s', '%s', %f, %d);";
        try (Connection c = database.createConnection(); Statement stmt = c.createStatement()) {
            stmt.executeUpdate(String.format(sql, description, image, price, stock), Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) productNumber = resultSet.getInt(1);
            else {
                //Didn't find get any generated key??
            }
            //NOTE: Connection & Statement auto close, ResultSet closes when it's Statement closes
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the Product in the Database with the fields in this object
     */
    public void update() {
        String sql = "UPDATE Product SET ProductDescription='%s', ProductImage='%s', ProductStock=%d, ProductPrice=%f WHERE ProductNumber=%d;";
        database.executeUpdate(sql, description, image, stock, price, productNumber);
    }

    /**
     * Delete this Product from the Database.
     */
    public void delete() {
        String sql = "DELETE FROM Product WHERE ProductNumber=%d;";
        database.executeUpdate(sql, productNumber);
    }

    /**
     * Converts the Product into a Gson JSONObject.
     * @return A JsonObject conversion of this Product
     */
    public JsonObject toJsonObject() {
        JsonObject object = new JsonObject();
        object.addProperty("productNumber", productNumber);
        object.addProperty("description", description);
        object.addProperty("image", image);
        object.addProperty("stock", stock);
        object.addProperty("price", toDecimalPlaces(price, 2));
        return object;
    }

    /**
     * Formats a double to a specific amount of decimal places
     *
     * @param d             The double to convert
     * @param decimalPlaces The decimal places to format the double into
     * @return The formatted double
     */
    private double toDecimalPlaces(double d, int decimalPlaces) {
        return Double.parseDouble(String.format(String.format("%%.%df", decimalPlaces), d));
    }

    public String getImage() {
        return image;
    }
}