package fr.adaming.dao;

import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Client;

@Repository
public class ClientDaoImpl implements IClientDao{

	@Autowired
	private SessionFactory sf ; 
	
	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Client createClient(Client c) {
		Session s = sf.getCurrentSession() ;
		Client preExistClient = getClientByInfo(c);
		if (preExistClient!=null){
			return preExistClient;
		} else {
			s.save(c);
			return c;
		}
	}

	@Override
	public Client getClientById(Client c) {
		return (Client) sf.getCurrentSession().get(Client.class, c.getIdClient());
	}

	@Override
	public Client getClientByInfo(Client c) {
		String req = "FROM Client c WHERE c.email=:pEmail AND c.codePerso=:pPass";
		Query query = sf.getCurrentSession().createQuery(req);
		query.setParameter("pEmail", c.getEmail());
		query.setParameter("pPass", c.getCodePerso());
		
		try {
			return (Client)query.uniqueResult();
		} catch (NonUniqueResultException ex){
			return null;
		}
	}

	@Override
	public Client modifClient(Client c) {
		Client modifClient = getClientById(c);
		
		if (modifClient!=null){
			sf.getCurrentSession().save(c);
			return c;
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteClient(Client c) {
		Client delClient = getClientById(c);
		if(delClient!=null){
			sf.getCurrentSession().delete(c);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Client> listerClient() {
		String req ="From Client";
		return sf.getCurrentSession().createQuery(req).list();
	}

	@Override
	public List<Client> rechercheClientMail(Client c) {
		
		String req="FROM Client c WHERE c.email=:pEmail";
		Query query = sf.getCurrentSession().createQuery(req);
		query.setParameter("pEmail", c.getEmail());

		return query.list();
	}
	

}
