package fr.adaming.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.modele.Categorie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/test/resources/application-context.xml")
public class CategorieDAOTest {
	
	@Autowired
	private ICategorieDao categorieDAO;
	
	@Test
	@Transactional
	public void testAjoutCategorie (){
		
		Categorie categorieIn = new Categorie("Test","Categorie de Test");
		Categorie categorieOut = categorieDAO.ajouterCategorie(categorieIn);
		
		String[] in = {categorieIn.getNomCategorie(),categorieIn.getDescription()};
		String[] out = {categorieOut.getNomCategorie(),categorieOut.getDescription()};
		
		assertArrayEquals(in,out);
		assertNotNull(categorieOut.getIdCategorie());
	}
	
	@Test
	@Transactional
	public void testGetCategorieById(){
		//On envoie une nouvelle categorie, dont on recupere l'id apres creation, et on verifie si le getById renvoie la même catégorie
		Categorie categorieIn = categorieDAO.ajouterCategorie(new Categorie("Test","Categorie de Test"));
		Categorie categorieOut = categorieDAO.rechercherCategorieParId(categorieIn);
		
		assertEquals(categorieIn,categorieOut);
	}
	
	@Test
	@Transactional
	public void testModifCategorie(){
		//on cree une categorie, on la modifie, on verifie si la modification a bien ete faite
		Categorie categorieIn = categorieDAO.ajouterCategorie(new Categorie("Test","Categorie de Test"));
		
		Categorie categorieModif = categorieDAO.rechercherCategorieParId(categorieIn);
		
		categorieModif.setNomCategorie("foo");
		categorieModif.setDescription("bar");
		
		categorieModif = categorieDAO.modifierCategorie(categorieModif);
		
		String[] in = {"foo","bar"};
		String[] out = {categorieModif.getNomCategorie(),categorieModif.getDescription()};
		
		assertArrayEquals(in,out);
	}
	
	@Test
	@Transactional
	public void testListerCategorie(){
		//on obtient la quantité de départ d'entités dans la base, on en rajoute un certain nombre, puis on vérifie si le nouveau nombre correspond
		int categoriesIn = categorieDAO.listerCategorie().size();
		int ajouts = 5;
		
		for(int index = 0; index < ajouts; index++){
			categorieDAO.ajouterCategorie(new Categorie());
		}
		
		int categoriesOut = categorieDAO.listerCategorie().size();
		
		assertEquals(categoriesIn+ajouts,categoriesOut);
	}
	
	@Test
	@Transactional
	public void testSupprimerCategories(){
		// on recupere le nombre d'elements au depart, on supprime l'element, on verifie que le nombre d'elements est egal à -1
		Categorie categorieSuppr = categorieDAO.ajouterCategorie(new Categorie());
		
		int categoriesIn = categorieDAO.listerCategorie().size();
		
		categorieDAO.supprimerCategorie(categorieSuppr);
		
		int categoriesOut = categorieDAO.listerCategorie().size();
		
		//premier assert pour verifier qu'on a bien supprimé quelquechose
		assertEquals(categoriesIn - 1, categoriesOut);
		//deuxieme assert pour verifier que c'est bien la categorie voulue qui a été supprimée
		assertNull(categorieDAO.rechercherCategorieParId(categorieSuppr));
	}
}
