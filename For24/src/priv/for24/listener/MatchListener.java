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
        			serverSocket = new ServerSocket(5000);
        			mExecutors = Executors.newCachedThreadPool(); // �����̳߳�
        			System.out.println("���������������ȴ��ͻ�������...");
        			Socket client = null;
        			Socket client1 = null; //����� �Ѿ����Ŷӵ�
        			String name1 = null;
        			while (true) {
        				 // û���������������һֱ����  
        				client = serverSocket.accept();
        				System.out.println("1Ŀǰ����:"+ mClientList.size());
        				in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
//        				client.shutdownInput();
        				String name = in.readLine();
        				System.out.println(name); 
        				//���пͻ����ڵȴ�ʱ ���ȡһ������ ������û�ƥ��  
        				System.out.println("1Ŀǰ����:"+ name);
        				if(mClientList.size()>0) {
        					int index=(int)(Math.random()* mClientList.size());
        					client1=mClientList.get(index);//���ȡ
        					name1=nameList.get(index);
        					if(!name.equals(name1)) { //���ܺ��Լ�pk
        						mClientList.remove(client1);//�ӵȴ��������Ƴ�
            					nameList.remove(name1);
            					mExecutors.execute(new QService(client,client1,name,name1)); //����һ���̣߳�Ϊ�����ͻ��˷�����Ŀ
            					System.out.println("2Ŀǰ����:"+ mClientList.size());
        					}
        					
        				}else { //�������ȴ�����
        					mClientList.add(client);
        					nameList.add(name);
        					System.out.println("3Ŀǰ����:"+ mClientList.size());
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
    				//������Ŀ
    				QuestionService serv = new QuestionService();
    				ArrayList<Question> getQs = serv.GetQuestion();
    				JQBean JQBean1 = new JQBean();
    				JQBean JQBean2 = new JQBean();
    				JQBean1.setQbeans(getQs);
    				JQBean2.setQbeans(getQs);
    				JQBean1.setMatchname(name2);//�����û��� ʵ��ƥ��
    				JQBean2.setMatchname(name1);
    				
    				Gson gson = new Gson();
    				String jsonObj1 = gson.toJson(JQBean1); 
    				String jsonObj2 = gson.toJson(JQBean2);
    				// ������Ϣ���ͻ���
    				os1 = socket1.getOutputStream();
    				os2 = socket2.getOutputStream();
    				os1.write(jsonObj1.getBytes());
    				os2.write(jsonObj2.getBytes());
    				System.out.println(jsonObj1);  //����
    				System.out.println("�ѷ�����Ŀ");
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