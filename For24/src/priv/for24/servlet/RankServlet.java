package priv.for24.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import priv.for24.db.databean.JRankBean;
import priv.for24.db.databean.User_rank;
import priv.for24.service.RankService;

public class RankServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RankService serv = new RankService();
		ArrayList<User_rank> ranks =serv.Getrank();
		
		JRankBean jRankBean= new JRankBean();
		jRankBean.setRank(ranks);
		
		Gson gson = new Gson();
		String jsonObj = gson.toJson(jRankBean);
		
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
