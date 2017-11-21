package fr.adaming.controllers;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import java.awt.Desktop;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.cfg.CreateKeySecondPass;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;
import fr.adaming.service.IServiceCategorie;
/**
 * 
 * @author inti0236
 *Cette classe est le controlleur de toutes les opï¿½rations liï¿½es au panier.
 */
@Controller
public class ControlleurPanier {

	@Autowired
	private IProduitService serviceProduit;
	@Autowired
	private IClientService serviceClient;
	@Autowired
	private ICommandeService serviceCommande;
	@Autowired
	private ILigneCommandeService serviceLigneCommande;
	@Autowired
	private IServiceCategorie serviceCategorie;
	

	/**
	 * Mï¿½thode permettant d'ajouter exactement un exemplaire du produit en cliquant sur le lien. 
	 * Des vï¿½rifications sont effectuï¿½es permettant de ne garder qu'un seule Ligne de commande par produit et 
	 * de s'assurer que l'on peut pas commander plus que le stock disponible.
	 * @param idProduit : id du produit dans la base de donnï¿½es.Permet de rï¿½cupï¿½rer
	 *  toutes les infos de celui-ci grï¿½ce ï¿½ une mï¿½thode de DAO
	 * @param session Session HTTP (contient notamment le panier)
	 * @return ModelAndView de la page principale contient le tableau des produits
	 */
	@RequestMapping(value = "/ajoutViaLien")
	public String ajoutPanierParLigne( Model model, @RequestParam("pIdProduit") int idProduit,
			HttpSession session) {
		Produit produitTemp = new Produit();
		produitTemp.setIdProduit(idProduit);
		LigneCommande ligneAModifier = new LigneCommande();
		boolean verifLigne = false;
		;

		Produit produitDemande = serviceProduit.rechercherProduitAvecId(produitTemp);

		// On rï¿½cupï¿½re le panier de la session.

		// System.out.println(produitDemande);
		// On rï¿½cupï¿½re le panier depuis le modele.
		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		for (LigneCommande ligne : listeLigneCommande) {
			System.out.println("Id du produit dans de la ligne" + ligne.getProduit().getIdProduit()
					+ "vs produit demandï¿½" + idProduit);
			if (ligne.getProduit().getIdProduit() == idProduit) {
				System.out.println("Produit dï¿½jï¿½ dans la commande il faut modifier la ligne");
				verifLigne = true;
				// Si la ligne existe dï¿½jï¿½ on la rï¿½cupï¿½re
				ligneAModifier = ligne;
				break;
			} else {
				System.out.println(
						"Le produit n'existe pas on peut donc crï¿½er une ligne de commande et l'ajouter dans le panier");
				verifLigne = false;

			}

		}

		if (verifLigne == true) {

			System.out.println("On paye initialement " + ligneAModifier.getPrix() + "pour"
					+ ligneAModifier.getQuantite() + "unitï¿½s");

			double nouveauPrix = ligneAModifier.getPrix() + produitDemande.getPrix();
			int nouvelleQuantite = ligneAModifier.getQuantite() + 1;
			if (produitDemande.getQuantite() < nouvelleQuantite) {
				System.out.println("Trop de produit demandï¿½");
				System.out.println("La ligne de commande pour ce produit ne sera pas modifiï¿½e");
			} else {
				ligneAModifier.setPrix(nouveauPrix);
				ligneAModifier.setQuantite(nouvelleQuantite);
				System.out.println("On paye dï¿½sormais " + ligneAModifier.getPrix() + "pour"
						+ ligneAModifier.getQuantite() + "unitï¿½s");

			}

		}

		// Ligne de commande ï¿½ ajouter
		if (verifLigne == false) {
			LigneCommande ligneCommande = new LigneCommande();
			// System.out.println(listeLigneCommande.size());
			// ligneCommande.setPositionTableau(listeLigneCommande.size()-1);
			// ligneCommande.setIdLigne(liste);
			ligneCommande.setProduit(produitDemande);
			ligneCommande.setQuantite(1);
			ligneCommande.setPrix(ligneCommande.getQuantite() * produitDemande.getPrix());
			listeLigneCommande.add(ligneCommande);
			panierSession.setListeLignesCommande(listeLigneCommande);
		}

		System.out.println(panierSession.getListeLignesCommande().size());

		// Ajoute le panier ï¿½ la session
		session.setAttribute("panier", panierSession);
		//List<Produit> listeProduit = serviceProduit.listerProduits();
		List<Categorie> listeCategorie = serviceCategorie.listerCategorie();

		model.addAttribute("listeCategorie",listeCategorie);
		return "accueil";
	}

