package Repositories;

import Sales.Cart;

public class CartRepository {
    private Cart[] carts = new Cart[100];
    private int count = 0;

    public void add(Cart cart) {
        if (count < carts.length) {
            carts[count++] = cart;
        }
    }

    public Cart getById(Long id) {
        for (int i = 0; i < count; i++) {
            if (carts[i].getId().equals(id)) {
                return carts[i];
            }
        }
        return null;
    }

    public int size() {
        return count;
    }
}