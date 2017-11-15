package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IAdminDao;
import fr.adaming.modele.Administrateur;

@Service("serviceAdmin")
@Transactional
public class AdminServiceImpl implements IServiceAdmin{

	@Autowired
	private IAdminDao daoAdmin;
//Setters pour injection
	public void setDaoAdmin(IAdminDao daoAdmin) {
		this.daoAdmin = daoAdmin;
	}
	
	//Méthodes propres
	@Override
	public Administrateur connexionAdmin(Administrateur administrateur) {
		return daoAdmin.connexionAdmin(administrateur);
	}
	
	
}
