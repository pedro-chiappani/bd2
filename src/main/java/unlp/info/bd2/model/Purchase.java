package unlp.info.bd2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;
    @Column(name = "code", unique = true, nullable = false, length = 15)
    private String code;
    @Column(name = "total_price", nullable = false)
    private float totalPrice;
    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private Review review;

    @OneToMany(cascade =
            {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            mappedBy = "purchase")
    private List<ItemService> itemServiceList;

    public Purchase() {}
    public Purchase(String code, Date date, User user, Route route) {
        this.code = code;
        this.date = date;
        this.route = route;
        this.user = user;
        this.totalPrice = route.getPrice();
        this.itemServiceList = new ArrayList<ItemService>();

    }
    public Purchase(String code, User user, Route route) {
        this.code = code;
        this.route = route;
        this.user = user;
        this.totalPrice = route.getPrice();
        this.itemServiceList = new ArrayList<ItemService>();

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addPrice(float price) {
        this.totalPrice += price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public List<ItemService> getItemServiceList() {
        return itemServiceList;
    }

    public void setItemServiceList(List<ItemService> itemServiceList) {
        this.itemServiceList = itemServiceList;
    }

    public void addItemService(ItemService itemService){
        this.itemServiceList.add(itemService);
        this.addPrice(itemService.getQuantity() * itemService.getService().getPrice());
    }
}
