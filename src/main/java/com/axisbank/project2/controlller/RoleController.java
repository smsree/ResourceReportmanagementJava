package com.axisbank.project2.controlller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.axisbank.project2.model.Role;
import com.axisbank.project2.repository.RoleRepository;

@RestController
public class RoleController {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public RoleRepository getRoleRepository() {
		return roleRepository;
	}
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public RoleController(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}
	public RoleController() {
		
	}
	@PostMapping("/role/add")
	public String addRole(@RequestBody Role role)
	{
		roleRepository.save(role);
		return "role added successfully";
	}
	@GetMapping("/role/list")
	public List<Role> allRole() 
	{
		return roleRepository.findAll();
	}

}