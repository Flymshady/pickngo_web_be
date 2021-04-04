package cz.uhk.fim.bs.pickngo_web_be.Order;

import cz.uhk.fim.bs.pickngo_web_be.Item.Item;
import cz.uhk.fim.bs.pickngo_web_be.User.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Order {

    @Id
    @SequenceGenerator(name="order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    private Long id;
    private User user;
    private double price;
    private Date date;
    private int state;
    private List<Item> items;

    public Order(User user, double price, Date date, int state, List<Item> items) {
        this.user = user;
        this.price = price;
        this.date = date;
        this.state = state;
        this.items = items;
    }

    public Order(User user, double price, Date date, int state) {
        this.user = user;
        this.price = price;
        this.date = date;
        this.state = state;
    }

    public Order(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", price=" + price +
                ", date=" + date +
                ", state=" + state +
                ", items=" + items +
                '}';
    }
}
