package onlineshop.service.impl;

import onlineshop.error.BoardSizeNotFoundException;
import onlineshop.model.dtos.OrderItemServiceModel;
import onlineshop.model.dtos.OrderServiceModel;
import onlineshop.model.dtos.UserServiceModel;
import onlineshop.model.entities.Order;
import onlineshop.model.entities.OrderItem;
import onlineshop.model.entities.Product;
import onlineshop.repository.BoardSizeRepository;
import onlineshop.repository.OrderItemRepository;
import onlineshop.repository.OrderRepository;
import onlineshop.repository.ProductRepository;
import onlineshop.service.OrderService;
import onlineshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService{

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final BoardSizeRepository boardSizeRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final ProductRepository productRepository;


    public OrderServiceImpl(ModelMapper modelMapper, OrderRepository orderRepository, BoardSizeRepository boardSizeRepository, OrderItemRepository orderItemRepository, UserService userService, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.boardSizeRepository = boardSizeRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @Override
    public void createOrder(OrderServiceModel orderServiceModel) {
        orderServiceModel.setFinishedOn(LocalDateTime.now());

        Order order = this.modelMapper.map(orderServiceModel,Order.class);

        order.getBoards()
                .forEach(b -> {
                    this.convertBoardSize(orderServiceModel, b);
                    orderItemRepository.save(b);
                });

        this.orderRepository.save(order);
    }

    private void convertBoardSize(OrderServiceModel model, OrderItem result) {
        for (OrderItemServiceModel board : model.getBoards()) {
            if (result.getProduct().getId().equals(board.getProduct().getId())) {
                result.setBoardSize(this.boardSizeRepository.findBySize(board.getBoardSize())
                        .orElseThrow(() -> new BoardSizeNotFoundException("Incorrect board size!")));
                model.getBoards().remove(board);
                break;
            }
        }
    }

}
