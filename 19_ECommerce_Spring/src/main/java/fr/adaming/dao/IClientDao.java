package fr.adaming.dao;

import java.util.List;

import fr.adaming.modele.Client;

public interface IClientDao {

	public Client getClientByInfo(Client c); 
	public Client getClientById(Client c);
	public Client createClient(Client c);
	public Client modifClient(Client c);
	public boolean deleteClient (Client c);
	public List<Client> listerClient();
	public List<Client> rechercheClientMail(Client c);
	
	
}
