package org.erp.model.user;

import lombok.RequiredArgsConstructor;
import org.erp.repository.PrivilegeRepository;
import org.erp.repository.RoleRepository;
import org.erp.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    private boolean alreadySetup = false;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (alreadySetup) return;

        PrivilegeEntity readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeEntity writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<PrivilegeEntity> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN");

        userRepository.save(UserEntity.builder()
        .email("testEmail")
        .name("testName")
        .isEnabled(true)
        .password("testPwd")
        .roleEntities(Arrays.asList(adminRole))
        .build());

        alreadySetup = true;
    }

    @Transactional
    public PrivilegeEntity createPrivilegeIfNotFound(String name) {

        PrivilegeEntity privilege = privilegeRepository.findByName(name);

        if (privilege == null) {
            privilege = new PrivilegeEntity(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public RoleEntity createRoleIfNotFound(String name, Collection<PrivilegeEntity> privileges) {

        RoleEntity role = roleRepository.findByName(name);

        if (role == null) {
            role = new RoleEntity(name);
            role.setPrivilegeEntities(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
