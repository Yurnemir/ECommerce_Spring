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

import org.hibernate.cfg.CreateKeySecondPass;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;
/**
 * 
 * @author inti0236
 *Cette classe est le controlleur de toutes les opérations liées au panier.
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

	/**
	 * Méthode permettant d'ajouter exactement un exemplaire du produit en cliquant sur le lien. 
	 * Des vérifications sont effectuées permettant de ne garder qu'un seule Ligne de commande par produit et 
	 * de s'assurer que l'on peut pas commander plus que le stock disponible.
	 * @param idProduit : id du produit dans la base de données.Permet de récupérer
	 *  toutes les infos de celui-ci grâce à une méthode de DAO
	 * @param session Session HTTP (contient notamment le panier)
	 * @return ModelAndView de la page principale contient le tableau des produits
	 */
	@RequestMapping(value = "/ajoutViaLien")
	public ModelAndView ajoutPanierParLigne( @RequestParam("pIdProduit") int idProduit,
			HttpSession session) {
		Produit produitTemp = new Produit();
		produitTemp.setIdProduit(idProduit);
		LigneCommande ligneAModifier = new LigneCommande();
		boolean verifLigne = false;
		;

		Produit produitDemande = serviceProduit.rechercherProduitAvecId(produitTemp);

		// On récupère le panier de la session.

		// System.out.println(produitDemande);
		// On récupère le panier depuis le modele.
		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		for (LigneCommande ligne : listeLigneCommande) {
			System.out.println("Id du produit dans de la ligne" + ligne.getProduit().getIdProduit()
					+ "vs produit demandé" + idProduit);
			if (ligne.getProduit().getIdProduit() == idProduit) {
				System.out.println("Produit déjà dans la commande il faut modifier la ligne");
				verifLigne = true;
				// Si la ligne existe déjà on la récupère
				ligneAModifier = ligne;
				break;
			} else {
				System.out.println(
						"Le produit n'existe pas on peut donc créer une ligne de commande et l'ajouter dans le panier");
				verifLigne = false;

			}

		}

		if (verifLigne == true) {

			System.out.println("On paye initialement " + ligneAModifier.getPrix() + "pour"
					+ ligneAModifier.getQuantite() + "unités");

			double nouveauPrix = ligneAModifier.getPrix() + produitDemande.getPrix();
			int nouvelleQuantite = ligneAModifier.getQuantite() + 1;
			if (produitDemande.getQuantite() < nouvelleQuantite) {
				System.out.println("Trop de produit demandé");
				System.out.println("La ligne de commande pour ce produit ne sera pas modifiée");
			} else {
				ligneAModifier.setPrix(nouveauPrix);
				ligneAModifier.setQuantite(nouvelleQuantite);
				System.out.println("On paye désormais " + ligneAModifier.getPrix() + "pour"
						+ ligneAModifier.getQuantite() + "unités");

			}

		}

		// Ligne de commande à ajouter
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

		// Ajoute le panier à la session
		session.setAttribute("panier", panierSession);
		List<Produit> listeProduit = serviceProduit.listerProduits();

		return new ModelAndView("accueil", "listeProduit", listeProduit);
	}

	/**
	 * Permet d'ajouter un nombre d'exemplaire quelconque de produit au panier.
	 * Des vérifications sont effectuées permettant de ne garder qu'un seule Ligne de commande par produit et 
	 * de s'assurer que l'on peut pas commander plus que le stock disponible.

	 * @param model : modele MVC de la page principale
	 * @param idProduit Id du produit demandé
	 * @param quantite Quantité demandée du produit
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
		// Récupérer le produit
		Produit produitTemp = new Produit();
		produitTemp.setIdProduit(idProduit);
		Produit produitDemande = serviceProduit.rechercherProduitAvecId(produitTemp);
		// Récupérer le panier depuis la session et la liste des lignes
		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		for (LigneCommande ligne : listeLigneCommande) {
			System.out.println(
					"Id du produit actuel" + ligne.getProduit().getIdProduit() + "vs celui demandé" + idProduit);
			if (ligne.getProduit().getIdProduit() == idProduit) {
				verifExistenceLigne = true;
				ligneCommandeTemp = ligne;

				break;
			} else {
				verifExistenceLigne = false;
			}
		}

		if (verifExistenceLigne == true) {
			System.out.println("Ce produit a déjà une ligne. Il faut actualiser la ligne");
			int quantiteNouvelle = ligneCommandeTemp.getQuantite() + quantite;
			double prixNouveau = ligneCommandeTemp.getPrix() + produitDemande.getPrix() * quantite;
			if (produitDemande.getQuantite() < quantiteNouvelle) {
				System.out.println("Trop de produit commandé. La commande ne sera pas mise à jour.");
			} else {
				ligneCommandeTemp.setQuantite(quantiteNouvelle);
				ligneCommandeTemp.setPrix(prixNouveau);
			}
		} else {
			System.out.println("Aucune ligne associé à ce produit on peut en créer une nouvelle.");

			if (produitDemande.getQuantite() < quantite) {
				System.out.println("Trop de produit commandé. On ne peut pas ajouter le produit");
			} else {
				ligneCommandeTemp.setProduit(produitDemande);
				ligneCommandeTemp.setPrix(quantite * produitDemande.getPrix());
				ligneCommandeTemp.setQuantite(quantite);
				listeLigneCommande.add(ligneCommandeTemp);
				panierSession.setListeLignesCommande(listeLigneCommande);
				System.out.println("Vous achetez" + ligneCommandeTemp.getQuantite() + "unité pour un prix total de"
						+ ligneCommandeTemp.getPrix());
			}

		}

		// Ajout du panier dans la session
		session.setAttribute("panier", panierSession);
		// Lister l'ensembles des produits
		List<Produit> listeProduit = serviceProduit.listerProduits();

		model.addAttribute("listeProduit", listeProduit);
		return "accueil";
	}
	/**
	 * Méthode permettant d'afficher la page Panier à savoir les deux formulaires de validation et le contenu du panier.
	 * 
	 * @param session Session Http contenant le panier
	 * @return Renvoi la page panier contenant tableau et formulaires
	 */
	@RequestMapping(value = "/panier/affichagePanier")
	public ModelAndView affichagePanier(HttpSession session) {
		// Récupération du panier

		Panier panierSession = (Panier) session.getAttribute("panier");
		// panierSession.getListeLignesCommande().get(0).get
		ModelAndView modeleVue = new ModelAndView("panier", "clientAAjouter", new Client());
		modeleVue.addObject("clientDejaDansBase", new Client());
		modeleVue.addObject("panierAffiche", panierSession);

		return modeleVue;
	}
	/**
	 * Méthode permettant de vider l'intégralité du panier.
	 * @param session Session Http contenant le panier
	 * @return Renvoi vers la page panier avec un panier affiché vide.
	 */
	@RequestMapping(value = "/panier/viderPanier")
	public ModelAndView viderPanier(HttpSession session) {
		// Création d'un nouveau panier ne contenant aucune ligne de commande
		Panier nouveauPanier = new Panier();
		List<LigneCommande> nouvelleListeLigneCommande = new ArrayList<>();
		nouveauPanier.setListeLignesCommande(nouvelleListeLigneCommande);

		// On ajoute le panier dans la session.
		session.setAttribute("panier", nouveauPanier);

		// On revient à la page du panier
		return new ModelAndView("panier", "panierAffiche", nouveauPanier);

	}
	/**
	 * 
	 * Permet de retirer une ligne de commande du panier.
	 * @param session Session Http contenant le panier
	 * @param idSuppression : id du produit à retirer du panier
	 * @return Renvoi vers la page panier avec un affichage actualisé du panier
	 */
	@RequestMapping(value = "/panier/retirerPanier")
	public ModelAndView retireProduitPanier(HttpSession session, @RequestParam("pIdSuppression") long idSuppression) {
		LigneCommande ligneASupprimer = new LigneCommande();
		Panier panierSession = (Panier) session.getAttribute("panier");
		boolean ligneTrouvee = false;
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();

		System.out.println(idSuppression);
		// On récupère la ligne de commande
		for (LigneCommande ligne : listeLigneCommande) {
			if (ligne.getProduit().getIdProduit() == idSuppression) {
				System.out.println("Ligne trouvée");
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

		return new ModelAndView("panier", "panierAffiche", panierSession);
	}

	/**
	 * Permet à un client pas encore enregistré de valider sa commande et de s'enregistrer.
	 * Un client ne peut s'enregistrer (et sa commande être validée) que si aucun autre client ne possède la même adresse mail.
	 * On génère ensuite le mot de passe de ce client et on l'enregistre. On enregistre ensuite la commande
	 *  et le contenu de celle-ci à traers les lignes de commande.
	 * @param session Session Http contenant le panier
	 * @param client client qui doit être enregistré.
	 * @return Page facture.
	 */
	@RequestMapping(value = "/panier/validationCommandePuisEnregistrement", method = RequestMethod.POST)
	public ModelAndView enregistreClient(HttpSession session, @ModelAttribute("clientAAjouter") Client client) {
		// Vérification de l'existence du client.
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
			modeleVue.addObject("messageErreur", "Adresse mail déjà existante");
			return modeleVue;

		}

		// if(listeClientParMail==null && listeClient!=null){
		// }else {
		// ModelAndView modeleVue = new
		// ModelAndView("panier","clientAAjouter",new Client());
		// modeleVue.addObject("clientDejaDansBase", new Client());
		// modeleVue.addObject("panierAffiche", panierSession);
		// modeleVue.addObject("messageErreur","Adresse mail déjà existante");
		// return modeleVue;
		// }

		double prix = 0;

		// Génération du code Perso.
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
	 * Validation de la commande pour un client déjà enregistré. On vérifie que l'adresse mail est bien enregistré et on passe 
	 * à l'enregistrement de la commande.
	 * @param session Session Http contenant le panier
	 * @param client Client qui cherche à valider sa commande
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
	 * Permet d'établir la facture en PDF.
	 * @param session Session Http contenant le panier
	 * @return On reste sur la même page.
	 */
	@RequestMapping(value = "/panier/facturePDF")
	public ModelAndView facturePDF(HttpSession session) {
		double prixTotal = 0;
		Document document = new Document();

		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(document,
					new FileOutputStream(System.getProperty("user.home") + "\\Desktop\\test.pdf"));
			document.open();

			// Ecrire la facture dans le pdf.
			// Entête du tableau
			String[] enteteTableau = { "Produit", "Description", "Quantité", "Prix" };
			PdfPTable table = new PdfPTable(enteteTableau.length);
			// Création de l'entete du tableau
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

			// Ajout du prix total.
			Paragraph paraPrixTotal = new Paragraph("Prix Total : " + prixTotal + "€");
			document.add(paraPrixTotal);

			document.close();
			// C:\Program Files (x86)\Adobe\Acrobat Reader
			// DC\Reader\AcroRd32.exe
			// Ouvrir le fichier PDF.
			System.out.println("On essaye d'ouvrir le fichier PDF qui a été généré");
			try {
				Desktop.getDesktop().open(new File(System.getProperty("user.home") + "\\Desktop\\test.pdf"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException | DocumentException e) {

			e.printStackTrace();
		}
		// ModelAndView modeleVue = new
		// ModelAndView("panier","panierAffiche",panierSession);
		// modeleVue.addObject("ClientAAjouter", new Client());
		return new ModelAndView("commandeValide", "prix", prixTotal);

	}
	
	/**
	 * Envoi d'un mail au client. Facture PDF jointe.
	 * @param sessionHttp Session Http contenant le panier
	 * @return On reste sur la page facture.
	 */
	@RequestMapping(value = "/panier/envoiMail", method = RequestMethod.GET)
	public String envoyerMail(HttpSession sessionHttp) {
		final String to = "benj.henry@free.fr";
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

			// Message du mail
			StringBuilder sb = new StringBuilder();

			// String nom = "Boizard";
			// sb.append("Mme/Mr. " + nom + ",\n\n");
			sb.append("Cher client/Chère cliente" + "\n");
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
			sb.append("Le montant total de vos achats s'élèvent à" + prix);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, 12);
			sb.append("\nLa date de réception est prévue au " + calendar.getTime());
			sb.append("Une facture plus détaillée se trouve jointe à ce mail.");
			message.setText(sb.toString());

			// Pdf en piece jointe
			// DataSource pieceJointe = new
			// FileDataSource(System.getProperty("user.home") +
			// "\\Desktop\\test.pdf");
			// message.setDataHandler(new DataHandler(pieceJointe));
			// message.setFileName("recapitulatif_commande.pdf");
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return "commandeValide";
	}
}
