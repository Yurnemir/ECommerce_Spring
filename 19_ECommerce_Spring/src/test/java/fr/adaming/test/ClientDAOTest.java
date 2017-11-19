package fr.adaming.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.modele.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/test/resources/application-context.xml")
public class ClientDAOTest {
	
	@Autowired
	private IClientDao clientDAO;
	
	private Client clientIn;
	
	@Before
	public void init (){
		clientIn = new Client(1, "testClientNom", "testClientAdresse", "testClient@mail", "testClientTel","testClientPassword");
	}
	
	@Test
	@Transactional
	public void testGetClientById(){
		//on obtient le client connu
		Client clientGet = clientDAO.getClientById(new Client(1, "testClientNom", "testClientAdresse", "testClient@mail", "testClientTel","testClientPassword"));
		
		//on vérifie que les infos sont les memes, donc le tostring
		assertEquals(clientIn.toString(),clientGet.toString());
	}
	
	@Test
	@Transactional
	public void testGetClientByInfo(){
		//on obtient le client connu
		Client clientGet = clientDAO.getClientByInfo(new Client(1, "testClientNom", "testClientAdresse", "testClient@mail", "testClientTel","testClientPassword"));
		
		//on vérifie que les infos sont les memes, donc le tostring
		assertEquals(clientIn.toString(),clientGet.toString());
	}
	
	@Test
	@Transactional
	public void testCreateClient(){
		// On ajoute un nouveau client
		Client clientAdd = clientDAO.createClient(new Client("testNom", "testAdresse", "testEmail", "testTel", "testCode"));
		
		String[] in = {"testNom", "testAdresse", "testEmail", "testTel", "testCode"};
		String[] out = {clientAdd.getNomClient(),clientAdd.getAdresse(),clientAdd.getEmail(),clientAdd.getTel(),clientAdd.getCodePerso()};
		
		//on verifie que les infos sont les memes
		assertArrayEquals(in,out);
		
		//on verifie qu'il a bien un ID
		assertNotNull(clientAdd.getIdClient());
		
	}
	
	@Test
	@Transactional
	public void testModifClient(){
		
		//on recupere le client de la bdd
		Client clientModif = clientDAO.getClientById(clientIn);
		
		//on le modifie
		clientModif.setNomClient("testNom");
		clientModif.setAdresse("testAdresse");
		clientModif.setCodePerso("testCode");
		clientModif.setEmail("testEmail");
		clientModif.setTel("testTel");
		
		//on l'enregistre
		clientDAO.modifClient(clientModif);
		
		//on le rerecupere
		clientModif = clientDAO.getClientById(clientModif);
		
		String[] in = {"testNom", "testAdresse", "testEmail", "testTel", "testCode"};
		String[] out = {clientModif.getNomClient(),clientModif.getAdresse(),clientModif.getEmail(),clientModif.getTel(),clientModif.getCodePerso()};
		
		//On verifie que les infos sont les memes
		assertArrayEquals(in, out);
		
	}
	
	@Test
	@Transactional
	public void testDeleteClient(){
		
		//on recupere le client a supprimer
		Client clientSuppr = clientDAO.getClientById(clientIn);
		
		//on le supprime
		clientDAO.deleteClient(clientSuppr);
		
		//on essaye de le re recuperer
		clientSuppr = clientDAO.getClientById(clientSuppr);
		
		assertNull(clientSuppr);
		
	}

}
