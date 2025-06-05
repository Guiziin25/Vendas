package Person;

public class Client {
    private Long id;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;

    public Client(Long id, String password, String name, String email, String phone, String address) {
        this.id = id;
        setName(name);
        setPassword(password);
        setEmail(email);
        setPhone(phone);
        setAddress(address);
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
        if (password != null && password.trim().length() >= 8) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        //Validação se telefone não é vazio ou diferente de 11 dígitos(ddd + número)
        if (phone != null && phone.replaceAll("\\s","").length() == 11) {
            this.phone = phone.replaceAll("\\s","");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        //Validação se endereço não é vazio
        if (address != null) {
            this.address = address.trim();
        }
    }
}

