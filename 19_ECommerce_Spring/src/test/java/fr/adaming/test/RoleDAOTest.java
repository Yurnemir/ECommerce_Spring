package fr.adaming.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IRoleDao;
import fr.adaming.modele.Administrateur;
import fr.adaming.modele.Role;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/test/resources/application-context.xml")
public class RoleDAOTest {

	@Autowired
	private IRoleDao roleDao;
	
	private Administrateur admin;
	private Role role;
	
	@Before
	public void init(){
		admin = new Administrateur(1, "a@a", "a");
		role = new Role(3,"ROLE_ADMIN_CATEGORIE");
		role.setAdministrateur(admin);
	}
	
	@Test
	@Transactional
	@Ignore

	public void testGetRoleByAdmin(){
		//on recupere un role d'un admin connu
		Role roleGet = roleDao.getRoleByAdmin(admin);
		
		//on vérifie que le role obtenu correspond, et l'id de l'admin associé
		assertEquals(role.getNomRole(), roleGet.getNomRole());
		assertEquals(role.getAdministrateur().getIdAdmin(),roleGet.getAdministrateur().getIdAdmin());
		
		//on verifie qu'un administrateur erronné ou inexistant renvoie null
		roleGet = roleDao.getRoleByAdmin(new Administrateur());
		assertNull(roleGet);
	}
	
}
