package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Administrateur;

@Repository
public class AdminDaoImpl implements IAdminDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Administrateur getAdminByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		String req ="FROM Administrateur admin WHERE admin.identifiant=:pIdentifiant";
		Query query = session.createQuery(req);
		query.setParameter("pIdentifiant", name);
		Administrateur administrateurRecup = (Administrateur) query.uniqueResult();
		return administrateurRecup;
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
