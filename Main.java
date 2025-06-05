package Sales;

import Person.Client;
import Person.Employee;
import Repositories.ClientRepository;
import Repositories.EmployeeRepository;

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

        // Teste do EmployeeRepository
        EmployeeRepository employeeRepo = new EmployeeRepository();
        Employee emp1 = new Employee(1L, "João Silva", "password123", "joao@gmail.com", "Vendedor");
        Employee emp2 = new Employee(2L, "Maria da Graça", "password456", "maria@gmail.com", "Gerente");
        employeeRepo.add(emp1);
        employeeRepo.add(emp2);

        System.out.println("Total number of employees: " + employeeRepo.size());
        Employee searchEmployee = employeeRepo.getById(1L);
        if (searchEmployee != null) {
            System.out.println("Employee found: " + searchEmployee.getName());
        } else {
            System.out.println("Employee not found.");
        }

        // Teste do ClientRepository
        ClientRepository clientRepo = new ClientRepository();
        Client cli1 = new Client(1L, "123", "Guilherme Augusto","gui@gmail.com", "123456789", "Rua A, 123");
        clientRepo.add(cli1);

        System.out.println("Total number of customers: " + clientRepo.size());
        Client searchClient = clientRepo.getById(1L);
        if (searchClient != null) {
            System.out.println("Client found: " + searchClient.getName());
        } else {
            System.out.println("Client not found.");
        }
    }
}