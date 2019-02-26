/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hala;

/**
 *
 * @author ANISH
 */
import java.util.*;
import java.io.*;

public class ZeroKnowledge
{
	int uid1;       //Present in Block
	int uid2;	//New input
	int g=2;
	int p=11;
	public ZeroKnowledge(int uid1,int uid2)
	{
		this.uid1=uid1;
		this.uid2=uid2;
	}
	public ZeroKnowledge()
	{

	}
	public long power(long x,long y,long p)
	{
		long res=1;
		x=x%p;
		while(y>0)
		{
			if((y&1)==1)
				res=(res*x)%p;
			y=y>>1;
			x=(x*x)%p;
		}
		return res;
	}
	public boolean verify()
	{
		Alice a=new Alice(uid1);
		Bob b=new Bob();
		long y=a.calcY(g,p,uid2);
		b.setY(y);
		//Scanner sc=new Scanner(System.in);
		//System.out.println("Enter no.of rounds ");
		int it=10;
		return a.aliceKnows(g,p,it,b);
	}
}
class Alice
{
	int x;
	long y;
	Alice(int uid)
	{
		x=uid;
	}
	public long calcY(int g,int p,int u)
	{
		ZeroKnowledge zk=new ZeroKnowledge();
		y=zk.power(g,u,p);
		return y;
	}
	public boolean aliceKnows(int g,int p,int i,Bob b)
	{
		ZeroKnowledge zk=new ZeroKnowledge();
		int j;int flag=0;
		for(j=1;j<=i;j++)
		{
			int r=(int)(Math.random()*1000);
			long C=zk.power(g,r,p);
			b.sendC(C);
			int req=b.request();
			if(req==0)
			{
				if(b.checkresponse(g,r,p,r,req)==false)
				{
					flag=1;
					break;
				}
			}
			else
			{
				long val=zk.power(x+r,1,p-1);
				if(b.checkresponse(g,r,p,val,req)==false)
				{
					flag=1;
					break;
				}
			}
		}
		if(flag==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
class Bob
{
	long y,C;
	public void setY(long y)
	{
		this.y=y;
	}
	public void sendC(long C)
	{
		this.C=C;
	}
	public int request()
	{
		return ((int)Math.round(Math.random()));
	}
	public boolean checkresponse(int g,int r,int p,long val,int request)
	{
		ZeroKnowledge zk=new ZeroKnowledge();
		if(request==0)
		{
			long newC=zk.power(g,val,p);
			if(newC==C)
				return true;
			else
				return false;
		}
		else
		{
			long newC=zk.power(g,val,p);
			long checkwith=zk.power(C*y,1,p);
			if(newC==checkwith)
				return true;
			else
				return false;
		}
	}
}
