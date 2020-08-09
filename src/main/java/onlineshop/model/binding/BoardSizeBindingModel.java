package onlineshop.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BoardSizeBindingModel extends BaseBinding{

    private String size;

    public BoardSizeBindingModel() {
    }

    @NotNull(message = "Size cannot be null!")
    @NotEmpty(message = "Board size cannot be empty!")
    @Length(max = 2, message = "Invalid board size length!")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
