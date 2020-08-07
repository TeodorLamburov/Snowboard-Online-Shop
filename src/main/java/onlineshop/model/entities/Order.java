package onlineshop.model.entities;

import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private List<OrderItem> boards;
    private UserEntity userEntity;
    private Address address;
    private Double totalPrice;
    private LocalDateTime finishedOn;

    public Order() {
    }


    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @OneToOne
    @JoinColumn(name = "address_id")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    @ManyToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL)
    @JoinTable(name = "orders_boards",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "board_id", referencedColumnName = "id"))
    public List<OrderItem> getBoards() {
        return boards;
    }

    public void setBoards(List<OrderItem> boards) {
        this.boards = boards;
    }

    @Column(name = "total_price")
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Column(name = "finished_on")
    public LocalDateTime getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(LocalDateTime finishedOn) {
        this.finishedOn = finishedOn;
    }
}


