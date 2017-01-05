package uk.ac.brighton.uni.ch629.catshop.update;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class CollectOrder implements Update { //Collection -> Server -> ShopDisplay
    private final int orderID;

    public CollectOrder(int orderID) {
        this.orderID = orderID;
    }

    @Override
    public String getType() {
        return "CollectOrder";
    }

    public int getOrderID() {
        return orderID;
    }
}
