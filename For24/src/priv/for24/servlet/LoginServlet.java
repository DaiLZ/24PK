package priv.for24.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import priv.for24.db.databean.User;
import priv.for24.service.StartService;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
//		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		System.out.println("�ͻ��˲�����" + username + "  ======  " + password);
		// �½��������
		StartService serv = new StartService();
		Boolean login = serv.login(username, password);
		if (login) {// ��֤
			System.out.print("��¼�ɹ���");
		} else {
			username=username+" failed";//��¼ʧ�ܷ��ز�һ�µ��û���
			System.out.print("��¼ʧ�ܣ�");
		}
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("username", username);
			System.out.println(jsonObj);
			// ������Ϣ���ͻ���
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