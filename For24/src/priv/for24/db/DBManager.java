package priv.for24.db;

import java.sql.*;

public class DBManager {
	
    //jdbc:mysql:// 是指JDBC连接方式         3306 SQL数据库的端口号
	public static final String URL = "jdbc:mysql://localhost:3306/for24";
    public static final String User = "root";
    public static final String Psd = "dlz1203#";

    // 静态成员，支持单态模式
    private static DBManager per = null;
    private Connection conn = null; //数据库连接对象
    private Statement stmt = null; //用于执行静态（不带参数）SQL语句并返回生成结果的对象。


    public static DBManager createInstance() {
    	//保证与数据源进行唯一对话
        if (per == null) {
            per = new DBManager();
            per.initDB();
        }
        return per;
    }

    // 加载驱动 以备java环境与数据库连接
    public void initDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("加载驱动...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 连接数据库，获取句柄+对象
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

    // 关闭数据库和对象，释放句柄
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
     * .查询
     * executeQuery 只能查询，返回代表查询结果的ResultSet对象
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
     * .增删改
     * executeUpdate 返回int，指示受影响行数
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