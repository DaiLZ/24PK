package priv.for24.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.gson.Gson;

import priv.for24.db.databean.JGradeBean;
import priv.for24.db.databean.JResultBean;
import priv.for24.service.UpdateService;

public class AnswerListener implements ServletContextListener {

    private MyThread myThread;
    
    List<Socket> mClientList = new ArrayList<Socket>();
    List<JGradeBean> clientInfo=new ArrayList<JGradeBean>();
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
        			serverSocket = new ServerSocket(5001);
        			mExecutors = Executors.newCachedThreadPool(); // 创建线程池
        			System.out.println("服务器已启动，等待客户端提交成绩...");
        			Socket client = null;
        			Socket client1 = null; //如果有 已经在排队的
        			while (true) {
        				 // 没有连接这个方法就一直堵塞  
        				client = serverSocket.accept();
        				Gson  gson = new Gson();
        				in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
        				String info=in.readLine();
        				JGradeBean gradeBean= gson.fromJson(info,JGradeBean.class);
        				int i;
        				for (i = 0; i < mClientList.size(); i++) {
        					System.out.println(i);
        					String name=clientInfo.get(i).getUsername();
        					//新来用户 以匹配名找对手
        					if(name.equals(gradeBean.getMatchname()))
        					{
        						client1 = mClientList.get(i);
        						JGradeBean match=clientInfo.get(i);
        						mClientList.remove(client1);//从等待队列里移除
            					clientInfo.remove(match);
            					mExecutors.execute(new GradeService(client,client1,gradeBean,match)); //启动一个线程，为两个客户端判断输赢
            					System.out.println("2等成绩人数:"+ mClientList.size());
        						break;
        					}
        				}
        				System.out.println(i);
        				if (i==clientInfo.size()) {
							mClientList.add(client);
							clientInfo.add(gradeBean); 
							System.out.println("开始等待对手倒计时");
							mExecutors.execute(new timeoutService(client,gradeBean));
        					System.out.println("等成绩人数：:"+ mClientList.size());
						}
        			}
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
            }
        }
        
        class GradeService implements Runnable {
    		private Socket socket1;
    		private Socket socket2;
    		private JGradeBean user1;
    		private JGradeBean user2;
     
    		public GradeService(Socket socket1,Socket socket2,JGradeBean user1,JGradeBean user2) {
    			this.socket1 = socket1;
    			this.socket2 = socket2;
    			this.user1=user1;
    			this.user2=user2;
    		}
    		@Override
    		public void run() {
    			OutputStream os1 = null;
    			OutputStream os2 = null;
    			UpdateService service=new UpdateService();
    			int score=0;
    			JResultBean resultBean1=new JResultBean();
				resultBean1.setUsergrade(user1.getGrade());
				resultBean1.setMatchgrade(user2.getGrade());
				JResultBean resultBean2=new JResultBean();
				resultBean2.setUsergrade(user2.getGrade());
				resultBean2.setMatchgrade(user1.getGrade());
    			try {
    				if (user1.getGrade()>user2.getGrade()) {
    					score=2+(int)(user1.getGrade()-user2.getGrade())/10;
    					service.updatewin(user1.getUsername(), score);
    					service.updatelose(user2.getUsername());
    					
    					resultBean1.setScore(score);
    					resultBean2.setScore(-2);
					}else if (user1.getGrade()<user2.getGrade()) {
						score=2+(int)(user2.getGrade()-user1.getGrade())/10;
						service.updatewin(user2.getUsername(), score);
    					service.updatelose(user1.getUsername());
    					
    					resultBean1.setScore(-2);
    					resultBean2.setScore(score);
					}else { //平局
						service.updatetie(user1.getUsername());
    					service.updatetie(user2.getUsername());
    					
    					resultBean1.setScore(1);
    					resultBean2.setScore(1);
					}
    				service.updatehigh(user1.getUsername(), user1.getGrade());
    				service.updatehigh(user2.getUsername(), user2.getGrade());
    				Gson gson = new Gson();
    				String jsonObj1 = gson.toJson(resultBean1); //交换用户名 实现匹配
    				String jsonObj2 = gson.toJson(resultBean2);
    				os1 = socket1.getOutputStream();
    				os2 = socket2.getOutputStream();
    				os1.write(jsonObj1.getBytes());
    				os2.write(jsonObj2.getBytes());
    				System.out.println(jsonObj1);  //测试
    				System.out.println("已发送成绩");
    				socket1.shutdownOutput();
    				socket2.shutdownOutput();
    				socket1.close();
    				socket2.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
        class timeoutService implements Runnable {
    		private Socket socket1;
    		private JGradeBean user1;
     
    		public timeoutService(Socket socket1,JGradeBean user1) {
    			this.socket1 = socket1;
    			this.user1=user1;
    		}
    		@Override
    		public void run() {
    			Timer timer = new Timer();
    			System.out.println("等待时间："+ ((user1.getGrade()/100+1)*100+(10-(user1.getGrade()/100+1))*60- user1.getGrade())*1000);
    			timer.schedule(new TimerTask() {
    			    @Override
    			    public void run() {
    			    	if(socket1.isClosed()) {
    			    		System.out.println("socket已经关闭！");
    			    	}else {
    			    		timeout();
						}
    			    }
    			},((user1.getGrade()/100+1)*100+(10-(user1.getGrade()/100+1))*60- user1.getGrade())*1000);
    		
    		}
    		public void timeout() {
    			OutputStream os1 = null;
    			UpdateService service=new UpdateService();
    			JResultBean resultBean1=new JResultBean();
				resultBean1.setUsergrade(user1.getGrade());
				resultBean1.setMatchgrade(-1);
    			try {
					service.updatewin(user1.getUsername(), 2);	 //对手超时 赢两分				
					resultBean1.setScore(2);
    				service.updatehigh(user1.getUsername(), user1.getGrade());
    				
    				service.updatelose(user1.getMatchname());	 //-2超时				
					

    				Gson gson = new Gson();
    				String jsonObj1 = gson.toJson(resultBean1); 
    				os1 = socket1.getOutputStream();
    				os1.write(jsonObj1.getBytes());

    				System.out.println("已发送成绩");
    				socket1.shutdownOutput();
    				
    				mClientList.remove(socket1);//从等待队列里移除
					clientInfo.remove(user1);
					
    				socket1.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
}