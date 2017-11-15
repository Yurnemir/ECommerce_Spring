package fr.adaming.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;

@Repository
public class ProduitDaoImpl implements IProduitDao{

	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public List<Produit> listerProduits() {
		Session session = sessionFactory.getCurrentSession();
		//Requ�te SQL 
		String req = "FROM Produit";
		Query query =session.createQuery(req);
		//Execution de la requ�te
		List<Produit> listeProduits = query.list();
		return listeProduits;
	}


	@Override
	public List<Produit> getProduitsByCategorie(Categorie c) {
		Session s = sessionFactory.getCurrentSession() ; 
		String req = "Select p from Produit p where p.categorie.idCategorie=:pidc";

		// recup d'un query
		Query query = s.createQuery(req) ; 

		// passage des param
		query.setParameter("pidc", c.getIdCategorie());

		try{
		List<Produit> listeProduitsCat = query.list();
		return listeProduitsCat;

		} catch (Exception e){
			System.out.println("Impossible de trouver");
			return null;
		}
	}

	@Override
	public List<Produit> getSelectedProduit() {
		String req = "From Produit p WHERE p.selectionne:=true";
		
		Query query = sessionFactory.getCurrentSession().createQuery(req);
		
		List<Produit> liste = (List<Produit>)query.list();
		
		return liste;
	}
	
	@Override
	public Produit rechercherProduitAvecId(Produit produit) {
		Session session = sessionFactory.getCurrentSession();
		//Requ�te SQL 
		String req = "FROM Produit prod WHERE prod.idProduit=:pIdProduit";
		Query query = session.createQuery(req);
		//Passage du param�tre
		query.setParameter("pIdProduit", produit.getIdProduit());
		Produit produitCherche = (Produit) query.uniqueResult();
		return produitCherche;
	}

	@Override
	public List<Produit> getProduitsByMot(String mot) {
		Session s = sessionFactory.getCurrentSession() ; 
		String req = "SELECT prod FROM Produit prod WHERE prod.description LIKE :pDescription";
		Query query = s.createQuery(req) ; 

		// Production du param�tre
		StringBuilder intitule = new StringBuilder();
		intitule.append('%');
		intitule.append(mot);
		intitule.append('%');
		String intituleParam = intitule.toString();
		//Passage du param�tre
		query.setParameter("pDescription", intituleParam);
		@SuppressWarnings("unchecked")
		List<Produit> liste = query.list();
		
		return liste;
	}
	
	@Override
	public Produit ajouterProduit(Produit produit) {
		Session session = sessionFactory.getCurrentSession();
		session.save(produit);
		
		return produit;
	}

	
	public void supprimerProduit(Produit produit){
		Session session = sessionFactory.getCurrentSession();
		//Requ�te SQL 
		String req = "FROM Produit prod WHERE prod.idProduit=:pIdProduit";
		Query query = session.createQuery(req);
		//Passage du param�tre
		query.setParameter("pIdProduit", produit.getIdProduit());
		Produit produitCherche = (Produit) query.uniqueResult();

		session.delete(produitCherche);
	}


	@Override
	public Produit modifierProduit(Produit produit) {
		Session session = sessionFactory.getCurrentSession();
		//Requ�te SQL 
		String req = "FROM Produit prod WHERE prod.idProduit=:pIdProduit";
		Query query = session.createQuery(req);
		//Passage du param�tre
		query.setParameter("pIdProduit", produit.getIdProduit());
		Produit produitCherche = (Produit) query.uniqueResult();

		
		//Remplacement des attributs: 
		produitCherche.setDescription(produit.getDescription());
		produitCherche.setDesignation(produit.getDesignation());
		produitCherche.setIdProduit(produit.getIdProduit());
		produitCherche.setPrix(produit.getPrix());
		produitCherche.setQuantite(produit.getQuantite());
		produitCherche.setImage(produit.getImage());
		produitCherche.setCategorie(produit.getCategorie());
		
		session.saveOrUpdate(produitCherche);
		
		return produitCherche;
	}


	@Override
	public int assoicierImageProduit(Produit produit) {
		Session session = sessionFactory.getCurrentSession();
		String req = "UPDATE Produit prod SET prod.imageFichier=:pImage WHERE prod.idProduit=:pIDProduit";
		Query query = session.createQuery(req);
		
		//Passage des param�tre.
		try {
			query.setParameter("pImage",produit.getImageFichier());
			query.setParameter("pIDProduit",produit.getIdProduit());
			return query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
			return 0;
		}
	}


}
