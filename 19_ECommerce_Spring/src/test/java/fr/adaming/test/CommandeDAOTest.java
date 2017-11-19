package fr.adaming.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.dao.ICommandeDao;
import fr.adaming.dao.IProduitDao;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Produit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/test/resources/application-context.xml")
@Transactional
public class CommandeDAOTest {
	
	@Autowired
	private ICommandeDao commandeDAO;
	
	private Produit produitIn1;
	private Client clientIn;
	private Commande commandeIn;
	private Date dateIn;
	
	@SuppressWarnings("deprecation")
	@Before
	public void init(){
		produitIn1 = new Produit(
				"testProduitDesignation1",
				"testProduitDescription1",
				123.4,
				456,
				"testProduitImage1",
				false,
				null);
		
		clientIn = new Client(1, "testClientNom", "testClientAdresse", "testClient@mail", "testClientTel","testClientPassword");

		dateIn = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateIn = dateFormat.parse("2017-11-11");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		commandeIn = new Commande(1,
				dateIn);
	}
	
	@Test
	public void testEnregistrementCommande(){
		Date dateAdd = new Date();
		commandeIn.setDateCommande(dateAdd);
		Commande commandeOut = commandeDAO.enregistrementCommande(new Commande(dateAdd));
		
		//on verifie que la commande a bien été enregistrée
		assertNotNull(commandeOut.getIdCommande());
		
		assertEquals(commandeIn.getDateCommande(),commandeOut.getDateCommande());
		
	}
	
	@Test
	public void testGetCommandeById(){
		
		Commande commandeOut = commandeDAO.getCommandeById(new Commande(1,
				(Date)dateIn.clone()));
		
		assertEquals(commandeIn.getIdCommande(),commandeOut.getIdCommande());
		assertEquals(commandeIn.getDateCommande(),commandeOut.getDateCommande());
		
	}
	
	
	@Test
	public void testGetAllCommandes(){
		
		List<Commande> liste = commandeDAO.getAllCommandes();
		
		int in = liste.size();
		assertEquals(1, in);
		
		assertEquals(commandeIn.getIdCommande(),liste.get(0).getIdCommande());
		assertEquals(commandeIn.getDateCommande(),liste.get(0).getDateCommande());
	}
	
	
	@Test
	public void testGetCommandesByClient(){
		
		List<Commande> liste = commandeDAO.getCommandesByClient(clientIn);
		
		int in = liste.size();
		assertEquals(1, in);
		
		assertEquals(commandeIn.getIdCommande(),liste.get(0).getIdCommande());
		assertEquals(commandeIn.getDateCommande(),liste.get(0).getDateCommande());
	}
	
	@Test
	public void testDeleteCommande(){
		
		int in = commandeDAO.getAllCommandes().size();
		
		Commande commandeSuppr = commandeDAO.getCommandeById(commandeIn);
		
		boolean deleted = commandeDAO.deleteCommande(commandeSuppr);
		
		assertTrue(deleted);
		
		int out = commandeDAO.getAllCommandes().size();
		
		assertEquals(in-1,out);
		
		commandeSuppr = commandeDAO.getCommandeById(commandeIn);
		
		assertNull(commandeSuppr);
		
	}
	

}
