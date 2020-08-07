package onlineshop.model.binding;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

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
}
