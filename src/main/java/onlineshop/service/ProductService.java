package onlineshop.service;

import onlineshop.model.dtos.ProductServiceModel;
import onlineshop.model.view.ProductViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    boolean productExist(String productName);

    void addProduct(ProductServiceModel productServiceModel);


    List<ProductServiceModel> findAllProducts();

    ProductViewModel findById(String id);

    void deleteProductById(String id);

    void editById(String id, ProductServiceModel serviceModel);

}
