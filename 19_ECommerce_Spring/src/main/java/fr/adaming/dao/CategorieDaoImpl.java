package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Categorie;
@Repository
public class CategorieDaoImpl implements ICategorieDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public List<Categorie> listerCategorie() {
		Session session =sessionFactory.getCurrentSession();
		String req = "FROM Categorie";
		Query query =session.createQuery(req);
		//Execution de la requête
		@SuppressWarnings("unchecked")
		List<Categorie> listeCategorie = query.list();
		return listeCategorie;
	}

	@Override
	public Categorie ajouterCategorie(Categorie categorie) {
		Session session =sessionFactory.getCurrentSession();
		session.save(categorie);
		return categorie;
	}

	@Override
	public Categorie rechercherCategorieParId(Categorie categorie) {
		Session session =sessionFactory.getCurrentSession();
		String req = "FROM Categorie cat WHERE cat.idCategorie=:pIDCategorie";
		Query query = session.createQuery(req);
		
		//Passage de paramètre 
		query.setParameter("pIDCategorie", categorie.getIdCategorie());
		Categorie categorieCherche = (Categorie) query.uniqueResult();
		
		return categorieCherche;
	}

	@Override
	public void supprimerCategorie(Categorie categorie) {
		Session session =sessionFactory.getCurrentSession();
		String req = "FROM Categorie cat WHERE cat.idCategorie=:pIDCategorie";
		Query query = session.createQuery(req);
		
		//Passage de paramètre 
		query.setParameter("pIDCategorie", categorie.getIdCategorie());
		Categorie categorieCherche = (Categorie) query.uniqueResult();

		session.delete(categorieCherche);
		
	}

	@Override
	public Categorie modifierCategorie(Categorie categorie) {
		Session session =sessionFactory.getCurrentSession();
		String req = "FROM Categorie cat WHERE cat.idCategorie=:pIDCategorie";
		Query query = session.createQuery(req);
		
		//Passage de paramètre 
		query.setParameter("pIDCategorie", categorie.getIdCategorie());
		Categorie categorieCherche = (Categorie) query.uniqueResult();
		
		//Remplacement des attributs
		categorieCherche.setDescription(categorie.getDescription());
		categorieCherche.setNomCategorie(categorie.getNomCategorie());

		//On merge
		session.saveOrUpdate(categorieCherche);
		return categorieCherche;
	}

}
