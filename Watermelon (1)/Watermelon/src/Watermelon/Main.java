package Watermelon;

import java.io.IOException;
import java.util.*;

public class Main {
	public static String PlayerMode = "" ; //  PlayerMode = black(�ϥΪ̭n�´�) or white  //�զ����
	public static Node CorrentNode = new Node(); //�ثe�L��
	public static int D =8; //�`��
	public static int T = 1 ;
	static int[] intB = {1,1,1,1,1,0,1,0,0,0,0,0,0,0,2,0,2,2,2,2,2};  //��l�a��
	static int  AItest = 0;
	public static void main(String[] args){		
		CorrentNode.board = Arrays.copyOf(intB, intB.length);		
		Game game = new Game();
		game.Start();
    	while(true)
    	{
    	
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		if(!PlayerMode.equals("")) 
    		{
    			System.out.println("�o��player mode = " + PlayerMode);
    			break;
    		}
    	}
    	int count = 0 ;
    	
    	//(��)�t�άy�{    	
    	if(AItest == 1 ){
    		if(PlayerMode == "Black" ){
    		int time=0;
    		Scanner s =new Scanner(System.in);
    		time = s.nextInt();
    		for(int i=0;i<time;i++){
    			if(i==(time-1)){
    				System.out.print("Press ANY key To Start.");
    				try {
    					System.in.read();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				  	System.out.println("Start....");
    			}
    			AlphaBetaAlgorithm2.ChangeScoreTest();
    		    if(i!=0) game.iniFunction();
    		    count = 0 ;
    			while(true){
    				CorrentNode = game.GetComputerInput(CorrentNode);				
    				if(game.WinOrLose(CorrentNode)=="Win"||game.WinOrLose(CorrentNode)=="Lose")
    				{
    					game.ShowWinLose(game.WinOrLose(CorrentNode)); //�ǤJ��Ĺ���Ѽƭ�
    					break;
    				}
    				count++;
    				System.out.println(count);
    				CorrentNode = game.GetComputerInput2(CorrentNode);								
    				if(game.WinOrLose(CorrentNode)=="Win"||game.WinOrLose(CorrentNode)=="Lose")
    				{
    					game.ShowWinLose(game.WinOrLose(CorrentNode));
    					break;
    				}
    				count++;
    				System.out.println("�B�� : "+count);
				
    				//100�B��j���
    				if(count>100) break;
    			}
    			game.TestEnd(CorrentNode);
    			System.out.println("time :" +time+" "+i );
    		 }
    		}
		}
    	else {    	
    		if(PlayerMode == "Black" ){
    			while(true){
    				CorrentNode = game.GetComputerInput(CorrentNode);				
    				if(game.WinOrLose(CorrentNode)=="Win"||game.WinOrLose(CorrentNode)=="Lose")
    				{
    					game.ShowWinLose(game.WinOrLose(CorrentNode)); //�ǤJ��Ĺ���Ѽƭ�
    					break;
    				}
    				count++;
    				System.out.println(count);
    				CorrentNode = game.GetUserInput(CorrentNode); 								
    				if(game.WinOrLose(CorrentNode)=="Win"||game.WinOrLose(CorrentNode)=="Lose")
    				{
    					game.ShowWinLose(game.WinOrLose(CorrentNode));
    					break;
    				}
    				count++;
    				System.out.println(count);
    			}
    		}
		
    		if(PlayerMode == "White" ){
    			while(true){
    				CorrentNode = game.GetUserInput(CorrentNode); 
    				if(game.WinOrLose(CorrentNode)=="Win"||game.WinOrLose(CorrentNode)=="Lose")
    				{
    					game.ShowWinLose(game.WinOrLose(CorrentNode)); //�ǤJ��Ĺ���Ѽƭ�
    					break;
    				}
    				count++;
    				System.out.println(count);
    				CorrentNode = game.GetComputerInput(CorrentNode);
    				if(game.WinOrLose(CorrentNode)=="Win"||game.WinOrLose(CorrentNode)=="Lose")
    				{
    					game.ShowWinLose(game.WinOrLose(CorrentNode)); //�ǤJ��Ĺ���Ѽƭ�
    					break;
    				}
    				count++;
    				System.out.println(count);
    			}
    		}
    	}
		//if(game.WinOrLose(CorrentNode)=="Win")		
	}

}
