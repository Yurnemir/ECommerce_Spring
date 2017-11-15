package fr.adaming.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IProduitDao;
import fr.adaming.modele.Produit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/test/resources/application-context.xml")
public class ProduitDAOTest {
	
	@Autowired
	private IProduitDao produitDAO;
	
	@Test
	@Transactional
	public void testAjouterProduit(){
		
		Produit produitIn = new Produit(
				"testDesignation",
				"testDescription",
				1.0,
				2,
				"test",
				true,
				new byte[]{1,2,3,4,5,6,7,8,9,0});
		
		Produit produitOut = produitDAO.ajouterProduit(produitIn);
		
		assertEquals(produitIn,produitOut);
	}
	
	public void testGetProduitById(){
		
		Produit produitIn = produitDAO.ajouterProduit(new Produit(
				"testDesignation",
				"testDescription",
				1.0,
				2,
				"test",
				true,
				new byte[]{1,2,3,4,5,6,7,8,9,0}));
		
		Produit produitOut = produitDAO.rechercherProduitAvecId(produitIn);
		
		assertEquals(produitIn,produitOut);
	}
	
	@Test
	@Transactional
	public void testModifierProduit(){
		
		Produit produitIn = produitDAO.ajouterProduit(new Produit(
				"testDesignation",
				"testDescription",
				1.0,
				2,
				"test",
				true,
				new byte[]{1,2,3,4,5,6,7,8,9,0}));
		
		Produit produitModif = produitDAO.rechercherProduitAvecId(produitIn);

		produitModif.setDesignation("modifDesignation");
		produitModif.setDescription("modifDescription");
		
		produitModif = produitDAO.modifierProduit(produitModif);
		
		String[] in = {"modifDesignation", "modifDescription"};
		String[] out = {produitModif.getDesignation(),produitModif.getDescription()};
		
		assertArrayEquals(in, out);
	}
	
	@Test
	@Transactional
	public void testListerProduits(){
		
		int produitsIn = produitDAO.listerProduits().size();
		int ajouts = 5;
		
		for(int index = 0; index < ajouts; index++){
			produitDAO.ajouterProduit(new Produit());
		}
		
		int produitsOut = produitDAO.listerProduits().size();
		
		assertEquals(produitsIn+ajouts,produitsOut);
	}
	
	@Test
	@Transactional
	public void testSupprimerProduit(){
		
		Produit produitSuppr = produitDAO.ajouterProduit(new Produit());
		
		int produitsIn = produitDAO.listerProduits().size();
		
		produitDAO.supprimerProduit(produitSuppr);
		
		int produitsOut = produitDAO.listerProduits().size();
		
		assertEquals(produitsIn-1,produitsOut);
		assertNull(produitDAO.rechercherProduitAvecId(produitSuppr));
		
	}

}
