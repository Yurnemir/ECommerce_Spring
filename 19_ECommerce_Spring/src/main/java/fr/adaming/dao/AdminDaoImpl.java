package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Administrateur;

/**
 * Interaction avec les administrateurs de la db
 * @author inti
 */
@Repository
public class AdminDaoImpl implements IAdminDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	//Setters de l'injection
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	@Override
	public Administrateur connexionAdmin(Administrateur administrateur) {
		//Instanciation de la session
		Session session = sessionFactory.getCurrentSession();
		//Requête HQL
		String req ="FROM Administrateur admin WHERE admin.identifiant=:pIdentifiant AND admin.mdp=:pMdP";
		Query query = session.createQuery(req);
		//Passage des paramètres.
		query.setParameter("pIdentifiant", administrateur.getIdentifiant());
		query.setParameter("pMdP", administrateur.getMdp());
		//Exécution de la requête
		Administrateur administrateurRecup = (Administrateur) query.uniqueResult();
		
		return administrateurRecup;
	}

}
