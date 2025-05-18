package Person;

import Product.Products;

public class Employee {
    private Long id;
    private String name;
    private String password;
    private String email;
    private String cargo;
    private Products gerencia;

    public Employee(Long id, String name, String password, String mail, String cargo) {
        this.id = id;
        setName(name);
        setPassword(password);
        setEmail(mail);
        setCargo(cargo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        //Validação se nome não é vazio ou incompleto
        if (name != null && name.trim().contains(" ")) {
            this.name = name.trim();
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //Validação se senha não é vazia e se tem no mínimo 8 caracteres
        if (password != null && password.length() >= 8) {
            this.password = password.trim();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        //Validação se o email está no formato correto
        if (email != null && email.contains("@")){
            this.email = email.trim();
        }
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        if (cargo != null) {
            this.cargo = cargo.trim();
        }
    }

    public Products getManager() {
        return gerencia;
    }

    public void setManager(Products gerencia) {
        this.gerencia = gerencia;
    }
}
