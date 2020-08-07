package onlineshop.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ProductAddBinding extends BaseBinding {

    private String productName;
    private String productBrand;
    private String productCategory;
    private String productModel;
    private double productPrice;
    private String description;
    private List<String> boardSize;
    private MultipartFile pictureUrl;

    public ProductAddBinding() {
    }

    @Length(min = 3, max = 20, message = "Name length must be between 3 and 20 characters")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Length(min = 2, max = 30, message = "Brand length must be between 2 and 30 characters")
    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    @Length(min = 3, max = 20, message = "Category length must be between 3 and 20 characters")
    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    @Length(min = 2, max = 20, message = "Model length must be between 2 and 20 characters")
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<String> getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(List<String> boardSize) {
        this.boardSize = boardSize;
    }

    public MultipartFile getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(MultipartFile pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
