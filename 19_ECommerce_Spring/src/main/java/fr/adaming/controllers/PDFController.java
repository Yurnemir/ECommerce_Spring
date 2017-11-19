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
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;
import fr.adaming.service.IServiceCategorie;

@Controller
@RequestMapping("/admin")
public class PDFController {

	@Autowired
	private IServiceCategorie categorieService;
	
	@RequestMapping(value="/produit/pdf", method=RequestMethod.GET )
	public void afficherPDFCategories(Model model, HttpServletResponse response){
		List<Categorie> liste = categorieService.listerCategorie();
		List<Produit> listeProduits = new ArrayList<Produit>();
		//Enumeration<String> sessionAttribs = session.getAttributeNames();

		Document recap = new Document();
        try {

    		PdfWriter writer = PdfWriter.getInstance(recap, response.getOutputStream());
    		
    		DateFormat currentDate = new SimpleDateFormat("dd'/'MM'/'yyyy",Locale.FRANCE);
    		
	        Calendar date = Calendar.getInstance(TimeZone.getTimeZone("France/Paris"), Locale.FRANCE);
	        date.setTime(new Date());
	        String dateString = date.DATE+"/"+date.MONTH+"/"+date.YEAR;
	        
	        recap.open();
	        recap.add(new Paragraph("Liste des categories et produits le "+currentDate.format(new Date())));//calendar
	        
	        
	        recap.add(new Phrase("Format : \n- Categorie\n    Produit (Stock)\n\n"));
	        com.itextpdf.text.List recapList = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
	        for(Categorie categorie:liste){
	        	StringBuilder ligne = new StringBuilder(categorie.getNomCategorie());//categorie.toString();
	        	listeProduits = categorie.getListeProduits();
	        	for(Produit produit:listeProduits){
	        		ligne.append("\n  "+produit.getDesignation()+" ("+produit.getQuantite()+")");
	        	}
	        	
		        recapList.add(ligne.toString());
	        }
	        recap.add(recapList);
	        
//	        recap.add(new Paragraph("Liste des attributs du modele"));
//	        com.itextpdf.text.List modelList = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
//	        for(String key : model.asMap().keySet()){
//	        	modelList.add(model.asMap().get(key).toString());
//	        }
//	        recap.add(modelList);
//	        
//	        recap.add(new Paragraph("Liste des attributs de la session"));
//	        com.itextpdf.text.List sessionList = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
//	        for(String key : model.asMap().keySet()){
//	        	sessionList.add(model.asMap().get(key).toString());
//	        }
//	        recap.add(sessionList);
	        
		} catch (DocumentException|IOException e) {
			e.printStackTrace();
		} finally{
			recap.close();
		}
		
		response.setHeader("Content-Disposition", "attachement; filename=categories.pdf"); // nom du fichier fixé par la valeur de requestMapping value...
	}
	
}
