package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IRoleDao;
import fr.adaming.modele.Administrateur;
import fr.adaming.modele.Role;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private IRoleDao roleDao;
	
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	

	@Override
	public Role getRoleByAdmin(Administrateur admin) {
		return roleDao.getRoleByAdmin(admin);
	}
}
