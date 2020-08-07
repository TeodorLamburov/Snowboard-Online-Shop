package onlineshop.web;

import onlineshop.model.binding.UserEditBindingModel;
import onlineshop.model.binding.UserLoginBindingModel;
import onlineshop.model.binding.UserRegisterBinding;
import onlineshop.model.dtos.RoleServiceModel;
import onlineshop.model.dtos.UserServiceModel;
import onlineshop.model.view.UserAllViewModel;
import onlineshop.model.view.UserProfileViewModel;
import onlineshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBinding")) {
            model.addAttribute("userRegisterBinding", new UserRegisterBinding());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBinding")
                                          UserRegisterBinding userRegisterBinding, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        System.out.println();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBinding", userRegisterBinding);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegister", bindingResult);
            return "redirect:/users/register";
        }


        if (!userRegisterBinding.getPassword().equals(userRegisterBinding.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBinding", userRegisterBinding);
            redirectAttributes.addFlashAttribute("wrongPassword", true);
            return "redirect:/users/register";
        }

        UserServiceModel userServiceModel = this.userService.findByUsername(userRegisterBinding.getUsername());

        if (userServiceModel != null) {
            redirectAttributes.addFlashAttribute("userRegisterBinding", userRegisterBinding);
            redirectAttributes.addFlashAttribute("userExists", true);
            return "redirect:/users/register";
        }

        this.userService.seedUser(this.modelMapper.map(userRegisterBinding, UserServiceModel.class));

        return "redirect:/login";
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String login(Model model) {
        if (!model.containsAttribute("userLoginBindingBindingModel")) {
            model.addAttribute("userLoginBindingBindingModel", new UserLoginBindingModel());
        }
        return "login";
    }

    @GetMapping("/profile")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {

        UserServiceModel user = this.userService.findByUsername(principal.getName());
        UserProfileViewModel model = this.modelMapper.map(user, UserProfileViewModel.class);
        modelAndView.addObject("model", model);
        modelAndView.setViewName("profile");

        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
        UserServiceModel user = this.userService.findByUsername(principal.getName());
        UserEditBindingModel model = this.modelMapper.map(user, UserEditBindingModel.class);

        modelAndView.addObject("model", model);
        modelAndView.setViewName("edit-profile");

        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editProfileConfirm(@Valid @ModelAttribute("userEditBindingModel") UserEditBindingModel userEditBindingModel,
                                           BindingResult bindingResult, ModelAndView modelAndView) {
        if (!userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userEditBindingModel", "password", "Password dont match!"));
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("edit-profile");
        }

        UserServiceModel userServiceModel = this.modelMapper.map(userEditBindingModel, UserServiceModel.class);
        this.userService.editUserProfile(userServiceModel, userEditBindingModel.getOldPassword());
        modelAndView.setViewName("redirect:/users/profile");

        return modelAndView;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        List<UserAllViewModel> users = userService.findAllUsers()
                .stream()
                .map(u -> {
                    UserAllViewModel user = this.modelMapper.map(u, UserAllViewModel.class);
                    user.setAuthorities(u.getAuthorities().stream().map(a -> a.getAuthority())
                            .collect(Collectors.toSet()));
                    return user;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("users", users);
        modelAndView.setViewName("all-users");
        return modelAndView;
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id,ModelAndView modelAndView) {
        this.userService.setUserRole(id, "user");

        modelAndView.setViewName("redirect:/users/all");
        return modelAndView;
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id,ModelAndView modelAndView) {
        this.userService.setUserRole(id, "moderator");

        modelAndView.setViewName("redirect:/users/all");
        return modelAndView;
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id,ModelAndView modelAndView) {
        this.userService.setUserRole(id, "admin");

        modelAndView.setViewName("redirect:/users/all");
        return modelAndView;
    }


    private Set<String> getAuthoritiesToString(UserServiceModel userServiceModel) {
        return userServiceModel.getAuthorities()
                .stream()
                .map(RoleServiceModel::getAuthority)
                .collect(Collectors.toSet());
    }

}
