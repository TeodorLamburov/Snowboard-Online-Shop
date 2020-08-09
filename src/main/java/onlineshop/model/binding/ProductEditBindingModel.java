package onlineshop.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductEditBindingModel extends BaseBinding{

    private String productName;
    private String productBrand;
    private String productCategory;
    private String productModel;
    private double productPrice;
    private String description;
    private List<String> boardSize;


    public ProductEditBindingModel() {
    }

    @Length(min = 3, max = 20, message = "Name length must be between 3 and 20 characters")
    @NotNull(message = "Name cannot be null.")
    @NotEmpty(message = "Name cannot be empty")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Length(min = 2, max = 30, message = "Brand length must be between 2 and 30 characters")
    @NotNull(message = "Brand cannot be null.")
    @NotEmpty(message = "Brand cannot be empty")
    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    @Length(min = 2, max = 20, message = "Category length must be between 2 and 20 characters")
    @NotNull(message = "Category cannot be null.")
    @NotEmpty(message = "Category cannot be empty")
    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    @Length(min = 2, max = 20, message = "Model length must be between 2 and 20 characters")
    @NotNull(message = "Model cannot be null.")
    @NotEmpty(message = "Model cannot be empty")
    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    @DecimalMin(value = "1", message = "Price can not be negative number!")
    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Length(min = 5, message = "Description length must be at least 5 characters")
    @NotNull(message = "Description cannot be null.")
    @NotEmpty(message = "Description cannot be empty")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty(message = "Board size cannot be empty")
    public List<String> getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(List<String> boardSize) {
        this.boardSize = boardSize;
    }
}
