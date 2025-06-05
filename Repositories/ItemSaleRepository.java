package Repositories;

import Sales.ItemSale;

public class ItemSaleRepository {
    private ItemSale[] items = new ItemSale[100];
    private int count = 0;

    public void add(ItemSale item) {
        if (count < items.length) {
            items[count++] = item;
        }
    }

    public ItemSale getByIndex(int index) {
        if (index >= 0 && index < count) {
            return items[index];
        }
        return null;
    }

    public int size() {
        return count;
    }
}