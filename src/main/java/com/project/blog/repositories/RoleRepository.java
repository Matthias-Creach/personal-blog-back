package com.project.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.models.parameters.EnumRole;
import com.project.blog.models.parameters.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(EnumRole name);
}
