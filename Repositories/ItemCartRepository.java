package Repositories;

import Sales.ItemCart;

public class ItemCartRepository {
    private ItemCart[] items = new ItemCart[100];
    private int count = 0;

    public void add(ItemCart item) {
        if (count < items.length) {
            items[count++] = item;
        }
    }

    public ItemCart getByIndex(int index) {
        if (index >= 0 && index < count) {
            return items[index];
        }
        return null;
    }

    public int size() {
        return count;
    }
}