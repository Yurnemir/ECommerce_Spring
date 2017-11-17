package fr.adaming.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.modele.Categorie;
import fr.adaming.service.IServiceCategorie;

@Controller
@RequestMapping("/admin")
public class PDFController {

	@Autowired
	private IServiceCategorie categorieService;
	
	
	@RequestMapping(value="/categorie/recap/pdf", method=RequestMethod.GET )
	public void afficherPDFCategories(ModelMap model, HttpServletResponse response){	
		//get the categories
		List<Categorie> liste = categorieService.listerCategorie();

		//make the pdf file
		Document recap = new Document();
        // step 2
        try {

    		PdfWriter writer = PdfWriter.getInstance(recap, response.getOutputStream());
	        // step 3
	        recap.open();
	        recap.add(new Paragraph("Liste des categories le "+new Date()));//calendar
	        
	        com.itextpdf.text.List recapList = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
	        for(Categorie categorie:liste){
	        	String ligne = categorie.toString();
		        recapList.add(ligne);
	        }
	        recap.add(recapList);
		} catch (DocumentException|IOException e) {
			e.printStackTrace();
		} finally{
			recap.close();
		}
		
		response.setHeader("Content-Disposition", "attachement; filename=categories.pdf");
	}
	
}
