package main.by.library.entity;

import java.io.Serializable;
import java.util.List;

public class ShoppingCart implements Serializable {
    private List<Book> shoppingList;

    public ShoppingCart(List<Book> shoppingCart) {
        this.shoppingList = shoppingCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingCart that = (ShoppingCart) o;

        return shoppingList != null ? shoppingList.equals(that.shoppingList) : that.shoppingList == null;
    }

    @Override
    public int hashCode() {
        return shoppingList != null ? shoppingList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shoppingCart=" + shoppingList +
                '}';
    }

    public List<Book> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<Book> shoppingList) {
        this.shoppingList = shoppingList;
    }
}
