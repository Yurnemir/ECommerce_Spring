package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;


@Service("cService")
@Transactional
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDao clientDao ;
	
	
	
	public void setClientDao(IClientDao clientDao) {
		this.clientDao = clientDao;
	}

	@Override
	public List<Categorie> getAllCategories() {
		return clientDao.getAllCategories();
	}

	@Override
	public List<Produit> getAllProduits() {
		return clientDao.getAllProduits();

	}

	@Override
	public List<Produit> getAllProduitByCategorie(Categorie c) {
		// TODO Auto-generated method stub
		return clientDao.getAllProduitByCategorie(c);
	}

	@Override
	public List<Produit> getProduitsSelect(List<Produit> lp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Produit> getProduitsByMot(String mot) {
		return clientDao.getProduitsByMot(mot);
	}

	@Override
	public Produit addProduitPanier(Produit p, int quantite, Panier pan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteProduitPanier(Produit p, Panier pan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Client enregitrementClient(Client c) {
		return clientDao.enregitrementClient(c);
	}

	@Override
	public Commande enregistrementCommande(Commande commande) {
		return clientDao.enregistrementCommande(commande);
	}

	@Override
	public Client recuperClient(Client c) {
		return clientDao.recuperClient(c);
	}

}
