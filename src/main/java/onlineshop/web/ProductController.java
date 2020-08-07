package onlineshop.web;

import onlineshop.model.binding.CartItemBindingModel;
import onlineshop.model.binding.ProductAddBinding;
import onlineshop.model.binding.ProductEditBindingModel;
import onlineshop.model.dtos.ProductServiceModel;
import onlineshop.model.view.ProductViewModel;
import onlineshop.service.ProductService;
import onlineshop.utils.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public String add(Model model) {

        if (model.getAttribute("productAdd") == null) {
            model.addAttribute("productAdd", new ProductAddBinding());
        }
        return "product-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("productAdd")
                                     ProductAddBinding productAddBinding, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws IOException {

        System.out.println();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAdd", productAddBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAdd", bindingResult);
            return "redirect:/products/add";
        }


        if (this.productService.productExist(productAddBinding.getProductName())) {
            redirectAttributes.addFlashAttribute("productAdd", productAddBinding);
            redirectAttributes.addFlashAttribute("productExists", true);
            return "redirect:/products/add";
        }
        ProductServiceModel productServiceModel = this.modelMapper.map(productAddBinding, ProductServiceModel.class);
        productServiceModel.setPictureUrl(
                this.cloudinaryService.uploadImage(productAddBinding.getPictureUrl()));


        this.productService.addProduct(productServiceModel);
        return "redirect:/products/all";
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView details(@PathVariable String id,
                                ModelAndView modelAndView,
                                @ModelAttribute("cartModel") CartItemBindingModel cartModel) {
        ProductViewModel productService = this.productService.findById(id);
        ProductServiceModel product = this.modelMapper.map(productService,ProductServiceModel.class);

        modelAndView.addObject("product", product);
        modelAndView.setViewName("single");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllProducts(ModelAndView modelAndView) {
        List<ProductViewModel> products = this.productService.findAllProducts()
                .stream()
                .map(board -> this.modelMapper.map(board, ProductViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("allBoards", products);
        modelAndView.setViewName("shop");

        return modelAndView;
    }


    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allProductsInModeratorMenu(ModelAndView modelAndView) {
        List<ProductViewModel> boards = this.productService.findAllProducts()
                .stream()
                .map(board -> this.modelMapper.map(board, ProductViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("boards", boards);
        modelAndView.setViewName("all-products");

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductViewModel productViewModel = this.productService.findById(id);

        ProductEditBindingModel editModel = this.modelMapper.map(productViewModel, ProductEditBindingModel.class);

        modelAndView.addObject("editModel", editModel);
        modelAndView.setViewName("edit-product");

        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editConfirm(@PathVariable String id,
                                    @Valid @ModelAttribute(name = "editModel") ProductEditBindingModel editModel,
                                    BindingResult bindingResult,
                                    ModelAndView modelAndView){

        ProductServiceModel serviceModel = this.modelMapper.map(editModel, ProductServiceModel.class);
        this.productService.editById(id, serviceModel);
        modelAndView.setViewName("redirect:/products/all");
        return modelAndView;
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView delete(@RequestParam("id") String id, ModelAndView modelAndView) {

        this.productService.deleteProductById(id);
        modelAndView.setViewName("redirect:/products/");
        return modelAndView;
    }


    //метод купуване на продукт
    //От листнатите продукти се взема ид-то на дадения продукт, чрез натискане на бутона купуване
    //След което се намира логнатията потребител от спринг секюрити (принципал)
    //След като се намери потребителя тно продукта, даденият продукт се добавя към списъка с продукти на потребителя
}
