package onlineshop.model.dtos;

import onlineshop.model.entities.BoardSize;
import onlineshop.model.entities.Product;

import java.util.Set;

public class OrderItemServiceModel extends BaseServiceModel{

    private ProductServiceModel product;
    private Double price;
    private Integer quantity;
    private String boardSize;

    public OrderItemServiceModel() {
    }

    public ProductServiceModel getProduct() {
        return product;
    }

    public void setProduct(ProductServiceModel product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(String boardSize) {
        this.boardSize = boardSize;
    }
}
