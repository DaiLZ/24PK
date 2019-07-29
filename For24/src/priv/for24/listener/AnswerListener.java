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
	static ExecutorService mExecutors = null; // �̳߳ض���
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
            myThread.start();//servlet�����ĳ�ʼ��ʱ����socket
        }
    }
  // �Զ���һ��Class�߳���̳����߳��࣬��дrun()���������ڴӺ�̨��ȡ��������
    class MyThread extends Thread{
        public void run() {
            while (!this.isInterrupted()) {//�߳�δ�ж�ִ��ѭ��
        		try {
        			serverSocket = new ServerSocket(5001);
        			mExecutors = Executors.newCachedThreadPool(); // �����̳߳�
        			System.out.println("���������������ȴ��ͻ����ύ�ɼ�...");
        			Socket client = null;
        			Socket client1 = null; //����� �Ѿ����Ŷӵ�
        			while (true) {
        				 // û���������������һֱ����  
        				client = serverSocket.accept();
        				Gson  gson = new Gson();
        				in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
        				String info=in.readLine();
        				JGradeBean gradeBean= gson.fromJson(info,JGradeBean.class);
        				int i;
        				for (i = 0; i < mClientList.size(); i++) {
        					System.out.println(i);
        					String name=clientInfo.get(i).getUsername();
        					//�����û� ��ƥ�����Ҷ���
        					if(name.equals(gradeBean.getMatchname()))
        					{
        						client1 = mClientList.get(i);
        						JGradeBean match=clientInfo.get(i);
        						mClientList.remove(client1);//�ӵȴ��������Ƴ�
            					clientInfo.remove(match);
            					mExecutors.execute(new GradeService(client,client1,gradeBean,match)); //����һ���̣߳�Ϊ�����ͻ����ж���Ӯ
            					System.out.println("2�ȳɼ�����:"+ mClientList.size());
        						break;
        					}
        				}
        				System.out.println(i);
        				if (i==clientInfo.size()) {
							mClientList.add(client);
							clientInfo.add(gradeBean); 
							System.out.println("��ʼ�ȴ����ֵ���ʱ");
							mExecutors.execute(new timeoutService(client,gradeBean));
        					System.out.println("�ȳɼ�������:"+ mClientList.size());
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
					}else { //ƽ��
						service.updatetie(user1.getUsername());
    					service.updatetie(user2.getUsername());
    					
    					resultBean1.setScore(1);
    					resultBean2.setScore(1);
					}
    				service.updatehigh(user1.getUsername(), user1.getGrade());
    				service.updatehigh(user2.getUsername(), user2.getGrade());
    				Gson gson = new Gson();
    				String jsonObj1 = gson.toJson(resultBean1); //�����û��� ʵ��ƥ��
    				String jsonObj2 = gson.toJson(resultBean2);
    				os1 = socket1.getOutputStream();
    				os2 = socket2.getOutputStream();
    				os1.write(jsonObj1.getBytes());
    				os2.write(jsonObj2.getBytes());
    				System.out.println(jsonObj1);  //����
    				System.out.println("�ѷ��ͳɼ�");
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
    			System.out.println("�ȴ�ʱ�䣺"+ ((user1.getGrade()/100+1)*100+(10-(user1.getGrade()/100+1))*60- user1.getGrade())*1000);
    			timer.schedule(new TimerTask() {
    			    @Override
    			    public void run() {
    			    	if(socket1.isClosed()) {
    			    		System.out.println("socket�Ѿ��رգ�");
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
					service.updatewin(user1.getUsername(), 2);	 //���ֳ�ʱ Ӯ����				
					resultBean1.setScore(2);
    				service.updatehigh(user1.getUsername(), user1.getGrade());
    				
    				service.updatelose(user1.getMatchname());	 //-2��ʱ				
					

    				Gson gson = new Gson();
    				String jsonObj1 = gson.toJson(resultBean1); 
    				os1 = socket1.getOutputStream();
    				os1.write(jsonObj1.getBytes());

    				System.out.println("�ѷ��ͳɼ�");
    				socket1.shutdownOutput();
    				
    				mClientList.remove(socket1);//�ӵȴ��������Ƴ�
					clientInfo.remove(user1);
					
    				socket1.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
}