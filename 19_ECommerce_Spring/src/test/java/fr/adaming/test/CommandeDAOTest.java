package fr.adaming.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.adaming.dao.ICommandeDao;
import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/test/resources/application-context.xml")
public class CommandeDAOTest {
	
	@Autowired
	private ICommandeDao commandeDAO;
	@Ignore
	@Test
	public void testEnregistrementCommande(){
		Commande inCommande = new Commande();
		
	}

}
