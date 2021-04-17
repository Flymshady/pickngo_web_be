package cz.uhk.fim.bs.pickngo_web_be.Customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Customer {
    @Id
    @SequenceGenerator(name="user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String name;
    private String email;
    private String email_short;
    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<BaguetteOrder> baguetteOrders;


    public Customer() {
    }



    public Customer(Long id, String name, String email, String email_short) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.email_short = email_short;
    }

    public Customer(String name, String email, String email_short) {
        this.name = name;
        this.email = email;
        this.email_short =email_short;
    }

    public Customer(String name, String email, String email_short, List<BaguetteOrder> baguetteOrders) {
        this.name = name;
        this.email = email;
        this.email_short = email_short;
        this.baguetteOrders = baguetteOrders;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_short() {
        return email_short;
    }

    public void setEmail_short(String email_short) {
        this.email_short = email_short;
    }

    public List<BaguetteOrder> getBaguetteOrders() {
        return baguetteOrders;
    }

    public void setBaguetteOrders(List<BaguetteOrder> baguetteOrders) {
        this.baguetteOrders = baguetteOrders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", email_short='" + email_short + '\'' +
                ", baguetteOrders=" + baguetteOrders +
                '}';
    }
}
