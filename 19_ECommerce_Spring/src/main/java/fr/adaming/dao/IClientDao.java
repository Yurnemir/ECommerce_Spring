package fr.adaming.dao;

import fr.adaming.modele.Client;

public interface IClientDao {

	public Client getClientByInfo(Client c); 
	public Client getClientById(Client c);
	public Client createClient(Client c);
	public Client modifClient(Client c);
	public boolean deleteClient (Client c);
	
	
	
}
