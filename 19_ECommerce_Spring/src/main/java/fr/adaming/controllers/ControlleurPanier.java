package fr.adaming.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/panier")
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
			System.out.println("Id de la ligne"+ligne.getProduit().getIdProduit() + "vs produit demandé" + idProduit);
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
		
		
		if(verifLigne==false){
			LigneCommande ligneCommande = new LigneCommande();
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
	
	@RequestMapping(value="/affichagePanier")
	public ModelAndView affichagePanier(HttpSession session){
		//Récupération du panier
		
		Panier panierSession = (Panier) session.getAttribute("panier");
		//panierSession.getListeLignesCommande().get(0).get
		return new ModelAndView("panier","panierAffiche",panierSession);
		
	}
	@RequestMapping(value="/facturePDF")
	public ModelAndView facturePDF(HttpSession session){
		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		Document document = new Document();
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
				Object[] ligneFacture ={ligne.getProduit().getDesignation(),ligne.getProduit().getDescription(),ligne.getQuantite(),ligne.getPrix()};
				for (Object objet:ligneFacture){
					Paragraph paragrapheTemp = new Paragraph();
					paragrapheTemp.add(objet.toString());
					table.addCell(paragrapheTemp);
				}
				
			}
			
			document.add(table);
			
			document.close();
		
		} catch (FileNotFoundException | DocumentException e) {

			e.printStackTrace();
		}
		
		return new ModelAndView("panier","panierAffiche",panierSession);

	}
}
