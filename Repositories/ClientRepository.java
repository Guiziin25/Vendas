package Repositories;

import Person.Client;

public class ClientRepository {
    private Client[] clients = new Client[100];
    private int count = 0;

    public void add(Client client) {
        if (count < clients.length) {
            clients[count++] = client;
        }
    }

    public Client getById(Long id) {
        for (int i = 0; i < count; i++) {
            if (clients[i].getId().equals(id)) {
                return clients[i];
            }
        }
        return null;
    }

    public int size() {
        return count;
    }
}