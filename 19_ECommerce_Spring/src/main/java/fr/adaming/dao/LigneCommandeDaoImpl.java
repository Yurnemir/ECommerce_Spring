package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;
@Repository
public class LigneCommandeDaoImpl implements ILigneCommandeDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public LigneCommande enregistrerLigneCommande(LigneCommande ligne) {
		Session session = sessionFactory.getCurrentSession();
		session.save(ligne);
		
		return ligne;
	}

	@Override
	public List<LigneCommande> listeLigneCommandeParCommande(Commande commande) {
		Session session = sessionFactory.getCurrentSession();
		String req ="FROM LigneCommande ligne where ligne.commande.idCommande=:pIDCommande";
		Query query =session.createQuery(req);
		query.setParameter("pIDCommande", commande.getIdCommande());
		
		return query.list();
	}

}
