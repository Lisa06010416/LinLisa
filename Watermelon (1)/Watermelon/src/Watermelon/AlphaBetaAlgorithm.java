package Watermelon;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//(改)  這裡我直接把原本的Node和List改掉   Node<String> childNode2 = new Node<String>("Child 2"); 
public class AlphaBetaAlgorithm { 
	
	public String PlayerMode = Main.PlayerMode ;  //PlayerMode為使用者持方
	Node B = new Node();  //最佳Node
	Node Root = new Node();  //Root為現在的局面
	//***************************************************************
	static int[] ScoreTest = {98 , 110 , 97 , 115 , 101 , 126 , 121 , 97 , 106 , 108 , 117 , 109 , 108 , 91 , 101 , 75 , 105 , 83 , 113 , 69 , 71};  //被較正的其點位置參數		
	//***************************************************************
	static int L_4 = 0; //判斷有沒有移動4次  1有  0無
	static int MoveChessNewPosition = 0;
	public AlphaBetaAlgorithm(String PlayerMode,Node Root){ //(改) PlayerMode為使用者持方  Root為現在的局面
		PlayerMode = this.PlayerMode; 
		Root = this.Root;
	}
	
//	(改) 這個Function為AlphaBetaTree演算法
	Node[] BestThreadAnswer = new Node[Main.T];
	
	public void threadAB(Node N, int depth, int alpha, int beta){		
		//-----------------------------THREAD----------------找第一層子點
		//判斷下一步是1走或2走
	    int mode =0;
	    if(PlayerMode == "Black")
	    {
	    	if(Main.CorrentNode.level%2==0) mode =2;
	    	else mode =1;
	    }
	    if(PlayerMode == "White")
    	{
    		if(Main.CorrentNode.level%2==0) mode =1;
    		else mode =2;
    	}

	    //找所有子點
	    ArrayList<Node> ChildNode = AddChildNode(Main.CorrentNode,mode);
	 	
	    
	    for(int i=0;i<ChildNode.size();i++){
	    	if(Game.WinOrLose(ChildNode.get(i))=="Win"){
	    		B = ChildNode.get(i);
	    		System.out.println("Computer Win!!!");	
	    		return;
	    	}
	    }
	        
	    //----------------------------- THREAD START ----------------開始THREAD
	    ExecutorService pool = Executors.newFixedThreadPool(Main.T);	 	    
	    for(int i=0;i<Main.T;i++){			 
	    	ArrayList<Node> ChildNodeA = new ArrayList<Node>();
	    	
	    	for(int j = 0 ;j<ChildNode.size();j++){				
				if(j%Main.T==i)
				{ 
					ChildNodeA.add(ChildNode.get(j));				
				}								
			}	    	
	    	pool.execute(new T_AB(ChildNodeA,i));	
	    }
	    pool.shutdown();
	  		try {
	  			pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);
	  		} catch (InterruptedException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
	  	//----------------------------- THREAD END ----------------找出最大質
	  		int best = -java.lang.Integer.MAX_VALUE;
						
			for(int i=0;i<Main.T;i++){
				System.out.println("BestThreadAnswer "+i+" "+BestThreadAnswer[i].score);
				if(BestThreadAnswer[i].score>best) {
					
					B = BestThreadAnswer[i];
					best = B.score;
				}
			}	  															  			  		   	    		
	}
	
	class T_AB implements Runnable
    {
		
		Node BB = new Node();  //最佳Node
		Node Root = new Node();  //Root為現在的局面
		ArrayList<Node> ThreadLevelOneChildNode = new ArrayList();
		int index = -1;


		
		T_AB(ArrayList<Node> ThreadChildNode,int i)
        {

            this.ThreadLevelOneChildNode= ThreadChildNode;
            this.index = i;
            
        }

		@Override
		public void run() {
			alphaBeta( Main.CorrentNode, Main.D, -java.lang.Integer.MAX_VALUE, java.lang.Integer.MAX_VALUE);//電腦算出下一步					
			//-----------------把B放進對應陣列---------------
			BestThreadAnswer[index] = BB;			
		}
		
