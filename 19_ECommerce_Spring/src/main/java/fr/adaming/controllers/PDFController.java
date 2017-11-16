package fr.adaming.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.modele.Categorie;
import fr.adaming.service.IServiceCategorie;

@Controller
@RequestMapping("/admin")
public class PDFController {

	@Autowired
	private IServiceCategorie categorieService;
	
	
	@RequestMapping(value="/categorie/recap/pdf", method=RequestMethod.GET )
	public ModelAndView afficherPDFCategories(ModelMap model){
		
		//get the categories
		List<Categorie> liste = categorieService.listerCategorie();

		//make the pdf file
		Document document = new Document();
        // step 2
        try {
			PdfWriter.getInstance(document, new FileOutputStream("%HOME%\\test.pdf"));
	        // step 3
	        document.open();
	        // step 4
	        for(Categorie categorie : liste){
		        document.add(new Paragraph(categorie.toString()));
	        }
	        // step 5
	        document.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// put pdf file through modelAndView?
		
		
		return new ModelAndView("categorie_recap","categories",liste);
	}
	
}
