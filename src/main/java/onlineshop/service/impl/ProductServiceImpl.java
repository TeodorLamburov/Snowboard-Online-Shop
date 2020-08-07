package onlineshop.service.impl;

import onlineshop.error.ProductNotFoundException;
import onlineshop.model.dtos.ProductServiceModel;
import onlineshop.model.entities.BoardSize;
import onlineshop.model.entities.Product;
import onlineshop.model.view.ProductViewModel;
import onlineshop.repository.BoardSizeRepository;
import onlineshop.repository.ProductRepository;
import onlineshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final BoardSizeRepository boardSizeRepository;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, BoardSizeRepository boardSizeRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.boardSizeRepository = boardSizeRepository;
    }


    @Override
    public void addProduct(ProductServiceModel productServiceModel) {
        productExist(productServiceModel.getProductName());

        Product product = this.modelMapper.map(productServiceModel, Product.class);
        Set<BoardSize> sizes =
                new HashSet<>(this.boardSizeRepository.findAllById(productServiceModel.getBoardSize()));

        product.setBoardSize(sizes);

        this.productRepository.save(product);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository
                .findAll()
                .stream()
                .map(this::getProductServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public ProductViewModel findById(String id) {

        return this.productRepository.findById(id)
                .map(product -> {

                    return this.modelMapper
                            .map(product, ProductViewModel.class);
                })
                .orElse(null);
    }

    @Override
    public void deleteProductById(String id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public void editById(String id, ProductServiceModel productServiceModel) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Incorrect id!"));

        String editBoardName = productServiceModel.getProductName();
        String editBoardBrand = productServiceModel.getProductBrand();
        String editBoardModel = productServiceModel.getProductModel();

        if (!product.getProductName().equals(editBoardName)
                || !product.getProductBrand().equals(editBoardBrand)
                || !product.getProductModel().equals(editBoardModel)) {
            this.checkIfBoardAlreadyExist(editBoardName, editBoardBrand, editBoardModel);
        }

        product.setProductName(editBoardName);
        product.setProductBrand(editBoardBrand);
        product.setProductCategory(productServiceModel.getProductCategory());
        product.setProductModel(editBoardModel);
        product.setProductPrice(productServiceModel.getProductPrice());
        product.setDescription(productServiceModel.getDescription());


        Set<BoardSize> sizes = this.boardSizeRepository.findAll()
                .stream()
                .filter(c -> productServiceModel.getBoardSize().contains(c.getSize()))
                .collect(Collectors.toSet());
        product.setBoardSize(sizes);

        this.productRepository.save(product);
    }

    private ProductServiceModel getProductServiceModel(Product product) {
        ProductServiceModel serviceModel = this.modelMapper.map(product, ProductServiceModel.class);

        serviceModel.setBoardSize(this.getSizes(product));

        return serviceModel;
    }

    @Override
    public boolean productExist(String productName) {
        if (this.productRepository.findByProductName(productName).orElse(null) == null) {
            return false;
        }
        return true;
    }

    private Set<String> getSizes(Product product) {
        return product.getBoardSize()
                .stream()
                .map(BoardSize::getSize)
                .collect(Collectors.toSet());
    }

    private void checkIfBoardAlreadyExist(String name, String brand, String model) {
        Product product = this.productRepository.findByProductNameAndProductBrandAndProductModel(name, brand, model)
                .orElse(null);

        if (product != null) {
            throw new ProductNotFoundException("Product already exist!");
        }
    }
}

