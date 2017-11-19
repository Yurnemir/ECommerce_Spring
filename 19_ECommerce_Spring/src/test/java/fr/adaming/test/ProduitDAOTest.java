package fr.adaming.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IProduitDao;
import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/test/resources/application-context.xml")
public class ProduitDAOTest {
	
	@Autowired
	private IProduitDao produitDAO;
	
	private Produit produitIn;
	
	@Before
	public void init(){
		produitIn = new Produit(1,
				"testProduitDesignation1",
				"testProduitDescription1",
				123.4,
				456,
				"testProduitImage1",
				false,
				null);
	}
	
	@Test
	@Transactional
	public void testAjouterProduit(){
		//on cree un nouvea produit, on l'ajoute
		Produit produitAdd = new Produit(
				"testAddProduitDesignation1",
				"testAddProduitDescription1",
				234.5,
				678,
				"testAddProduitImage1",
				true,
				null);
		
		Produit produitOut = produitDAO.ajouterProduit(produitAdd);
		
		//produitOut doit avoir un id non nul
		assertNotNull(produitOut.getIdProduit());
		
		//les infos doivent être les mêmes (partiel)
		String[] in = {"testAddProduitDesignation1","testAddProduitDescription1"};
		String[] out = {produitOut.getDesignation(),produitOut.getDescription()};
		
		assertArrayEquals(in,out);
		
	}
	
	@Test
	@Transactional
	public void testGetProduitById(){
		//on recupere un produit connu
		
		Produit produitOut = produitDAO.rechercherProduitAvecId(new Produit(1,
				"testProduitDesignation1",
				"testProduitDescription1",
				123.4,
				456,
				"testProduitImage1",
				false,
				null));
		
		//on verifie que les informations sont les bonnes
		assertEquals(produitIn.toString(),produitOut.toString());
	}
	
	@Test
	@Transactional
	public void testModifierProduit(){
		
		//on recupere un produit connu
		Produit produitModif = produitDAO.rechercherProduitAvecId(produitIn);

		//on le modifie
		produitModif.setDesignation("modifDesignation");
		produitModif.setDescription("modifDescription");
		
		//on enregistre la modif
		produitModif = produitDAO.modifierProduit(produitModif);
		
		//on verifie que les infos modifiées sont bonnes
		String[] in = {"modifDesignation", "modifDescription"};
		String[] out = {produitModif.getDesignation(),produitModif.getDescription()};
		
		assertArrayEquals(in, out);
	}
	
	@Test
	@Transactional
	public void testListerProduits(){
		//on compte le nombre de produits avant ajout
		int produitsIn = produitDAO.listerProduits().size();
	
		//on en rajoute un certain nombre
		int ajouts = 5;
		for(int index = 0; index < ajouts; index++){
			produitDAO.ajouterProduit(new Produit());
		}
		
		//on recompte
		int produitsOut = produitDAO.listerProduits().size();
		
		// on verifie que le recompte correspond bien au nombre d'ajouts
		assertEquals(produitsIn+ajouts,produitsOut);
	}
	
	@Test
	@Transactional
	public void testSupprimerProduit(){
		
		//on determine le nombre de produits (normalement, 1)
		int produitsIn = produitDAO.listerProduits().size();
		
		//on supprime un produit dont on est sur de la présence
		//on retire aussi le produit de la categorie (a fair ecote service)
		produitIn = produitDAO.rechercherProduitAvecId(produitIn);
		produitIn.getCategorie().getListeProduits().remove(produitIn);
		produitDAO.supprimerProduit(produitIn);
		
		//on recompte
		int produitsOut = produitDAO.listerProduits().size();
		
		//on verifie que le nombre a diminué d'un
		assertEquals(produitsIn-1,produitsOut);
		//on verifie que le produit recherche est bien absent
		assertNull(produitDAO.rechercherProduitAvecId(produitIn));
	}
	
	
	@Test
	@Transactional
	public void testGetProduitByCategorie(){
		//on recupere une liste de produits dont on connait la categorie
		Categorie c = new Categorie(1, "testCategorieNom", "testCategorieDescription");

		List<Produit> produitsOut = produitDAO.getProduitsByCategorie(c);
		
		//on verifie que la liste est de la longueur attendue
		assertEquals(2,produitsOut.size());
	}
	
	@Test
	@Transactional
	public void testSelectedProduit(){
		//on recupere une liste de produits selected

		List<Produit> produitsOut = produitDAO.getSelectedProduit();
		
		//on verifie que la liste est de la longueur attendue
		assertEquals(1,produitsOut.size());
	}
	
	@Test
	@Transactional
	public void testgetProduitsByMot(){
		//on recupere une liste de produits contenant un mot clé

		List<Produit> produitsOut = produitDAO.getProduitsByMot("Description1");
		
		//on verifie que la liste est de la longueur attendue
		assertEquals(1,produitsOut.size());
	}

}
