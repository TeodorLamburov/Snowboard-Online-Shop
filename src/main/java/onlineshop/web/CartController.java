package onlineshop.web;

import onlineshop.model.binding.CartItemBindingModel;
import onlineshop.model.dtos.OrderItemServiceModel;
import onlineshop.model.dtos.OrderServiceModel;
import onlineshop.model.view.BoardViewModel;
import onlineshop.model.view.CartItemViewModel;
import onlineshop.model.view.ProductViewModel;
import onlineshop.service.OrderService;
import onlineshop.service.ProductService;
import onlineshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {


    private final ModelMapper modelMapper;
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartController(ModelMapper modelMapper, OrderService orderService, UserService userService, ProductService productService) {
        this.modelMapper = modelMapper;
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView cartDetails(ModelAndView modelAndView,
                                    HttpSession session) {
        List<CartItemViewModel> cart = this.retrieveCart(session);
        modelAndView.addObject("totalPrice", this.calcTotal(cart));

        modelAndView.setViewName("cart-details");

        return modelAndView;
    }


    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCartConfirm(@Valid @ModelAttribute("cartItemBindingModel") CartItemBindingModel cartItemBindingModel,
                                         String id,
                                         HttpSession session,
                                         BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("single");
        }
        BoardViewModel board = this.modelMapper.map(this.productService.findById(id), BoardViewModel.class);

        CartItemViewModel cartItem = this.modelMapper.map(cartItemBindingModel, CartItemViewModel.class);
        cartItem.setProduct(board);

        List<CartItemViewModel> cart = this.retrieveCart(session);
        this.addItemToCart(cartItem, cart, cartItemBindingModel.getBoardSize());

        modelAndView.setViewName("redirect:/cart/details");


        return modelAndView;
    }

    @PostMapping("/checkout")
    public ModelAndView checkoutConfirm(HttpSession session, Principal principal, ModelAndView modelAndView) {
        List<CartItemViewModel> cart = this.retrieveCart(session);

        OrderServiceModel orderServiceModel = this.prepareOrder(cart, principal.getName());
        this.orderService.createOrder(orderServiceModel);

        modelAndView.setViewName("redirect:/home");

        return modelAndView;
    }

    @PostMapping("/remove")
    public String removeFromCartConfirm(String id, HttpSession session) {
        this.removeItemFromCart(id, this.retrieveCart(session));

        return "redirect:/cart/details";
    }

    private void removeItemFromCart(String id, List<CartItemViewModel> cart) {
        cart.removeIf(ci -> ci.getProduct().getId().equals(id));
    }

    private void addItemToCart(CartItemViewModel item, List<CartItemViewModel> cart, String boardSize) {
        for (CartItemViewModel shoppingCartItem : cart) {
            if (shoppingCartItem.getProduct().getId().equals(item.getProduct().getId())
                    && shoppingCartItem.getBoardSize().equals(boardSize)) {
                shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        cart.add(item);
    }

    @SuppressWarnings(value = "unchecked")
    private List<CartItemViewModel> retrieveCart(HttpSession session) {
        this.initCart(session);
        return (List<CartItemViewModel>) session.getAttribute("shopping-cart");
    }

    private void initCart(HttpSession session) {
        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new ArrayList<>());
        }
    }

    private double calcTotal(List<CartItemViewModel> cart) {
        double result = 0;
        for (CartItemViewModel item : cart) {
            double price = item.getProduct().getProductPrice();

            result = price * item.getQuantity();
        }

        return result;
    }

    private OrderServiceModel prepareOrder(List<CartItemViewModel> cart, String username) {
        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setUser(this.userService.findByUsername(username));

        List<OrderItemServiceModel> boards = new ArrayList<>();
        for (CartItemViewModel item : cart) {
            OrderItemServiceModel orderItemServiceModel = this.modelMapper.map(item, OrderItemServiceModel.class);
            orderItemServiceModel.setPrice(item.getProduct().getProductPrice());


            boards.add(orderItemServiceModel);

        }

        orderServiceModel.setBoards(boards);
        orderServiceModel.setTotalPrice(this.calcTotal(cart));

        return orderServiceModel;
    }

}
