package onlineshop.model.binding;

import javax.validation.constraints.NotNull;

public class BoardSizeBindingModel extends BaseBinding{

    private String size;

    public BoardSizeBindingModel() {
    }

    @NotNull(message = "Size cannot be null")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
