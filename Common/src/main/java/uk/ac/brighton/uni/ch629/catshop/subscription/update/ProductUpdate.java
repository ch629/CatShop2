package uk.ac.brighton.uni.ch629.catshop.subscription.update;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.brighton.uni.ch629.catshop.data.Product;

@JsonAutoDetect
public class ProductUpdate extends Update {
    private final int oldID;
    private final Product newProduct;

    @JsonCreator
    public ProductUpdate(@JsonProperty("oldID") int oldID,
                         @JsonProperty("newProduct") Product newProduct) {
        super(UpdateType.PRODUCT_UPDATE);
        this.oldID = oldID;
        this.newProduct = newProduct;
    }
}