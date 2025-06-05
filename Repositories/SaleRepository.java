package Repositories;

import Sales.Sale;

public class SaleRepository {
    private Sale[] sales = new Sale[100];
    private int count = 0;

    public void add(Sale sale) {
        if (count < sales.length) {
            sales[count++] = sale;
        }
    }

    public Sale getById(Long id) {
        for (int i = 0; i < count; i++) {
            if (sales[i].getId().equals(id)) {
                return sales[i];
            }
        }
        return null;
    }

    public int size() {
        return count;
    }
}