package fr.adaming.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	//public ModelAndView afficherPDFCategories(ModelMap model, HttpServletResponse response){
	public void afficherPDFCategories(ModelMap model, HttpServletResponse response){	
		//response.setContentType("application/force-download");
		
		
		//get the categories
		List<Categorie> liste = categorieService.listerCategorie();

		//make the pdf file
		Document recap = new Document();
		//System.getProperties().getProperty("user.home");
        // step 2
        try {

    		PdfWriter writer = PdfWriter.getInstance(recap, response.getOutputStream());
	        // step 3
	        recap.open();
	        recap.add(new Paragraph("Let the categories BEGIN"));
	        // step 4
	        for(Categorie categorie : liste){
		        recap.add(new Paragraph(categorie.toString()));
	        }
		} catch (DocumentException|IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			recap.close();
		}
		
		response.setHeader("Content-Disposition", "attachement; filename=categories.pdf");
		// put pdf file through modelAndView?
		
		
		//return new ModelAndView("categorie_recap","categories",liste);
	}
	
}
