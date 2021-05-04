package cz.uhk.fim.bs.pickngo_web_be.BaguetteItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrder;
import cz.uhk.fim.bs.pickngo_web_be.Item.Item;
import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class BaguetteItem {
    @Id
    @SequenceGenerator(name="baguette_item_sequence", sequenceName = "baguette_item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "baguette_item_sequence")
    private Long id;
    @JsonIgnore
    @ManyToOne
    private BaguetteOrder baguetteOrder;
    private double price;

    @OneToMany(mappedBy = "baguetteItem")
    private List<Item> items;
    private boolean offer;

    public BaguetteItem(BaguetteOrder baguetteOrder, double price, List<Item> items,boolean offer) {
        this.baguetteOrder = baguetteOrder;
        this.price = price;
        this.items = items;
        this.offer=offer;
    }

    public BaguetteItem(Long id, BaguetteOrder baguetteOrder, double price, List<Item> items,boolean offer) {
        this.id = id;
        this.baguetteOrder = baguetteOrder;
        this.price = price;
        this.items = items;
        this.offer=offer;
    }

    public BaguetteItem(BaguetteOrder baguetteOrder, double price,boolean offer) {
        this.baguetteOrder = baguetteOrder;
        this.price = price;
        this.offer=offer;
    }

    public BaguetteItem(Long id, BaguetteOrder baguetteOrder, double price, boolean offer) {
        this.id = id;
        this.baguetteOrder = baguetteOrder;
        this.price = price;
        this.offer = offer;
    }

    public BaguetteItem(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public BaguetteOrder getBaguetteOrder() {
        return baguetteOrder;
    }

    public void setBaguetteOrder(BaguetteOrder baguetteOrder) {
        this.baguetteOrder = baguetteOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "BaguetteItem{" +
                "id=" + id +
                ", baguetteOrder=" + baguetteOrder +
                ", price=" + price +
                ", items=" + items +
                ", offer=" + offer +
                '}';
    }
}
