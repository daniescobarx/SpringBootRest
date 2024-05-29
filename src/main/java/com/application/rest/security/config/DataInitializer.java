package com.application.rest.security.config;

import com.application.rest.security.persistence.entity.PermissionEntity;
import com.application.rest.security.persistence.entity.RoleEntity;
import com.application.rest.security.persistence.entity.RoleEnum;
import com.application.rest.security.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            //permissions
            PermissionEntity createPemission = PermissionEntity.builder()
                    .name("CREATE")
                    .build();

            PermissionEntity readPermission = PermissionEntity.builder()
                    .name("READ")
                    .build();

            PermissionEntity updatePermission = PermissionEntity.builder()
                    .name("UPDATE")
                    .build();

            PermissionEntity deletePermission = PermissionEntity.builder()
                    .name("DELETE")
                    .build();

            //roles
            RoleEntity roleAdmin = RoleEntity.builder()
                    .roleEnum(RoleEnum.ADMIN)
                    .permissionList(Set.of(createPemission, readPermission, updatePermission, deletePermission))
                    .build();

            RoleEntity roleMaker = RoleEntity.builder()
                    .roleEnum(RoleEnum.MAKER)
                    .permissionList(Set.of(createPemission))
                    .build();

            RoleEntity roleCustomer = RoleEntity.builder()
                    .roleEnum(RoleEnum.CUSTOMER)
                    .permissionList(Set.of(createPemission))
                    .build();

        };
    }
}
