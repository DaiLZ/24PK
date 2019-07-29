package priv.for24.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import priv.for24.db.databean.User;
import priv.for24.service.StartService;

public class GetuserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
//		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		System.out.println("客户端参数：" + username);
		StartService serv = new StartService();
		User userinfo = serv.GetUser(username);
		Gson gson = new Gson();
		String jsonObj = gson.toJson(userinfo);
		System.out.println(jsonObj);
		// 返回信息到客户端
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(jsonObj);
		out.flush();
		out.close();
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}