		public int alphaBeta(Node N, int depth, int alpha, int beta)
		{
		    int value;
		    if( depth == 0 || !Game.WinOrLose(N).equals("Non"))
		    {
		    	 NodeScore(N);
		         return N.score;
		    }		    
		    //判斷下一步是1走或2走
		    int mode =0;
		    if(PlayerMode == "Black")
		    {
		    		if(N.level%2==0) mode =2;
		    		else mode =1;
		    }
		    if(PlayerMode == "White")
	    	{
	    		if(N.level%2==0) mode =1;
	    		else mode =2;
	    	}

		    //找所有子點
		    ArrayList<Node> ChildNode = AddChildNode(N,mode);
		    //如果是第一層  給ThreadLevelOneChildNode
		    if(depth == Main.D){
		    	ChildNode = ThreadLevelOneChildNode;		    
		    }
		    
		    int best = -java.lang.Integer.MAX_VALUE; 
		    Node nextBoard;
		    Node bestBoard = new Node();
		    while (!ChildNode.isEmpty())
		    {		    	
		        nextBoard = ChildNode.get(0);
		        ChildNode.remove(0);	            
		        
		        value = -alphaBeta(nextBoard, depth-1,-beta,-alpha);
		       
		        if(value > best){
		             best = value;	             	        
		             bestBoard = nextBoard;
		        }
		        if(best > alpha)
		             alpha = best;
		        if(best >= beta){
		        	break;
		        }		        
		     }
		    
		    BB = bestBoard;	 
		    BB.score = best ;
		    return best;
		}    
    }
	
	public int alphaBeta(Node N, int depth, int alpha, int beta)
	{
	    int value;
	    if( depth == 0 || !Game.WinOrLose(N).equals("Non"))
	    {
	    	 NodeScore(N);
	         return N.score;
	    }
	    //判斷下一步是1走或2走
	    int mode =0;
	    if(PlayerMode == "Black")
	    {
	    		if(N.level%2==0) mode =2;
	    		else mode =1;
	    }
	    if(PlayerMode == "White")
    	{
    		if(N.level%2==0) mode =1;
    		else mode =2;
    	}

	    //找所有子點
	    ArrayList<Node> ChildNode = AddChildNode(N,mode);
	    //如果下一步就贏  直接下
	    if(depth == Main.D){
	    	for(int i=0;i<ChildNode.size();i++){
	    		if(Game.WinOrLose(ChildNode.get(i))=="Win"){
	    			B = ChildNode.get(i);
	    			System.out.println("Computer Win!!!");
	    			return 0;
	    		}
	    	}
	    }
	    
	    int best = -java.lang.Integer.MAX_VALUE; 
	    Node nextBoard;
	    Node bestBoard = new Node();
	    while (!ChildNode.isEmpty())
	    {
	    	
	        nextBoard = ChildNode.get(0);
	        ChildNode.remove(0);	            
	        
	        value = -alphaBeta(nextBoard, depth-1,-beta,-alpha);
	       
	        if(value > best){
	             best = value;	             	        
	             bestBoard = nextBoard;
	        }
	        if(best > alpha)
	             alpha = best;
	        if(best >= beta){
	        	break;
	        }
	        
	     }
	    
	    B = bestBoard;	    
	    return best;
	}
	//	(改) 這個Function負責將全部ChildNode加到父點之下
	public ArrayList<Node> AddChildNode(Node Parent,int mode){  //Parent為父點  mode= 1 or 2 為要找的是黑色或白色的子點 ex mode=2找黑色子點
		//int[] NewBoard = new int[21]; //走下一步的盤面 
		
		ArrayList<Node> ChildNode = new ArrayList<Node>();
		//找出所有ChildNode 
		for(int i=0;i<21;i++){
			 //如果有旗子重複移動4次  移除該旗子移動的tree
		     if(L_4==1){
		    	    if(Game.MoveChessNewPosition==i){
		    	    L_4=0;  continue;
		    	}
		     }
		    //
			if(Parent.board[i]==mode){
				FindNextStep(Parent.board,i,ChildNode,Parent);
			}
		}
		
		return ChildNode;	
	}
	
