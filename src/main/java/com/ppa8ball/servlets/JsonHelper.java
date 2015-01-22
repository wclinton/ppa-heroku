package com.ppa8ball.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper
{

	public static void ReturnJson(HttpServletResponse response, Object obj) 
	{
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
		//Gson gson = new Gson();
		String json = gson.toJson(obj);
		
		response.setContentType("text/javascript");
		
		PrintWriter writer;
		try
		{
			writer = response.getWriter();
		
		
		writer.write(json);

		//create the JSON string, I suggest using some framework.

		writer.flush();
		writer.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
