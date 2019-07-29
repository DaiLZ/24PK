package priv.for24.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.gson.Gson;

import priv.for24.db.databean.JQBean;
import priv.for24.db.databean.Question;
import priv.for24.service.QuestionService;
//import test.com.a24.Service.QService;

public class MatchListener implements ServletContextListener {

    private MyThread myThread;
    
    List<Socket> mClientList = new ArrayList<Socket>();
	List<String> nameList= new ArrayList<String>();
	static ExecutorService mExecutors = null; // 线程池对象
	ServerSocket serverSocket = null;
	BufferedReader in;
	
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        if (myThread!=null&&myThread.isInterrupted()) {
            myThread.interrupt();
        }
    }
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        String str =null;
        if (str==null&&myThread==null) {
            myThread=new MyThread();
            myThread.start();//servlet上下文初始化时启动socket
        }
    }
  // 自定义一个Class线程类继承自线程类，重写run()方法，用于从后台获取处理数据
 
    class MyThread extends Thread{
        public void run() {
            while (!this.isInterrupted()) {//线程未中断执行循环
        		try {
        			serverSocket = new ServerSocket(5000);
        			mExecutors = Executors.newCachedThreadPool(); // 创建线程池
        			System.out.println("服务器已启动，等待客户端连接...");
        			Socket client = null;
        			Socket client1 = null; //如果有 已经在排队的
        			String name1 = null;
        			while (true) {
        				 // 没有连接这个方法就一直堵塞  
        				client = serverSocket.accept();
        				System.out.println("1目前在线:"+ mClientList.size());
        				in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
//        				client.shutdownInput();
        				String name = in.readLine();
        				System.out.println(name); 
        				//当有客户端在等待时 随机取一个出来 与刚来用户匹配  
        				System.out.println("1目前在线:"+ name);
        				if(mClientList.size()>0) {
        					int index=(int)(Math.random()* mClientList.size());
        					client1=mClientList.get(index);//随机取
        					name1=nameList.get(index);
        					if(!name.equals(name1)) { //不能和自己pk
        						mClientList.remove(client1);//从等待队列里移除
            					nameList.remove(name1);
            					mExecutors.execute(new QService(client,client1,name,name1)); //启动一个线程，为两个客户端发送题目
            					System.out.println("2目前在线:"+ mClientList.size());
        					}
        					
        				}else { //否则放入等待队列
        					mClientList.add(client);
        					nameList.add(name);
        					System.out.println("3目前在线:"+ mClientList.size());
        				}
        			}
        		} catch (IOException e) {
        			e.printStackTrace();
        		}

            }
        }
        
        class QService implements Runnable {
    		private Socket socket1;
    		private Socket socket2;
    		private String name1;
    		private String name2;

    		public QService(Socket socket1,Socket socket2,String name1,String name2) {
    			this.socket1 = socket1;
    			this.socket2 = socket2;
    			this.name1=name1;
    			this.name2=name2;
    		}
  
    		@Override
    		public void run() {
    			OutputStream os1 = null;
    			OutputStream os2 = null;
    			try {
    				//发送题目
    				QuestionService serv = new QuestionService();
    				ArrayList<Question> getQs = serv.GetQuestion();
    				JQBean JQBean1 = new JQBean();
    				JQBean JQBean2 = new JQBean();
    				JQBean1.setQbeans(getQs);
    				JQBean2.setQbeans(getQs);
    				JQBean1.setMatchname(name2);//交换用户名 实现匹配
    				JQBean2.setMatchname(name1);
    				
    				Gson gson = new Gson();
    				String jsonObj1 = gson.toJson(JQBean1); 
    				String jsonObj2 = gson.toJson(JQBean2);
    				// 返回消息到客户端
    				os1 = socket1.getOutputStream();
    				os2 = socket2.getOutputStream();
    				os1.write(jsonObj1.getBytes());
    				os2.write(jsonObj2.getBytes());
    				System.out.println(jsonObj1);  //测试
    				System.out.println("已发送题目");
    				socket1.shutdownOutput();
    				socket2.shutdownOutput();
    				socket1.close();
    				socket2.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }

}