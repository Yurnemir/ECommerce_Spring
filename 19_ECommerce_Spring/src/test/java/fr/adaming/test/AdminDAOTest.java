package fr.adaming.test;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IAdminDao;
import fr.adaming.modele.Administrateur;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/test/resources/application-context.xml")
public class AdminDAOTest {
	
	@Autowired
	private IAdminDao adminDAO;
	
	@Test
	@Transactional
	@Rollback(true)
	@Ignore
	public void testConnexionAdmin(){
		Administrateur adminIn = new Administrateur("a@a","a");
		Administrateur adminOut = adminDAO.connexionAdmin(adminIn);
		
		assertEquals(adminIn.getIdentifiant(),adminOut.getIdentifiant());
		assertEquals(adminIn.getMdp(),adminOut.getMdp());
	}

}