	/**
	 * Permet d'ajouter un nombre d'exemplaire quelconque de produit au panier.
	 * Des vï¿½rifications sont effectuï¿½es permettant de ne garder qu'un seule Ligne de commande par produit et 
	 * de s'assurer que l'on peut pas commander plus que le stock disponible.

	 * @param model : modele MVC de la page principale
	 * @param idProduit Id du produit demandï¿½
	 * @param quantite Quantitï¿½ demandï¿½e du produit
	 * @param session Session Http
	 * @return String adresse de la page principale
	 */
	// Panier avec formulaire
	@RequestMapping(value = "/ajouterProduitPanierViaFormulaire", method = RequestMethod.GET)
	public String ajoutPanierParLigneAvecForm(Model model, @RequestParam("identifiantProduit") int idProduit,
			@RequestParam("quantite") int quantite, HttpSession session) {
		boolean verifExistenceLigne = false;
		LigneCommande ligneCommandeTemp = new LigneCommande();
		System.out.println(idProduit + " " + quantite);
		// Rï¿½cupï¿½rer le produit
		Produit produitTemp = new Produit();
		produitTemp.setIdProduit(idProduit);
		Produit produitDemande = serviceProduit.rechercherProduitAvecId(produitTemp);
		// Rï¿½cupï¿½rer le panier depuis la session et la liste des lignes
		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		for (LigneCommande ligne : listeLigneCommande) {
			System.out.println(
					"Id du produit actuel" + ligne.getProduit().getIdProduit() + "vs celui demandï¿½" + idProduit);
			if (ligne.getProduit().getIdProduit() == idProduit) {
				verifExistenceLigne = true;
				ligneCommandeTemp = ligne;

				break;
			} else {
				verifExistenceLigne = false;
			}
		}

		if (verifExistenceLigne == true) {
			System.out.println("Ce produit a dï¿½jï¿½ une ligne. Il faut actualiser la ligne");
			int quantiteNouvelle = ligneCommandeTemp.getQuantite() + quantite;
			double prixNouveau = ligneCommandeTemp.getPrix() + produitDemande.getPrix() * quantite;
			if (produitDemande.getQuantite() < quantiteNouvelle) {
				System.out.println("Trop de produit commandï¿½. La commande ne sera pas mise ï¿½ jour.");
			} else {
				ligneCommandeTemp.setQuantite(quantiteNouvelle);
				ligneCommandeTemp.setPrix(prixNouveau);
			}
		} else {
			System.out.println("Aucune ligne associï¿½ ï¿½ ce produit on peut en crï¿½er une nouvelle.");

			if (produitDemande.getQuantite() < quantite) {
				System.out.println("Trop de produit commandï¿½. On ne peut pas ajouter le produit");
			} else {
				ligneCommandeTemp.setProduit(produitDemande);
				ligneCommandeTemp.setPrix(quantite * produitDemande.getPrix());
				ligneCommandeTemp.setQuantite(quantite);
				listeLigneCommande.add(ligneCommandeTemp);
				panierSession.setListeLignesCommande(listeLigneCommande);
				System.out.println("Vous achetez" + ligneCommandeTemp.getQuantite() + "unitï¿½ pour un prix total de"
						+ ligneCommandeTemp.getPrix());
			}

		}

		// Ajout du panier dans la session
		session.setAttribute("panier", panierSession);
		// Lister l'ensembles des produits
		List<Categorie> listeCategorie = serviceCategorie.listerCategorie();
		
		model.addAttribute("listeCategorie", listeCategorie);
		return "accueil";
	}
	/**
	 * Mï¿½thode permettant d'afficher la page Panier ï¿½ savoir les deux formulaires de validation et le contenu du panier.
	 * 
	 * @param session Session Http contenant le panier
	 * @return Renvoi la page panier contenant tableau et formulaires
	 */
	@RequestMapping(value = "/panier/affichagePanier")
	public ModelAndView affichagePanier(HttpSession session) {
		// Rï¿½cupï¿½ration du panier

		Panier panierSession = (Panier) session.getAttribute("panier");
		// panierSession.getListeLignesCommande().get(0).get
		ModelAndView modeleVue = new ModelAndView("panier", "clientAAjouter", new Client());
		modeleVue.addObject("clientDejaDansBase", new Client());
		modeleVue.addObject("panierAffiche", panierSession);
		modeleVue.addObject("ligneModifiee", new LigneCommande());
		return modeleVue;
	}
	/**
	 * Mï¿½thode permettant de vider l'intï¿½gralitï¿½ du panier.
	 * @param session Session Http contenant le panier
	 * @return Renvoi vers la page panier avec un panier affichï¿½ vide.
	 */
	@RequestMapping(value = "/panier/viderPanier")
	public ModelAndView viderPanier(HttpSession session) {
		// Crï¿½ation d'un nouveau panier ne contenant aucune ligne de commande
		Panier nouveauPanier = new Panier();
		List<LigneCommande> nouvelleListeLigneCommande = new ArrayList<>();
		nouveauPanier.setListeLignesCommande(nouvelleListeLigneCommande);

		// On ajoute le panier dans la session.
		session.setAttribute("panier", nouveauPanier);

		// On revient ï¿½ la page du panier
		ModelAndView modeleVue = new ModelAndView("panier", "clientAAjouter", new Client());
		modeleVue.addObject("clientDejaDansBase", new Client());
		modeleVue.addObject("panierAffiche", nouveauPanier);

		return modeleVue;
	}
	
