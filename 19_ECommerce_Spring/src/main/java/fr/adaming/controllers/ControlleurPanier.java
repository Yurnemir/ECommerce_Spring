package fr.adaming.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.cfg.CreateKeySecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;
@Controller
//@RequestMapping("/panier")
public class ControlleurPanier {

	@Autowired
	private IProduitService serviceProduit;

	
	@RequestMapping(value="/ajoutViaLien")
	public ModelAndView ajoutPanierParLigne(Model modele ,@RequestParam("pIdProduit")int idProduit, HttpSession session){
		Produit produitTemp = new Produit();
		produitTemp.setIdProduit(idProduit);
		LigneCommande ligneAModifier = new LigneCommande();
		boolean verifLigne =false;;
		
		Produit produitDemande = serviceProduit.rechercherProduitAvecId(produitTemp);
		
		//On récupère le panier de la session.
		
		//System.out.println(produitDemande);
		//On récupère le panier depuis le modele.
		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		for(LigneCommande ligne:listeLigneCommande){
			System.out.println("Id du produit dans de la ligne"+ligne.getProduit().getIdProduit() + "vs produit demandé" + idProduit);
			if(ligne.getProduit().getIdProduit()==idProduit){
				System.out.println("Produit déjà dans la commande il faut modifier la ligne");
				verifLigne = true;
				// Si la ligne existe déjà on la récupère
				ligneAModifier = ligne;
				break;
			}else {
				System.out.println("Le produit n'existe pas on peut donc créer une ligne de commande et l'ajouter dans le panier");
				verifLigne=false;
				
			}

		}
		
		if(verifLigne == true){
			
			System.out.println("On paye initialement "+ligneAModifier.getPrix()+"pour"+ligneAModifier.getQuantite()+"unités");

			double nouveauPrix = ligneAModifier.getPrix()+produitDemande.getPrix();
			int nouvelleQuantite = ligneAModifier.getQuantite() + 1;
			if(produitDemande.getQuantite()<nouvelleQuantite){
				System.out.println("Trop de produit demandé");
				System.out.println("La ligne de commande pour ce produit ne sera pas modifiée");
			}else {
				ligneAModifier.setPrix(nouveauPrix);
				ligneAModifier.setQuantite(nouvelleQuantite);
				System.out.println("On paye désormais "+ligneAModifier.getPrix()+"pour"+ligneAModifier.getQuantite()+"unités");

			}

		}
		
		//Ligne de commande à ajouter
		if(verifLigne==false){
			LigneCommande ligneCommande = new LigneCommande();
			//System.out.println(listeLigneCommande.size());
			//ligneCommande.setPositionTableau(listeLigneCommande.size()-1);
			//ligneCommande.setIdLigne(liste);
			ligneCommande.setProduit(produitDemande);
			ligneCommande.setQuantite(1);
			ligneCommande.setPrix(ligneCommande.getQuantite()*produitDemande.getPrix());
			listeLigneCommande.add(ligneCommande);
			panierSession.setListeLignesCommande(listeLigneCommande);
		}
		
		System.out.println(panierSession.getListeLignesCommande().size());
		 
		//Ajoute le panier à la session
		session.setAttribute("panier", panierSession);
		List<Produit> listeProduit = serviceProduit.listerProduits();
		
		return new ModelAndView("accueil","listeProduit",listeProduit);
	}
	
	//Panier avec formulaire
	@RequestMapping(value="/ajouterProduitPanierViaFormulaire", method=RequestMethod.GET)
	public String ajoutPanierParLigneAvecForm(Model model,@RequestParam("identifiantProduit") int idProduit,@RequestParam("quantite") int quantite, HttpSession session){
		boolean verifExistenceLigne =false;
		LigneCommande ligneCommandeTemp = new LigneCommande();
		System.out.println(idProduit + " " + quantite);
		//Récupérer le produit
		Produit produitTemp = new Produit();
		produitTemp.setIdProduit(idProduit);
		Produit produitDemande = serviceProduit.rechercherProduitAvecId(produitTemp);
		
		//Récupérer le panier depuis la session et la liste des lignes 
		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		for(LigneCommande ligne:listeLigneCommande){
			int i ;
			System.out.println("Id du produit actuel" +ligne.getProduit().getIdProduit()+"vs celui demandé"+idProduit);
			if(ligne.getProduit().getIdProduit()==idProduit){
				verifExistenceLigne = true;
				ligneCommandeTemp = ligne;

				break;
			}else{
				verifExistenceLigne = false;
			}
		}
		
		if(verifExistenceLigne==true){
			System.out.println("Ce produit a déjà une ligne. Il faut actualiser la ligne");
			int quantiteNouvelle = ligneCommandeTemp.getQuantite()+quantite;
			double prixNouveau = ligneCommandeTemp.getPrix()+produitDemande.getPrix()*quantite;
			if(produitDemande.getQuantite()<quantiteNouvelle){
				System.out.println("Trop de produit commandé. La commande ne sera pas mise à jour.");
			}else{
				ligneCommandeTemp.setQuantite(quantiteNouvelle);
				ligneCommandeTemp.setPrix(prixNouveau);
			}
		}else{
			System.out.println("Aucune ligne associé à ce produit on peut en créer une nouvelle.");

			if(produitDemande.getQuantite()<quantite){
				System.out.println("Trop de produit commandé. On ne peut pas ajouter le produit");
			}else{
				ligneCommandeTemp.setProduit(produitDemande);
				ligneCommandeTemp.setPrix(quantite*produitDemande.getPrix());
				ligneCommandeTemp.setQuantite(quantite);
				listeLigneCommande.add(ligneCommandeTemp);
				panierSession.setListeLignesCommande(listeLigneCommande);
				System.out.println("Vous achetez" + ligneCommandeTemp.getQuantite()+"unité pour un prix total de" + ligneCommandeTemp.getPrix());
			}

		}
		
		
		
		//Ajout du panier dans la session
		session.setAttribute("panier", panierSession);
		//Lister l'ensembles des produits 
		List<Produit> listeProduit = serviceProduit.listerProduits();
		
		
		model.addAttribute("listeProduit", listeProduit);
		return "accueil";
	}
	
	
	
