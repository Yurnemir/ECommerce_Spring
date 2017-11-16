package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;

@Repository
public class CommandeDaoImpl implements ICommandeDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Commande enregistrementCommande(Commande commande) {
		sessionFactory.getCurrentSession().saveOrUpdate(commande);
		return commande;
	};

	@Override
	public Commande getCommandeById(Commande commande) {
		if(commande.getIdCommande()!=0){
			return (Commande) sessionFactory.getCurrentSession().get(Commande.class, commande.getIdCommande());
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteCommande(Commande commande) {
		if (getCommandeById(commande)!=null){
			sessionFactory.getCurrentSession().delete(commande);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Commande> getCommandesByClient(Client client) {
		String req = "FROM Commande comm WHERE comm.client=:pClient";
		Query query = sessionFactory.getCurrentSession().createQuery(req);
		List<Commande> liste = (List<Commande>)query.list();
		return liste;
	}
	
	@Override
	public List<Commande> getAllCommandes(){
		String req = "FROM Commande";
		Query query = sessionFactory.getCurrentSession().createQuery(req);
		List<Commande> liste = query.list();
		System.out.println(liste);
		return liste;
	}

}
