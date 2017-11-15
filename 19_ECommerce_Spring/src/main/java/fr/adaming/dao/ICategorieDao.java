package fr.adaming.dao;

import java.util.List;

import fr.adaming.modele.Categorie;

public interface ICategorieDao {

	public List<Categorie> listerCategorie();

	public Categorie ajouterCategorie(Categorie categorie);
	
	public Categorie rechercherCategorieParId(Categorie categorie);
	
	public void supprimerCategorie(Categorie categorie);
	
	public Categorie modifierCategorie(Categorie categorie);
}

