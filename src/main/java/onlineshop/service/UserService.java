package onlineshop.service;

import onlineshop.model.dtos.UserServiceModel;
import onlineshop.model.view.UserAllViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    UserServiceModel findByUsername(String username);

    void seedUser(UserServiceModel userServiceModel);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel,String oldPassword);

    List<UserServiceModel> findAllUsers();

    void setUserRole(String id, String role);
}
