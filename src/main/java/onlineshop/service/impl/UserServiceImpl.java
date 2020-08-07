package onlineshop.service.impl;

import onlineshop.error.EmailAlreadyExistException;
import onlineshop.error.PasswordDontMatchException;
import onlineshop.model.dtos.UserServiceModel;
import onlineshop.model.entities.Address;
import onlineshop.model.entities.RoleEntity;
import onlineshop.model.entities.UserEntity;
import onlineshop.repository.AddressRepository;
import onlineshop.repository.RoleRepository;
import onlineshop.repository.UserRepository;
import onlineshop.service.RoleService;
import onlineshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("No such user " + username));
    }

    @Override
    public UserServiceModel findByUsername(String username) {

        return this.userRepository.findByUsername(username)
                .map(userEntity -> this.modelMapper.map(userEntity, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public void seedUser(UserServiceModel userServiceModel) {
        UserEntity user = this.modelMapper.map(userServiceModel, UserEntity.class);
        Address address = new Address(
                userServiceModel.getCountry(),
                userServiceModel.getCity(),
                userServiceModel.getStreet()
        );
        this.addressRepository.saveAndFlush(address);
        user.setAddress(address);
        if (this.userRepository.count() == 0) {
            // this.roleService.seedRolesInDb();
            user.setAuthorities(this.roleService.findAllRoles()
                    .stream()
                    .map(r -> this.modelMapper.map(r, RoleEntity.class))
                    .collect(Collectors.toSet()));
        } else {


            user.setAuthorities(new LinkedHashSet());
            RoleEntity roleEntity = this.modelMapper
                    .map(this.roleService.findByAuthority("ROLE_USER"), RoleEntity.class);
            user.getAuthorities().add(roleEntity);

        }
        user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));
        this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        UserEntity userEntity = (UserEntity) this.loadUserByUsername(userServiceModel.getUsername());

        this.checkIfPasswordMatch(oldPassword, userEntity);
        this.checkIfEmailAlreadyExist(userEntity.getEmail(), userServiceModel.getEmail());

        userEntity.setPassword(userServiceModel.getPassword().isEmpty() ?
                userEntity.getPassword() :
                passwordEncoder.encode(userServiceModel.getPassword()));
        userEntity.setEmail(userServiceModel.getEmail());
        userEntity.setName(userServiceModel.getName());
        UserEntity savedUser = this.userRepository.save(userEntity);

        return this.modelMapper.map(savedUser, UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void setUserRole(String id, String role) {
        UserEntity user =
                this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Incorrect id"));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        switch (role) {
            case "user":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                break;
            case "moderator":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
                break;
            case "admin":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_ADMIN"));
                break;
        }

        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, UserEntity.class));
    }


    private void checkIfEmailAlreadyExist(String oldEmail, String newEmail) {
        if (!oldEmail.equals(newEmail)) {
            UserEntity userWithSameEmail = this.userRepository.findByEmail(newEmail).orElse(null);

            if (userWithSameEmail != null) {
                throw new EmailAlreadyExistException("Email already exist!");
            }
        }
    }

    private void checkIfPasswordMatch(String oldPassword, UserEntity userEntity) {
        if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            throw new PasswordDontMatchException("Old password dont match current password!");
        }
    }


}