	public void FindNextStep(int[] map,int index, ArrayList<Node> ChildNode,Node Parent){ //在圖中位置為index的點  可能的走法(ChildNode)		
		if(index==0){ 	
			if(map[1]==0){AddNode(map,index,1,ChildNode,Parent);} 
			if(map[2]==0){AddNode(map,index,2,ChildNode,Parent);} 
			if(map[3]==0){AddNode(map,index,3,ChildNode,Parent);} 			
		}
		else if(index==1){
			if(map[0]==0){AddNode(map,index,0,ChildNode,Parent);} 
			if(map[2]==0){AddNode(map,index,2,ChildNode,Parent);} 
			if(map[4]==0){AddNode(map,index,4,ChildNode,Parent);} 	
		}
		else if(index==2){
			if(map[0]==0){AddNode(map,index,0,ChildNode,Parent);} 
			if(map[1]==0){AddNode(map,index,1,ChildNode,Parent);} 
			if(map[3]==0){AddNode(map,index,3,ChildNode,Parent);} 
			if(map[5]==0){AddNode(map,index,5,ChildNode,Parent);} 
		}
		else if(index==3){
			if(map[0]==0){AddNode(map,index,0,ChildNode,Parent);} 
			if(map[2]==0){AddNode(map,index,2,ChildNode,Parent);} 
			if(map[6]==0){AddNode(map,index,6,ChildNode,Parent);} 
		}
		else if(index==4){
			if(map[1]==0){AddNode(map,index,1,ChildNode,Parent);} 
			if(map[7]==0){AddNode(map,index,7,ChildNode,Parent);} 
			if(map[8]==0){AddNode(map,index,8,ChildNode,Parent);} 
		}
		else if(index==5){
			if(map[2]==0){AddNode(map,index,2,ChildNode,Parent);} 
			if(map[9]==0){AddNode(map,index,9,ChildNode,Parent);} 
			if(map[10]==0){AddNode(map,index,10,ChildNode,Parent);} 
			if(map[11]==0){AddNode(map,index,11,ChildNode,Parent);} 
		}
		else if(index==6){
			if(map[3]==0){AddNode(map,index,3,ChildNode,Parent);} 
			if(map[12]==0){AddNode(map,index,12,ChildNode,Parent);} 
			if(map[13]==0){AddNode(map,index,13,ChildNode,Parent);} 
		}
		else if(index==7){
			if(map[4]==0){AddNode(map,index,4,ChildNode,Parent);} 
			if(map[8]==0){AddNode(map,index,8,ChildNode,Parent);} 
			if(map[14]==0){AddNode(map,index,14,ChildNode,Parent);} 
		}
		else if(index==8){
			if(map[4]==0){AddNode(map,index,4,ChildNode,Parent);} 
			if(map[7]==0){AddNode(map,index,7,ChildNode,Parent);} 
			if(map[9]==0){AddNode(map,index,9,ChildNode,Parent);} 
			if(map[14]==0){AddNode(map,index,14,ChildNode,Parent);} 
		}
		else if(index==9){
			if(map[5]==0){AddNode(map,index,5,ChildNode,Parent);} 
			if(map[8]==0){AddNode(map,index,8,ChildNode,Parent);} 
			if(map[10]==0){AddNode(map,index,10,ChildNode,Parent);} 
			if(map[15]==0){AddNode(map,index,15,ChildNode,Parent);} 
		}
		else if(index==10){
			if(map[5]==0){AddNode(map,index,5,ChildNode,Parent);} 
			if(map[9]==0){AddNode(map,index,9,ChildNode,Parent);} 
			if(map[11]==0){AddNode(map,index,11,ChildNode,Parent);} 
			if(map[15]==0){AddNode(map,index,15,ChildNode,Parent);} 
		}
		else if(index==11){
			if(map[5]==0){AddNode(map,index,5,ChildNode,Parent);} 
			if(map[10]==0){AddNode(map,index,10,ChildNode,Parent);} 
			if(map[12]==0){AddNode(map,index,12,ChildNode,Parent);} 
			if(map[15]==0){AddNode(map,index,15,ChildNode,Parent);} 
		}
		else if(index==12){
			if(map[6]==0){AddNode(map,index,6,ChildNode,Parent);} 
			if(map[11]==0){AddNode(map,index,11,ChildNode,Parent);} 
			if(map[13]==0){AddNode(map,index,13,ChildNode,Parent);} 
			if(map[16]==0){AddNode(map,index,16,ChildNode,Parent);} 
		}
		else if(index==13){
			if(map[6]==0){AddNode(map,index,6,ChildNode,Parent);} 
			if(map[12]==0){AddNode(map,index,12,ChildNode,Parent);} 
			if(map[16]==0){AddNode(map,index,16,ChildNode,Parent);} 
		}
		else if(index==14){
			if(map[7]==0){AddNode(map,index,7,ChildNode,Parent);} 
			if(map[8]==0){AddNode(map,index,8,ChildNode,Parent);} 
			if(map[17]==0){AddNode(map,index,17,ChildNode,Parent);}
		}
		else if(index==15){
			if(map[9]==0){AddNode(map,index,9,ChildNode,Parent);} 
			if(map[10]==0){AddNode(map,index,10,ChildNode,Parent);} 
			if(map[11]==0){AddNode(map,index,11,ChildNode,Parent);}
			if(map[18]==0){AddNode(map,index,18,ChildNode,Parent);} 
		}
		else if(index==16){
			if(map[12]==0){AddNode(map,index,12,ChildNode,Parent);} 
			if(map[13]==0){AddNode(map,index,13,ChildNode,Parent);} 
			if(map[19]==0){AddNode(map,index,19,ChildNode,Parent);}
		}
		else if(index==17){
			if(map[14]==0){AddNode(map,index,14,ChildNode,Parent);} 
			if(map[18]==0){AddNode(map,index,18,ChildNode,Parent);} 
			if(map[20]==0){AddNode(map,index,20,ChildNode,Parent);}
		}
		else if(index==18){
			if(map[15]==0){AddNode(map,index,15,ChildNode,Parent);} 
			if(map[17]==0){AddNode(map,index,17,ChildNode,Parent);} 
			if(map[19]==0){AddNode(map,index,19,ChildNode,Parent);} 
			if(map[20]==0){AddNode(map,index,20,ChildNode,Parent);}
		}
		else if(index==19){
			if(map[16]==0){AddNode(map,index,16,ChildNode,Parent);} 
			if(map[18]==0){AddNode(map,index,18,ChildNode,Parent);} 
			if(map[20]==0){AddNode(map,index,20,ChildNode,Parent);}
		}
		else if(index==20){
			if(map[17]==0){AddNode(map,index,17,ChildNode,Parent);} 
			if(map[18]==0){AddNode(map,index,18,ChildNode,Parent);} 
			if(map[19]==0){AddNode(map,index,19,ChildNode,Parent);}
		}	
	}
	
