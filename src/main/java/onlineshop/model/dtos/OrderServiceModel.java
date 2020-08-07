package onlineshop.model.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel extends BaseServiceModel{

    private List<OrderItemServiceModel> boards;
    private UserServiceModel user;
    private Double totalPrice;
    private LocalDateTime finishedOn;

    public OrderServiceModel() {
    }


    public List<OrderItemServiceModel> getBoards() {
        return boards;
    }

    public void setBoards(List<OrderItemServiceModel> boards) {
        this.boards = boards;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(LocalDateTime finishedOn) {
        this.finishedOn = finishedOn;
    }
}
