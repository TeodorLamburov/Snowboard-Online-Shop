package onlineshop.service;

import onlineshop.model.dtos.RoleServiceModel;
import onlineshop.model.entities.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoleService {

    void seedRolesInDb();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String roleName);
}
