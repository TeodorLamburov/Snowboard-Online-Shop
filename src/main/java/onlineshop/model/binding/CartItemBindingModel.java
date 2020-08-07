package onlineshop.model.binding;


public class CartItemBindingModel extends BaseBinding{

    private String boardSize;
    private int quantity;

    public CartItemBindingModel() {
    }

    public String getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(String boardSize) {
        this.boardSize = boardSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
