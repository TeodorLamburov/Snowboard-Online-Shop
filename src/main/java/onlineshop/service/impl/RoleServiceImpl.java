package onlineshop.service.impl;

import onlineshop.model.dtos.RoleServiceModel;
import onlineshop.model.entities.RoleEntity;
import onlineshop.repository.RoleRepository;
import onlineshop.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @PostConstruct
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            RoleEntity admin = new RoleEntity("ROLE_ADMIN");
            RoleEntity root = new RoleEntity("ROLE_ROOT");
            RoleEntity moderator = new RoleEntity("ROLE_MODERATOR");
            RoleEntity user = new RoleEntity("ROLE_USER");


            this.roleRepository.saveAndFlush(admin);
            this.roleRepository.saveAndFlush(moderator);
            this.roleRepository.saveAndFlush(root);
            this.roleRepository.saveAndFlush(user);
        }

    }


    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String roleName) {
        RoleEntity role = this.roleRepository.findByAuthority(roleName).orElse(null);

        return this.modelMapper.map(role,RoleServiceModel.class);
    }
}