	@RequestMapping(value="/panier/affichagePanier")
	public ModelAndView affichagePanier(HttpSession session){
		//Récupération du panier
		
		Panier panierSession = (Panier) session.getAttribute("panier");
		//panierSession.getListeLignesCommande().get(0).get
		return new ModelAndView("panier","panierAffiche",panierSession);
		
	}
	@RequestMapping(value="/panier/viderPanier")
	public ModelAndView viderPanier(HttpSession session){
		// Création d'un nouveau panier ne contenant aucune ligne de commande
		Panier nouveauPanier = new Panier();
		List<LigneCommande> nouvelleListeLigneCommande = new ArrayList<>();
		nouveauPanier.setListeLignesCommande(nouvelleListeLigneCommande);
		
		//On ajoute le panier dans la session.
		session.setAttribute("panier", nouveauPanier);
		
		// On revient à la page du panier
		return new ModelAndView("panier","panierAffiche",nouveauPanier);
		//return "p";
	}
	
	@RequestMapping(value="/panier/retirerPanier")
	public ModelAndView retireProduitPanier(HttpSession session,@RequestParam("pIdSuppression") long idSuppression){
		LigneCommande ligneASupprimer = new LigneCommande();
		Panier panierSession = (Panier) session.getAttribute("panier");
		boolean ligneTrouvee = false;
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		
		System.out.println(idSuppression);
		//On récupère la ligne de commande
		for(LigneCommande ligne:listeLigneCommande){
			if(ligne.getProduit().getIdProduit()==idSuppression){
				System.out.println("Ligne trouvée");
				ligneASupprimer=ligne;
				ligneTrouvee = true;
			}
		}
		 if (ligneTrouvee ==true){
			 listeLigneCommande.remove(ligneASupprimer);
			panierSession.setListeLignesCommande(listeLigneCommande);

		 }

		//On ajoute le panier dans la session
		session.setAttribute("panier", panierSession);
		
		
		return new ModelAndView ("panier","panierAffiche",panierSession);
	}
	
	
	@RequestMapping(value="/panier/facturePDF")
	public ModelAndView facturePDF(HttpSession session){
		double prixTotal = 0;
		Document document = new Document();

		
		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		try {
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\inti0236\\Desktop\\Facture2.pdf"));
			document.open();
		
			//Ecrire la facture dans le pdf.
			// Entête du tableau
			String[] enteteTableau ={"Produit","Description","Quantité","Prix"};
			PdfPTable table = new PdfPTable(enteteTableau.length);
			//Création de l'entete du tableau
			for(String caseEntete : enteteTableau){
				Paragraph celluleEnteteTemp = new Paragraph();
				celluleEnteteTemp.add(caseEntete);
				table.addCell(celluleEnteteTemp);
			}
			//On ajoute les lignes de commandes.
			for(LigneCommande ligne : listeLigneCommande){
				prixTotal = prixTotal + ligne.getProduit().getPrix();
				Object[] ligneFacture ={ligne.getProduit().getDesignation(),ligne.getProduit().getDescription(),ligne.getQuantite(),ligne.getPrix()};
			
				for (Object objet:ligneFacture){
					Paragraph paragrapheTemp = new Paragraph();
					paragrapheTemp.add(objet.toString());
					table.addCell(paragrapheTemp);
				}
				
			}
			
			
			document.add(table);
			
			//Ajout du prix total.
			Paragraph paraPrixTotal = new Paragraph("Prix Total : " +prixTotal + "€");
			document.add(paraPrixTotal);
			
			document.close();
			//C:\Program Files (x86)\Adobe\Acrobat Reader DC\Reader\AcroRd32.exe
			//Ouvrir le fichier PDF.
			System.out.println("On essaye d'ouvrir le fichier PDF qui a été généré");
			try {
				Desktop.getDesktop().open(new File("C:\\Users\\inti0236\\Desktop\\Facture2.pdf"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException | DocumentException e) {

			e.printStackTrace();
		}
		
		return new ModelAndView("panier","panierAffiche",panierSession);

	}
}
