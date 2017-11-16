package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.modele.Administrateur;
import fr.adaming.modele.Role;

@Repository
public class RoleDaoImpl implements IRoleDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	@Override
	public Role getRoleByAdmin(Administrateur admin) {
		Session session = sessionFactory.getCurrentSession();
		String request = "FROM Role r WHERE r.administrateur.idAdmin=:pIdAdmin";
		Query query = session.createQuery(request);
		query.setParameter("pIdAdmin", admin.getIdAdmin());
		Role role = (Role) query.uniqueResult();
		return role;
	}

}
