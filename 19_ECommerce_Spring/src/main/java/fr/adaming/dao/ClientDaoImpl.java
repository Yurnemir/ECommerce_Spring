package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;

@Repository
public class ClientDaoImpl implements IClientDao{

	@Autowired
	private SessionFactory sf ; 
	
	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}
	
	@Override
	public List<Categorie> getAllCategories() {
		Session s = sf.getCurrentSession() ; 
		// la requete HQL
		String req ="FROM Categorie";
		
		// recup d'un query
		Query query = s.createQuery(req) ; 
		
		
		// envoi de la req et recup du résultat
		@SuppressWarnings("unchecked")
		List<Categorie> liste = query.list();
		
		return liste;
	}

	@Override
	public List<Produit> getAllProduits() {
		Session s = sf.getCurrentSession() ; 
		// la requete HQL
		String req ="FROM Produit";
		
		// recup d'un query
		Query query = s.createQuery(req) ; 
		
		
		// envoi de la req et recup du résultat
		@SuppressWarnings("unchecked")
		List<Produit> liste = query.list();
		
		return liste;
	}

	@Override
	public List<Produit> getAllProduitByCategorie(Categorie c) {
		Session s = sf.getCurrentSession() ; 
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
	public List<Produit> getProduitsSelect(List<Produit> lp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getProduitsByMot(String mot) {
		Session s = sf.getCurrentSession() ; 
		String req = "SELECT prod FROM Produit prod WHERE prod.description LIKE :pDescription";
		Query query = s.createQuery(req) ; 

		// Production du paramètre
		StringBuilder intitule = new StringBuilder();
		intitule.append('%');
		intitule.append(mot);
		intitule.append('%');
		String intituleParam = intitule.toString();
		//Passage du paramètre
		query.setParameter("pDescription", intituleParam);
		@SuppressWarnings("unchecked")
		List<Produit> liste = query.list();
		
		return liste;
	}

	@Override
	public Produit addProduitPanier(Produit p, int quantite, Panier pan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteProduitPanier(Produit p, Panier pan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Client enregitrementClient(Client c) {
		Session s = sf.getCurrentSession() ; 
		
		s.save(c) ; 
		return c ;
	}

	@Override
	public Commande enregistrementCommande(Commande commande) {
		Session s = sf.getCurrentSession() ; 
		
		s.save(commande) ; 
		return commande ;
	}

	@Override
	public Client recuperClient(Client c) {
		Session s = sf.getCurrentSession() ; 
		String req ="SELECT client from Client client WHERE client.idClient=:pIDClient";
		Query query = s.createQuery(req) ; 
		System.out.println("requête créée");
		query.setParameter("pIDClient",c.getIdClient());
	//	query.setParameter("pCodePerso", c.getCodePerso());
		Client clientChercher = (Client) query.uniqueResult();

		return clientChercher;
	}

	
	
}
