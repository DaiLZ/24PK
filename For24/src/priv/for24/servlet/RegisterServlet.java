package priv.for24.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import priv.for24.service.StartService;

public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 接收客户端信息 okgo框架可以传递中文字符
		String username = request.getParameter("username");
//		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String password = request.getParameter("password");
		System.out.println(username + "--" + password);
		// 新建服务对象
		StartService serv = new StartService();
		Boolean register = serv.register(username, password);
		if (register) {
			System.out.println("注册成功！");
		} else {
			username=username+" not ok";//注册失败返回不一致的用户名
			System.out.println("注册失败！");
		}
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("username", username);
			System.out.println(jsonObj);
			// 返回信息到客户端
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			out.flush();out.close();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}