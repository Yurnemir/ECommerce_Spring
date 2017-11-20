package fr.adaming.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;
import fr.adaming.service.IServiceCategorie;

@Controller
@RequestMapping("/admin")
public class PDFController {
	@Autowired
	private IServiceCategorie categorieService;
	@Autowired
	private IProduitService produitService;
	
	public void setCategorieService(IServiceCategorie categorieService) {
		this.categorieService = categorieService;
	}
	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}
	
	
	@RequestMapping(value="/produit/pdf/recap", method=RequestMethod.GET )
	public void afficherPDFCategories(Model model, HttpServletResponse response){
		Document recap = new Document();
        try {
    		@SuppressWarnings("unused")
			PdfWriter writer = PdfWriter.getInstance(recap, response.getOutputStream());
    		recap.open();
    		DateFormat currentDate = new SimpleDateFormat("dd'/'MM'/'yyyy",Locale.FRANCE);
	        recap.add(new Paragraph("Recapitulatif Général :"));
	        recap.add(new Paragraph("Date : " + currentDate.format(new Date())));
	        
	        recap.add(new Phrase("\nCategories :\n"));
	        List<Categorie> categories = categorieService.listerCategorie();
            PdfPTable tableCategories = new PdfPTable(3);
            tableCategories.addCell(new PdfPCell(new Paragraph("Id")));
            tableCategories.addCell(new PdfPCell(new Paragraph("Nom")));
            tableCategories.addCell(new PdfPCell(new Paragraph("Description")));
            for (Categorie categorie : categories) {
            	Integer id = categorie.getIdCategorie();
            	PdfPCell cell1 = new PdfPCell(new Paragraph(id.toString()));
            	PdfPCell cell2 = new PdfPCell(new Paragraph(categorie.getNomCategorie()));
            	PdfPCell cell3 = new PdfPCell(new Paragraph(categorie.getDescription()));
            	tableCategories.addCell(cell1);
            	tableCategories.addCell(cell2);
            	tableCategories.addCell(cell3);
            }
	        recap.add(tableCategories);

	        recap.add(new Phrase("\nProduits :\n"));
	        List<Produit> produits = produitService.listerProduits();
            PdfPTable tableProduits = new PdfPTable(6);
            tableProduits.addCell(new PdfPCell(new Paragraph("Id")));
            tableProduits.addCell(new PdfPCell(new Paragraph("Designation")));
            tableProduits.addCell(new PdfPCell(new Paragraph("Description")));
            tableProduits.addCell(new PdfPCell(new Paragraph("Prix")));
            tableProduits.addCell(new PdfPCell(new Paragraph("Quantite")));
            tableProduits.addCell(new PdfPCell(new Paragraph("Categorie")));
	        for (Produit produit : produits) {
	        	System.out.println(" => " + produit);
            	Integer id = produit.getIdProduit();
            	Double prix = produit.getPrix();
            	Integer quantite = produit.getQuantite();
            	PdfPCell cell1 = new PdfPCell(new Paragraph(id.toString()));
            	PdfPCell cell2 = new PdfPCell(new Paragraph(produit.getDesignation()));
            	PdfPCell cell3 = new PdfPCell(new Paragraph(produit.getDescription()));
            	PdfPCell cell4 = new PdfPCell(new Paragraph(prix.toString()));
            	PdfPCell cell5 = new PdfPCell(new Paragraph(quantite.toString()));
            	PdfPCell cell6 = new PdfPCell(new Paragraph(produit.getCategorie().getNomCategorie()));
            	tableProduits.addCell(cell1);
            	tableProduits.addCell(cell2);
            	tableProduits.addCell(cell3);
            	tableProduits.addCell(cell4);
            	tableProduits.addCell(cell5);
            	tableProduits.addCell(cell6);
			}
	        recap.add(tableProduits);
		} catch (DocumentException|IOException e) {
			e.printStackTrace();
		} finally{
			recap.close();
		}
		response.setHeader("Content-Disposition", "attachement; filename=categories.pdf");
	}
}
