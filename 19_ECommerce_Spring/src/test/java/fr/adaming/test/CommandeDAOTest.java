package fr.adaming.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

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
	
	@Autowired
	private IProduitDao produitDAO;
	
	@Autowired
	private IClientDao clientDAO;
	
	private static Produit produitIn;
	private static Client clientIn;
	private static LigneCommande ligneIn;
	private static Commande commandeIn;
	
	@BeforeClass
	public static void init(){
		produitIn = new Produit(
				"testDesignation",
				"testDescription",
				1.0,
				2,
				"test",
				true,
				new byte[]{1,2,3,4,5,6,7,8,9,0});		
		
		ligneIn = new LigneCommande();
		ligneIn.setProduit(produitIn);
		ligneIn.setQuantite(10);
		
		
		clientIn = new Client("testNom", "testAdresse", "testEmail", "0123456789");

		commandeIn = new Commande();
		
		commandeIn.setListeLigneCommande(new ArrayList<LigneCommande>());
		commandeIn.addLigne(ligneIn);
	}
	
	@Test
	public void testEnregistrementCommande(){
		clientDAO.createClient(clientIn);
		produitDAO.ajouterProduit(produitIn);
		ligneIn.setProduit(produitIn);
		commandeIn.getListeLigneCommande().add(ligneIn);
		commandeIn.setClient(clientIn);
		
		Commande commandeOut = commandeDAO.enregistrementCommande(commandeIn);
		
		assertEquals(produitIn,commandeOut.getListeLigneCommande().get(0).getProduit());
		assertEquals(10, commandeOut.getListeLigneCommande().get(0).getQuantite());
		assertEquals(clientIn,commandeOut.getClient());
		
	}
	
	@Test
	public void testGetCommandeById(){
		commandeDAO.enregistrementCommande(commandeIn);
		
		Commande commandeEmpty = new Commande();
		commandeEmpty.setIdCommande(commandeIn.getIdCommande());
		
		Commande commandeOut = commandeDAO.getCommandeById(commandeEmpty);
		
		assertEquals(produitIn,commandeOut.getListeLigneCommande().get(0).getProduit());
		assertEquals(10, commandeOut.getListeLigneCommande().get(0).getQuantite());
		assertEquals(clientIn,commandeOut.getClient());
		
	}
	@Ignore
	@Test
	public void testDeleteCommande(){
		clientDAO.createClient(clientIn);
		produitDAO.ajouterProduit(produitIn);
		ligneIn.setProduit(produitIn);
		commandeIn.getListeLigneCommande().add(ligneIn);
		commandeIn.setClient(clientIn);
		
		commandeDAO.enregistrementCommande(commandeIn);
		
		System.out.println(commandeDAO.getCommandeById(commandeIn));
		
		int commandesIn = commandeDAO.getAllCommandes().size();
		
		commandeDAO.deleteCommande(commandeIn);
		
		int commandesOut = commandeDAO.getAllCommandes().size();
		
		assertEquals(commandesIn-1,commandesOut);
		
		assertNull(commandeDAO.getCommandeById(commandeIn));
	}

}
