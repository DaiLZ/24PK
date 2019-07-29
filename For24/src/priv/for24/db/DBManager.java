package priv.for24.db;

import java.sql.*;

public class DBManager {
	
    //jdbc:mysql:// ��ָJDBC���ӷ�ʽ         3306 SQL���ݿ�Ķ˿ں�
	public static final String URL = "jdbc:mysql://localhost:3306/for24";
    public static final String User = "root";
    public static final String Psd = "dlz1203#";

    // ��̬��Ա��֧�ֵ�̬ģʽ
    private static DBManager per = null;
    private Connection conn = null; //���ݿ����Ӷ���
    private Statement stmt = null; //����ִ�о�̬������������SQL��䲢�������ɽ���Ķ���


    public static DBManager createInstance() {
    	//��֤������Դ����Ψһ�Ի�
        if (per == null) {
            per = new DBManager();
            per.initDB();
        }
        return per;
    }

    // �������� �Ա�java���������ݿ�����
    public void initDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("��������...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // �������ݿ⣬��ȡ���+����
    public void connectDB() {
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(URL, User, Psd);
            stmt = conn.createStatement();
            System.out.println("SqlManager:Connect to database successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("SqlManager:Connect to database successful.");
    }

    // �ر����ݿ�Ͷ����ͷž��
    public void closeDB() {
        System.out.println("Close connection to database..");
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Close connection successful");
    }
    
    /**
     * .��ѯ
     * executeQuery ֻ�ܲ�ѯ�����ش����ѯ�����ResultSet����
     */
    public ResultSet executeQuery(String sql) {
    	ResultSet rs = null;
        System.out.println("executeQuery == "+sql);
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("e.printStackTrace   "+e.toString());
        }
        return rs;
    }
   
    /**
     * .��ɾ��
     * executeUpdate ����int��ָʾ��Ӱ������
     */
    public int executeUpdate(String sql) {
        int ret = 0;
        try {
            ret = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}