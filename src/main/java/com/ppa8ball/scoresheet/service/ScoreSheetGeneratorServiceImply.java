package com.ppa8ball.scoresheet.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.ppa8ball.Scoresheet;
import com.ppa8ball.ScoresheetGenerator;

public class ScoreSheetGeneratorServiceImply implements ScoreSheetGenerator
{
	
	public void GenerateScoreSheet(HttpServletResponse resp, Scoresheet scoresheet) throws IOException
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ScoresheetGenerator.generateScoreSheet(baos, scoresheet);
			resp.setContentType("application/pdf");

			resp.setHeader("Content-disposition", "inline; filename='javatpoint.pdf'");
			resp.setContentLength(baos.size());
			// write ByteArrayOutputStream to the ServletOutputStream
			OutputStream os = resp.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (DocumentException e)
		{
			resp.setContentType("text/plain");
			resp.getWriter().println("Unable to generate spreadsheet.");
			return;
		}
	}
	
//	public OutputStream GenerateScoreSheetPdf(Scoresheet scoresheet)
//	{
//		
//	}
}