	public void AddNode(int[] map,int index_1,int index_2, ArrayList<Node> ChildNode ,Node Parent){
		Node n = new Node(); n.board=Arrays.copyOf(map, map.length); n.board[index_2]= map[index_1]; n.board[index_1] = 0;
		Game game = new Game();

		//n.board = game.Eat(n.board);
		n.parent = Parent;
		n.level = Parent.level+1;
		ChildNode.add(n);
	}
		
	// (改) 這個Function負責記錄一個點其評估值
	public void NodeScore(Node n)
	{
		int Score = -1;
		//評估局面分數  --棋點分數		
			int S =0;
			for(int i=0;i<21;i++){
				if(n.board[0]==1){S-=100;}
					else if(n.board[0]==2){S+=100;}
				if(n.board[1]==1){S-=100;}
					else if(n.board[1]==2){S+=100;}
				if(n.board[2]==1){S-=130;}
					else if(n.board[2]==2){S+=130;}	
				if(n.board[3]==1){S-=100;}
					else if(n.board[3]==2){S+=100;}	
				if(n.board[4]==1){S-=130;}
					else if(n.board[4]==2){S+=130;}	
				if(n.board[5]==1){S-=140;}
					else if(n.board[5]==2){S+=140;}	
				if(n.board[6]==1){S-=130;}
					else if(n.board[6]==2){S+=130;}	
				if(n.board[7]==1){S-=110;}
					else if(n.board[7]==2){S+=110;}	
				if(n.board[8]==1){S-=150;}
					else if(n.board[8]==2){S+=150;}	
				if(n.board[9]==1){S-=150;}
					else if(n.board[9]==2){S+=150;}	
				if(n.board[10]==1){S-=130;}
					else if(n.board[10]==2){S+=130;}	
				if(n.board[11]==1){S-=150;}
					else if(n.board[11]==2){S+=150;}	
				if(n.board[12]==1){S-=150;}
					else if(n.board[12]==2){S+=150;}	
				if(n.board[13]==1){S-=110;}
					else if(n.board[13]==2){S+=110;}	
				if(n.board[14]==1){S-=130;}
					else if(n.board[14]==2){S+=130;}	
				if(n.board[15]==1){S-=150;}
					else if(n.board[15]==2){S+=150;}	
				if(n.board[16]==1){S-=130;}
					else if(n.board[16]==2){S+=130;}	
				if(n.board[17]==1){S-=100;}
					else if(n.board[17]==2){S+=100;}	
				if(n.board[18]==1){S-=130;}
					else if(n.board[18]==2){S+=130;}	
				if(n.board[19]==1){S-=100;}
					else if(n.board[19]==2){S+=100;}	
				if(n.board[20]==1){S-=100;}
					else if(n.board[20]==2){S+=100;}
				}
				int w=0,b=0;
				for(int j=0;j<21;j++){
					if(n.board[j]==1) w++;
					else if(n.board[j]==2) b++;
				}
				S-=w*10000;
				S+=b*10000;
			//--AI為黑棋(2)
			if(PlayerMode.equals("Black")){
				S=S;
				if(Game.WinOrLose(n).equals("Win")) S = java.lang.Integer.MAX_VALUE;
				else if(Game.WinOrLose(n).equals("Win")) S = java.lang.Integer.MIN_VALUE;			
			}
			//--AI為白棋(1)
			else{
				S=-S;
				if(Game.WinOrLose(n).equals("Win")) S = java.lang.Integer.MAX_VALUE;
				else if(Game.WinOrLose(n).equals("Win")) S = java.lang.Integer.MIN_VALUE;
			}
			//--AI為黑棋(2)
			if(n.level%2==1){
					S=-S;
			}
			//--AI為白棋(1)
			else{
				S=S;
			}
			//記錄分數
			n.score = S; 
	}	
}

