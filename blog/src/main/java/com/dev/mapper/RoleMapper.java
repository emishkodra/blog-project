package com.dev.mapper;

import com.dev.dto.RoleDTO;
import com.dev.model.Role;
import com.dev.util.EntityMapper;

public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

    RoleDTO toDto(Role role);

    Role toEntity(RoleDTO roleDTO);

}