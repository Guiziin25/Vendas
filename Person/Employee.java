package Person;

public class Employee {
    private Long id;
    private String name;
    private String password;
    private String mail;
    private String cargo;

    public Employee(Long id, String name, String password, String mail, String cargo) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.cargo = cargo;
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
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
