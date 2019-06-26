/**
 * 
 */
package fr.gda.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.gda.service.UtilExcel;

/**
 * 
 * Controleur qui intercepte les demande d affichage des pages des "vues
 * synthetiques"
 * 
 * @author Eloi
 *
 */

@WebServlet(urlPatterns = "/controller/export")
public class ExportExcel extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Integer numeroMois;
		Integer annee;
		Integer idDepartement;

		idDepartement = Integer.parseInt(req.getParameter("inputDepartement"));
		annee = Integer.parseInt(req.getParameter("inputAnnee"));
		numeroMois = Integer.parseInt(req.getParameter("inputMois"));

		UtilExcel utilexcel = new UtilExcel();

		XSSFWorkbook workbook = utilexcel.exportExcelServer(numeroMois, annee, idDepartement);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=ExportExcel.xlsx");
		try (OutputStream out = resp.getOutputStream()) {
			workbook.write(out);
			workbook.close();
		}
		resp.flushBuffer();
	}

}
