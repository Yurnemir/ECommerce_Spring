package fr.adaming.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.LigneCommande;
@Repository
public class LigneCommandeDaoImpl implements ILigneCommandeDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public LigneCommande enregistrerLigneCommande(LigneCommande ligne) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println("Enregistrement d'une ligne");
		session.save(ligne);
		
		return ligne;
	}

}
