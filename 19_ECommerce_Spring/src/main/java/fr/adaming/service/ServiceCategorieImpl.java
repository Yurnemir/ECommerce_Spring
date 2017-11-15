package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.modele.Categorie;
@Service("categorieService")
@Transactional
public class ServiceCategorieImpl implements IServiceCategorie{

	@Autowired
	private ICategorieDao daoCategorie;
	
	public void setDaoCategorie(ICategorieDao daoCategorie) {
		this.daoCategorie = daoCategorie;
	}


	@Override
	public List<Categorie> listerCategorie() {

		return daoCategorie.listerCategorie();
				
	}


	@Override
	public Categorie ajouterCategorie(Categorie categorie) {
		return daoCategorie.ajouterCategorie(categorie);
	}


	@Override
	public Categorie rechercherCategorieParId(Categorie categorie) {
		// TODO Auto-generated method stub
		return daoCategorie.rechercherCategorieParId(categorie);
	}


	@Override
	public void supprimerCategorie(Categorie categorie) {
		daoCategorie.supprimerCategorie(categorie);
	}


	@Override
	public Categorie modifierCategorie(Categorie categorie) {
		return daoCategorie.modifierCategorie(categorie);
	}

	
	
}