	@RequestMapping(value="/panier/modifierProduitPanierViaFormulaire")
	public String modifierLigne(HttpSession session, Model model, @Valid @ModelAttribute("ligneModifiee") LigneCommande ligneMod, BindingResult bindingResult){
		
		Panier panier = (Panier) session.getAttribute("panier");
		
		List<LigneCommande> liste = panier.getListeLignesCommande();
		if(bindingResult.hasErrors()){
			model.addAttribute("message", "Pas de quantites nÃ©gatives!");
		} else {
			for(LigneCommande ligne :liste){
				if (ligneMod.getIdLigne()==ligne.getIdLigne()){
					ligne.setQuantite(ligneMod.getQuantite());
					ligne.setPrix(ligne.getProduit().getPrix()*ligne.getQuantite());
				}
			}
		}

		
		session.setAttribute("panier", panier);
		model.addAttribute("clientAAjouter", new Client());
		model.addAttribute("clientDejaDansBase", new Client());
		model.addAttribute("panierAffiche", panier);
		model.addAttribute("ligneModifiee", new LigneCommande());
		

		return "panier";
	}
	
	/**
	 * 
	 * Permet de retirer une ligne de commande du panier.
	 * @param session Session Http contenant le panier
	 * @param idSuppression : id du produit ï¿½ retirer du panier
	 * @return Renvoi vers la page panier avec un affichage actualisï¿½ du panier
	 */
	@RequestMapping(value = "/panier/retirerPanier", method=RequestMethod.GET)
	public String retireProduitPanier(Model model, HttpSession session, @RequestParam("pIdSuppression") long idSuppression) {
		LigneCommande ligneASupprimer = new LigneCommande();
		Panier panierSession = (Panier) session.getAttribute("panier");
		boolean ligneTrouvee = false;
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();

		System.out.println(idSuppression);
		// On rï¿½cupï¿½re la ligne de commande
		for (LigneCommande ligne : listeLigneCommande) {
			if (ligne.getProduit().getIdProduit() == idSuppression) {
				System.out.println("Ligne trouvï¿½e");
				ligneASupprimer = ligne;
				ligneTrouvee = true;
			}
		}
		if (ligneTrouvee == true) {
			listeLigneCommande.remove(ligneASupprimer);
			panierSession.setListeLignesCommande(listeLigneCommande);

		}

		// On ajoute le panier dans la session
		session.setAttribute("panier", panierSession);
		model.addAttribute("panierAffiche", panierSession);
		model.addAttribute("clientAAjouter",new Client());
		model.addAttribute("clientDejaDansBase", new Client());
		
		return "panier";
	}

