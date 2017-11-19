package fr.adaming.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Produit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/test/resources/application-context.xml")
public class CategorieDAOTest {
	
	@Autowired
	private ICategorieDao categorieDAO;
	
	private Categorie categorieIn;
//	private List<Produit> liste;
//	private Produit produit1;
//	private Produit produit2;
//	private LigneCommande ligneCommande1;
//	private LigneCommande ligneCommande2;
//	private Commande commande;
//	private Client client;
	
	@Before
	public void init(){
		categorieIn= new Categorie(1,"testCategorieNom","testCategorieDescription");
//		produit1 = new Produit(1, "testProduitDesignation1", "testProduitDescription1", 123.4, 456, "testProduitImage1", false, null);
//		produit1.setCategorie(categorieIn);
//		
//		
//		produit2 = new Produit(2, "testProduitDesignation2", "testProduitDescription2", 345.6, 789, "testProduitImage2", true, null);
//		produit2.setCategorie(categorieIn);
//		liste = new ArrayList<Produit>();
//		categorieIn.setListeProduits(liste);
	}
	
	@Test
	@Transactional
	public void testGetCategorieById(){
		//Utilisation d'une bdd connue, contenant une categorie
		//On verifie si le getById renvoie la même catégorie
//		liste.add(produit1);
//		liste.add(produit2);
//		categorieIn.setListeProduits(liste);
		Categorie categorieOut = categorieDAO.rechercherCategorieParId(new Categorie(1,"testCategorieNom","testCategorieDescription"));
		
		//System.out.println("In: "+categorieIn+" "+categorieIn.getListeProduits().get(0).getListeLigneCommande());
		//System.out.println("Out: "+categorieOut+" "+categorieOut.getListeProduits().get(0).getListeLigneCommande());
		
		//assertEquals est lourd avec les associations, donc on ne teste que les variables du toString... donc le toString
		
		assertEquals(categorieIn.toString(),categorieOut.toString());
	}
	

	@Test
	@Transactional
	public void testAjoutCategorie (){
		
		Categorie categorieOut = categorieDAO.ajouterCategorie(new Categorie("Test","Categorie de Test"));
		
		//On ne teste pas l'id (donc pas de toString) vu qu'il n'est pas donné...
		String[] in = {"Test","Categorie de Test"};
		String[] out = {categorieOut.getNomCategorie(),categorieOut.getDescription()};
		
		assertArrayEquals(in,out);
		assertNotNull(categorieOut.getIdCategorie());
	}
	
	@Test
	@Transactional
	public void testModifCategorie(){
		//On modifie une categorie existante
		Categorie categorieModif = categorieDAO.rechercherCategorieParId(categorieIn);
		
		categorieModif.setNomCategorie("foo");
		categorieModif.setDescription("bar");
		
		//On verifie si la modification a bien ete faite
		categorieModif = categorieDAO.modifierCategorie(categorieModif);
		
		String[] in = {"foo","bar"};
		String[] out = {categorieModif.getNomCategorie(),categorieModif.getDescription()};
		
		assertArrayEquals(in,out);
	}

	
	@Test
	@Transactional
	public void testListerCategorie(){
		//on obtient la quantité de départ d'entités dans la base
		int categoriesIn = categorieDAO.listerCategorie().size();
		
		// on en rajoute un certain nombre
		int ajouts = 5;
		
		for(int index = 0; index < ajouts; index++){
			categorieDAO.ajouterCategorie(new Categorie());
		}
		
		//on vérifie si le nouveau nombre correspond
		int categoriesOut = categorieDAO.listerCategorie().size();
		
		assertEquals(categoriesIn+ajouts,categoriesOut);
	}
	
	@Test
	@Transactional
	public void testSupprimerCategories(){
		// on recupere le nombre d'elements au depart
		
		int categoriesIn = categorieDAO.listerCategorie().size();
		
		// on supprime l'element
		categorieDAO.supprimerCategorie(categorieIn);
		
		//on recupere le nombre d'elements à la fin
		int categoriesOut = categorieDAO.listerCategorie().size();
		
		//premier assert pour verifier qu'on a bien supprimé quelquechose
		assertEquals(categoriesIn - 1, categoriesOut);
		//deuxieme assert pour verifier que c'est bien la categorie voulue qui a été supprimée
		assertNull(categorieDAO.rechercherCategorieParId(categorieIn));
	}
}
