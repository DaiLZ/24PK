package priv.for24.db;

public class Qtosql{
	public static void main(String[] args) throws Exception {
	int i,j,p,q,k,m,n;
	int[] a=new int[4];
	double[] b=new double[3];
	double[] c=new double[2];
	double d=0,proof=0.00000001;
	int count=0,count1=0;
	boolean flag = false; //前两个数使用的是+-则为true
	int flag1 = 0; //0表示(a?b)*/c 1:(a?b)+-c 2:c+-d  3:c*/d
	String string=null;
	String string1=null; //记录优先运算的后两个数
	String temp1=null;  //从第二个循环跳出 回到第一循环 保证string初值
	String temp2=null;  //从第三个循环跳出 回到第二循环 保证string初值
	while(count1<10) {
		for(i=0;i<4;i++)
		a[i]=(int)(Math.random()*10)+1;
	ko:
	for(i=0;i<3;i++){
		for(j=i+1;j<4;j++){
			ka:
			for(k=0;k<6;k++){
				string=null;string1=null;
				switch(k){
					case 0:b[0]=a[i]+a[j];string=a[i]+"+"+a[j];flag=true;break;
					case 1:b[0]=a[i]-a[j];string=a[i]+"-"+a[j];flag=true;break;
					case 2:b[0]=a[i]*a[j];string=a[i]+"*"+a[j];flag=false;break;
					case 3:if(a[j]==0)break ka;b[0]=(double)a[i]/a[j];string=a[i]+"/"+a[j];flag=false;break;
					case 4:b[0]=a[j]-a[i];string=a[j]+"-"+a[i];flag=true;break;
					case 5:if(a[i]==0)break ka;b[0]=(double)a[j]/a[i];string=a[j]+"/"+a[i];flag=false;break;
				}
				temp1=string;  //以便之后重置
				if(i>0){
					b[++count]=a[0];
					if(i>1){
						b[++count]=a[1];
					}
				}
				if(j-i>1){
					b[++count]=a[j-1];
					if(j-i>2){
						b[++count]=a[j-2];
					}
				}
				if(j<3){
					b[++count]=a[3];
					if(j<2){
						b[++count]=a[2];
					}
				}
				count=0;
				for(p=0;p<2;p++){
					for(q=p+1;q<3;q++){
						kb:
						for(m=0;m<6;m++){
							string=temp1;
							switch(m){
								case 0:
									c[0]=b[p]+b[q];
									if (p==0) { //b[0]是计算后的数  且必有q>p
										string=string+"+"+(int)b[q];
										flag1=1;
									}else {
										string1=(int)b[p]+"+"+(int)b[q];
										flag1=2;
									}break;
								case 1:
									c[0]=b[p]-b[q];
									if (p==0) {
										string=string+"-"+(int)b[q];
										flag1=1;
									}else {
										string1=(int)b[p]+"-"+(int)b[q];
										flag1=2;
									}break;
								case 2:
									c[0]=b[p]*b[q];
									if (p==0) {
										if (flag) {
											string="("+string+")*"+(int)b[q];
										}else {
											string=string+"*"+(int)b[q];
										}
										flag1=0;
									}else {
										string1=(int)b[p]+"*"+(int)b[q];
										flag1=3;
									}break;
								case 3:
									if(b[q]==0)break kb;
									c[0]=b[p]/b[q];
									if (p==0) {
										if (flag) {
											string="("+string+")/"+(int)b[q];
										}else {
											string=string+"/"+(int)b[q];
										}
										flag1=0;
									}else {
										string1=(int)b[p]+"/"+(int)b[q];
										flag1=3;
									}break;
								case 4:
									c[0]=b[q]-b[p];
									if (p==0) {  
										if(flag) {//减去一个 加减表达式需要带小括号
											string=(int)b[q]+"-("+string+")";
										}else {
											string=(int)b[q]+"-"+string;
										}
										flag1=1;
									}else {
										string1=(int)b[q]+"-"+(int)b[p];
										flag1=2;
									}break;
								case 5:
									if(b[p]==0)break kb;
									c[0]=b[q]/b[p];
									if (p==0) {
										if (flag) {
											string=(int)b[q]+"/("+string+")";
										}else {
											string=(int)b[q]+"/"+string;
										}
										flag1=0;
									}else {
										string1=(int)b[q]+"/"+(int)b[p];
										flag1=3;
									}break;
							}
							temp2=string;
							if(p>0) c[1]=b[0];
							if(q-p>1) c[1]=b[1];
							if(q==1) c[1]=b[2];
							for(n=0;n<6;n++){
								string=temp2;
								switch(n){ //如果使用string1 c[0]表示string1
									case 0:
										d=c[0]+c[1];
										if (flag1==0||flag1==1) {
											string+="+"+(int)c[1];
										}else {
											string+="+"+string1;
										}break;
									case 1:
										d=c[0]-c[1];
										if (flag1==0||flag1==1) {
											string+="-"+(int)c[1];
										}else if(flag1==2){
											string=string1+"-("+string+")";
										}else {
											string=string1+"-"+string;
										}break;
									case 2:
										d=c[0]*c[1];
										if (flag1==0) {
											string+="*"+(int)c[1];
										}else if (flag1==1) {
											string="("+string+")*"+(int)c[1];
										}else if (flag1==2) {
											if (flag) {
												string="("+string+")*("+string1+")";
											}else {
												string=string+"*("+string1+")";
											}
										}else {
											if (flag) {
												string="("+string+")*"+string1;
											}else {
												string=string+"*"+string1;
											}
										}break;
									case 3:
										if(c[1]==0)break;
										d=c[0]/c[1];
										if (flag1==0) {
											string+="/"+(int)c[1];
										}else if (flag1==1) {
											string="("+string+")/"+(int)c[1];
										}else if (flag1==2) {
											if (flag) {
												string="("+string+")/("+string1+")";
											}else {
												string=string1+"/("+string+")";
											}
										}else {
											if (flag) {
												string=string1+"/("+string+")";
											}else {
												string=string1+"/"+string;
											}
										}break;
									case 4:  //减法后面跟乘除 不需要小括号 ;跟-加减 需要
										d=c[1]-c[0];
										if (flag1==0) {
											if (flag) {
												string=(int)c[1]+"-("+string+")";
											}else {
												string=(int)c[1]+"-"+string;
											}
										}else if (flag1==1) {
											string=(int)c[1]+"-("+string+")";
										}else if(flag1==2){
											string=string+"-("+string1+")";
										}else {
											string=string+"-"+string1;
										}break;
									case 5: //除法后面的组合需要加小括号
										if(c[0]==0)break;
										d=c[1]/c[0];
										if (flag1==0||flag1==1) {
											string=(int)c[1]+"/("+string+")";
										}else if (flag1==2) {
											string="("+string+")/("+string1+")";
										}else {
											string=string+"/("+string1+")";
										}break;
								}
								if(d-24<proof&&d+proof>24) {
									break ko;
								}
							}
						}  
					}
				}
			} 
		}
	}
	if(d-24<proof&&d+proof>24) {
		System.out.println("Get!"); 
		insert(a[0],a[1],a[2],a[3],string);
		count1++;
	}
	else{
		System.out.println("No!"); 
	}
	}
	}
	 public static void insert(int num1,int num2,int num3,int num4,String answer) throws Exception{
	    	String qSql = "insert into question(num1,num2,num3,num4,answer) values('"
	                +num1+ "','" + num2 + "','" + num3 + "','" + num4 +  "','" + answer + "')";
			DBManager sql = DBManager.createInstance();
			sql.connectDB();
			int ret = sql.executeUpdate(qSql);
			sql.closeDB();
			if(ret > 0) {
	        System.out.println("插入成功！"+num1+","+num2+","+num3+","+num4+","+answer);
			}
	    }
}