package onlineshop.model.view;

import java.io.Serializable;

public class CartItemViewModel implements Serializable {

    private BoardViewModel product;
    private int quantity;
    private String boardSize;

    public CartItemViewModel() {
    }

    public BoardViewModel getProduct() {
        return product;
    }

    public void setProduct(BoardViewModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(String boardSize) {
        this.boardSize = boardSize;
    }
}
