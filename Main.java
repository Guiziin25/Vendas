package Sales;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Criando um carrinho
        Cart cart = new Cart(1L, BigDecimal.ZERO);
        System.out.println("Cart created with ID: " + cart.getId() + " and total: " + cart.getTotal());

        // Adicionando itens ao carrinho
        ItemCart itemCart = new ItemCart(3);
        System.out.println("Item added to the cart with quantity: " + itemCart.getQuantity());

        // Criando uma venda
        Sale sale = new Sale(1L, new Date(), BigDecimal.valueOf(150.00));
        System.out.println("Sale created with ID: " + sale.getId() + ", date: " + sale.getDate() + " and total: " + sale.getTotal());

        // Adicionando itens à venda
        ItemSale itemSale = new ItemSale(2, BigDecimal.valueOf(75.00));
        System.out.println("Sales item created with quantity: " + itemSale.getQuantity() + " and unit price: " + itemSale.getPriceunit());

        // Atualizando o total do carrinho
        cart.setTotal(BigDecimal.valueOf(150.00));
        System.out.println("Updated total of the cart to: " + cart.getTotal());
    }
}