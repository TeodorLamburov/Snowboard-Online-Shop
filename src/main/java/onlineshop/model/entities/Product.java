package onlineshop.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String productName;
    private String productBrand;
    private String productCategory;
    private String productModel;
    private double productPrice;
    private String description;
    private Set<BoardSize> boardSize;
    private String pictureUrl;


    public Product() {
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "board_sizes",
            joinColumns = @JoinColumn(
                    name = "board_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "size_id",
                    referencedColumnName = "id"
            )
    )
    public Set<BoardSize> getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(Set<BoardSize> boardSize) {
        this.boardSize = boardSize;
    }

    @Column(name = "category", nullable = false)
    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    @Column(name = "name", nullable = false)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "brand", nullable = false)
    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    @Column(name = "model", nullable = false)
    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }


    @Column(name = "price", nullable = false)
    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Column(name = "description",nullable = false,columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "picture_url")
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

}
