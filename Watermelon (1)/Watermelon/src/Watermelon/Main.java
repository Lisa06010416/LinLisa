package Watermelon;

import java.io.IOException;
import java.util.*;

public class Main {
	public static String PlayerMode = "" ; //  PlayerMode = black(使用者要黑棋) or white  //白色先攻
	public static Node CorrentNode = new Node(); //目前盤面
	public static int D =8; //深度
	public static int T = 1 ;
	static int[] intB = {1,1,1,1,1,0,1,0,0,0,0,0,0,0,2,0,2,2,2,2,2};  //初始地圖
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
    			System.out.println("得到player mode = " + PlayerMode);
    			break;
    		}
    	}
    	int count = 0 ;
    	
    	//(改)系統流程    	
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
    					game.ShowWinLose(game.WinOrLose(CorrentNode)); //傳入輸贏的參數值
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
    				System.out.println("步數 : "+count);
				
    				//100步後強制停止
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
    					game.ShowWinLose(game.WinOrLose(CorrentNode)); //傳入輸贏的參數值
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
    					game.ShowWinLose(game.WinOrLose(CorrentNode)); //傳入輸贏的參數值
    					break;
    				}
    				count++;
    				System.out.println(count);
    				CorrentNode = game.GetComputerInput(CorrentNode);
    				if(game.WinOrLose(CorrentNode)=="Win"||game.WinOrLose(CorrentNode)=="Lose")
    				{
    					game.ShowWinLose(game.WinOrLose(CorrentNode)); //傳入輸贏的參數值
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