	/**
	 * Permet à un client pas encore enregistré de valider sa commande et de s'enregistrer.
	 * Un client ne peut s'enregistrer (et sa commande être validée) que si aucun autre client ne possède la même adresse mail.
	 * On génère ensuite le mot de passe de ce client et on l'enregistre. On enregistre ensuite la commande
	 *  et le contenu de celle-ci via les lignes de commande.
	 * @param session Session Http contenant le panier
	 * @param client client qui doit être enregistré.
	 * @return Page facture.
	 */
	@RequestMapping(value = "/panier/validationCommandePuisEnregistrement", method = RequestMethod.POST)
	public ModelAndView enregistreClient(HttpSession session, @ModelAttribute("clientAAjouter") Client client) {
		// Vï¿½rification de l'existence du client.
		boolean clientAjoutable = false;
		Panier panierSession = (Panier) session.getAttribute("panier");

		List<Client> listeClientParMail = serviceClient.rechercheClientMail(client);
		List<Client> listeClient = serviceClient.listerClient();

		System.out.println("Liste de tous les clients"+listeClient);
		System.out.println("Liste des clients par mail"+listeClientParMail);
		if (listeClientParMail.isEmpty()) {
			clientAjoutable = true;
		}

		System.out.println(clientAjoutable);
		if (listeClient.isEmpty()) {
			clientAjoutable = true;
		}
		System.out.println(clientAjoutable);
		if (clientAjoutable == false) {
			ModelAndView modeleVue = new ModelAndView("panier", "clientAAjouter", new Client());
			modeleVue.addObject("clientDejaDansBase", new Client());
			modeleVue.addObject("panierAffiche", panierSession);
			modeleVue.addObject("messageErreur", "Adresse mail dï¿½jï¿½ existante");
			return modeleVue;

		}

		// if(listeClientParMail==null && listeClient!=null){
		// }else {
		// ModelAndView modeleVue = new
		// ModelAndView("panier","clientAAjouter",new Client());
		// modeleVue.addObject("clientDejaDansBase", new Client());
		// modeleVue.addObject("panierAffiche", panierSession);
		// modeleVue.addObject("messageErreur","Adresse mail dï¿½jï¿½ existante");
		// return modeleVue;
		// }

		double prix = 0;

		// Gï¿½nï¿½ration du code Perso.
		String caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder code = new StringBuilder();
		int max = caracteres.length() - 1;
		for (int i = 1; i < 10; i++) {
			int emplacementCarac = (int) Math.floor(max * Math.random());
			char caracTemp = caracteres.charAt(emplacementCarac);
			code.append(caracTemp);
		}
		client.setCodePerso(code.toString());
		Client clientEnregistre = serviceClient.createClient(client);

		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		// Calcul du prix total
		for (LigneCommande ligne : listeLigneCommande) {
			prix = prix + ligne.getPrix();
		}

		// Gestion du stock.
		for (LigneCommande ligne : listeLigneCommande) {
			Produit produitModifStocke = ligne.getProduit();
			int quantiteApres = produitModifStocke.getQuantite() - ligne.getQuantite();
			produitModifStocke.setQuantite(quantiteApres);
			serviceProduit.modifierProduit(produitModifStocke);
		}

		// Enregistrement de la commande.
		Commande commande = new Commande();
		Date dateCommande = new Date();
		commande.setDateCommande(dateCommande);
		commande.setClient(clientEnregistre);
		commande.setListeLigneCommande(listeLigneCommande);

		commande = serviceCommande.enregistrementCommande(commande);

		// Enregistrement des lignes de commandes

		for (LigneCommande ligne : listeLigneCommande) {

			ligne.setCommande(commande);
			// Enregistrement proprement dit
			serviceLigneCommande.enregistrerLigneCommande(ligne);
		}

		return new ModelAndView("commandeValide", "prix", prix);
	}
	/**
	 * Validation de la commande pour un client dï¿½jï¿½ enregistrï¿½. On vï¿½rifie que l'adresse mail est bien enregistrï¿½ et on passe 
	 * ï¿½ l'enregistrement de la commande.
	 * @param session Session Http contenant le panier
	 * @param client Client qui cherche ï¿½ valider sa commande
	 * @return Page Facture
	 */
	@RequestMapping(value = "panier/validationCommandeClientDansBase", method = RequestMethod.POST)
	public ModelAndView commandeAncienClient(HttpSession session, @ModelAttribute("clientDejaDansBase") Client client) {
		double prix = 0;
		Panier panierSession = (Panier) session.getAttribute("panier");
		System.out.println(client);
		Client clientTrouve = serviceClient.getClientByInfo(client);
		System.out.println(clientTrouve);

		if (clientTrouve == null) {
			System.out.println("On reste sur la page panier");
			ModelAndView modeleVue = new ModelAndView("panier", "clientAAjouter", new Client());
			modeleVue.addObject("clientDejaDansBase", new Client());
			modeleVue.addObject("panierAffiche", panierSession);
			modeleVue.addObject("messageErreur", "Aucun client trouvé");
			return modeleVue;

		}

		// Enregistrement de la commande
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		// Calcul du prix total
		for (LigneCommande ligne : listeLigneCommande) {
			prix = prix + ligne.getPrix();
		}

		// Gestion du stock.
		for (LigneCommande ligne : listeLigneCommande) {
			Produit produitModifStocke = ligne.getProduit();
			int quantiteApres = produitModifStocke.getQuantite() - ligne.getQuantite();
			produitModifStocke.setQuantite(quantiteApres);
			serviceProduit.modifierProduit(produitModifStocke);
		}

		// Enregistrement de la commande.
		Commande commande = new Commande();
		Date dateCommande = new Date();
		commande.setDateCommande(dateCommande);
		commande.setClient(clientTrouve);
		commande.setListeLigneCommande(listeLigneCommande);

		commande = serviceCommande.enregistrementCommande(commande);

		// Enregistrement des lignes de commandes

		for (LigneCommande ligne : listeLigneCommande) {

			ligne.setCommande(commande);
			// Enregistrement proprement dit
			serviceLigneCommande.enregistrerLigneCommande(ligne);
		}

		return new ModelAndView("commandeValide");
	}
	/**
	 * Permet d'etablir la facture en PDF.
	 * @param session Session Http contenant le panier
	 * @return On reste sur la meme page.
	 */
	@RequestMapping(value = "/panier/facturePDF")
	public ModelAndView facturePDF(HttpSession session) {
		double prixTotal = 0;
		Document document = new Document();

		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(document,
					new FileOutputStream(System.getProperty("user.home") + "\\Desktop\\factureEcommerce.pdf"));
			document.open();
			document.add(new Paragraph("Facture détaillée de la commande\n\n"));
			// Ecrire la facture dans le pdf.
			// Entï¿½te du tableau
			String[] enteteTableau = { "Produit", "Description", "Quantitée", "Prix" };
			PdfPTable table = new PdfPTable(enteteTableau.length);
			// Crï¿½ation de l'entete du tableau
			for (String caseEntete : enteteTableau) {
				Paragraph celluleEnteteTemp = new Paragraph();
				celluleEnteteTemp.add(caseEntete);
				table.addCell(celluleEnteteTemp);
			}
			// On ajoute les lignes de commandes.
			for (LigneCommande ligne : listeLigneCommande) {
				prixTotal = prixTotal + ligne.getProduit().getPrix();
				Object[] ligneFacture = { ligne.getProduit().getDesignation(), ligne.getProduit().getDescription(),
						ligne.getQuantite(), ligne.getPrix() };

				for (Object objet : ligneFacture) {
					Paragraph paragrapheTemp = new Paragraph();
					paragrapheTemp.add(objet.toString());
					table.addCell(paragrapheTemp);
				}

			}
			document.add(table);
			Paragraph paraPrixTotal = new Paragraph("\n\nPrix Total : " + prixTotal + " €");
			document.add(paraPrixTotal);

			document.close();
			try {
				Desktop.getDesktop().open(new File(System.getProperty("user.home") + "\\Desktop\\factureEcommerce.pdf"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
		return new ModelAndView("commandeValide", "prix", prixTotal);
	}
	
	
	/**
	 * Envoi d'un mail au client. Facture PDF jointe.
	 * @param sessionHttp Session Http contenant le panier
	 * @return On reste sur la page facture.
	 */
	@RequestMapping(value = "/panier/envoiMail", method = RequestMethod.GET)
	public String envoyerMail(HttpSession sessionHttp) {
		final String to = "h.boizard@laposte.net";
		final String username = "thezadzad@gmail.com";
		final String password = "adaming44";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Commande Ecommerce");
			System.out.println("Sujet");
			// Message du mail
			StringBuilder sb = new StringBuilder();

			// String nom = "Boizard";
			// sb.append("Mme/Mr. " + nom + ",\n\n");
			sb.append("Cher client / Chère cliente" + "\n");
			sb.append("Vous avez passé une commande pour :\n");

			// On liste les produits dans le corps du mail.
			double prix = 0;
			Panier panierSession = (Panier) sessionHttp.getAttribute("panier");
			List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
			for (LigneCommande ligne : listeLigneCommande) {
				String nomProduit = ligne.getProduit().getDesignation();
				int quantite = ligne.getQuantite();
				prix = prix + ligne.getPrix();
				sb.append("  -  " + nomProduit + ":" + quantite + " exemplaire(s)" + "\n");
			}
			sb.append("Le montant total de vos achats s'élève à " + prix + " euros");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, 12);
			sb.append("\nLa date de réception est prévue au " + calendar.getTime());
			sb.append("Une facture plus détaillée se trouve jointe à ce mail.");
			message.setText(sb.toString());
			System.out.println("Corps de texte");
			
			DataSource pieceJointe = new FileDataSource(System.getProperty("user.home") + "\\Desktop\\factureEcommerce.pdf");
			message.setDataHandler(new DataHandler(pieceJointe));
			message.setFileName("recapitulatif_commande.pdf");
			
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return "commandeValide";
	}
	
	@RequestMapping(value="/retourAccueil")
	private ModelAndView retourAccueil(HttpSession session){
		Panier panierSession =(Panier) session.getAttribute("panier");
		panierSession.setListeLignesCommande(new ArrayList<>());
		session.setAttribute("panier", panierSession);
		
		List<Produit> listeProduit = serviceProduit.listerProduits();
		List<Categorie> listeCategorie = serviceCategorie.listerCategorie();

		ModelAndView modeleVue = new ModelAndView("accueil","listeProduit",listeProduit);
		modeleVue.addObject("listeCategorie",listeCategorie);
		return modeleVue;
	}
}
