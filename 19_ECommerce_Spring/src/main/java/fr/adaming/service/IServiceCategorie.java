package fr.adaming.service;

import java.util.List;

import fr.adaming.modele.Categorie;

public interface IServiceCategorie {

	public List<Categorie> listerCategorie();
	
	public Categorie ajouterCategorie(Categorie categorie);
	
	public Categorie rechercherCategorieParId(Categorie categorie);
	
	public void supprimerCategorie(Categorie categorie);
	
	public Categorie modifierCategorie(Categorie categorie);
}
