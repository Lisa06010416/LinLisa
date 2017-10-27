package Watermelon;
import java.io.IOException;
import java.util.*;
//(改)  這裡我直接把原本的Node和List改掉   Node<String> childNode2 = new Node<String>("Child 2"); 
public class AlphaBetaAlgorithm2 { 
	public String PlayerMode = Main.PlayerMode ;  //PlayerMode為使用者持方
	Node B = new Node();  //最佳Node
	Node Root = new Node();  //Root為現在的局面
	//***************************************************************
	static int[] ScoreTest = new int[21];  //較正他人的其點位置參數		
	//***************************************************************
	static int L_4 = 0; //判斷有沒有移動4次   1有  0無
	static int MoveChessNewPosition = 0;
	public AlphaBetaAlgorithm2(String PlayerMode,Node Root){ //(改) PlayerMode為使用者持方  Root為現在的局面
		PlayerMode = this.PlayerMode; 
		Root = this.Root;		
	}
	
	public static void ChangeScoreTest(){
		//random 棋點參數  
		Random ran = new Random();		
		for(int i=0;i<21;i++){
			//對新參數隨機加減
			if(ran.nextInt(2)==0){  
				//加上random的0~20
				ScoreTest[i] = AlphaBetaAlgorithm.ScoreTest[20-i]+ran.nextInt(21);
			}
			else{
				//減掉random的0~20
				ScoreTest[i] = AlphaBetaAlgorithm.ScoreTest[20-i]-ran.nextInt(21);	
			}
		}
		
		System.out.print("白棋參數 : ");	
		for(int i=0;i<21;i++)  	System.out.print(ScoreTest[i]+" ");		
		System.out.println();						
	}
	
//	(改) 這個Function為AlphaBetaTree演算法

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
	    //
	    
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
		    	    if(Game.MoveChessNewPosition2==i){
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
		n.board = game.Eat(n.board);
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
				if(n.board[0]==1){S-=ScoreTest[0];}
				else if(n.board[0]==2){S+=ScoreTest[0];}
			if(n.board[1]==1){S-=ScoreTest[1];}
				else if(n.board[1]==2){S+=ScoreTest[1];}
			if(n.board[2]==1){S-=ScoreTest[2];}
				else if(n.board[2]==2){S+=ScoreTest[2];}	
			if(n.board[3]==1){S-=ScoreTest[3];}
				else if(n.board[3]==2){S+=ScoreTest[3];}	
			if(n.board[4]==1){S-=ScoreTest[4];}
				else if(n.board[4]==2){S+=ScoreTest[4];}	
			if(n.board[5]==1){S-=ScoreTest[5];}
				else if(n.board[5]==2){S+=ScoreTest[5];}	
			if(n.board[6]==1){S-=ScoreTest[6];}
				else if(n.board[6]==2){S+=ScoreTest[6];}	
			if(n.board[7]==1){S-=ScoreTest[7];}
				else if(n.board[7]==2){S+=ScoreTest[7];}	
			if(n.board[8]==1){S-=ScoreTest[8];}
				else if(n.board[8]==2){S+=ScoreTest[8];}	
			if(n.board[9]==1){S-=ScoreTest[9];}
				else if(n.board[9]==2){S+=ScoreTest[9];}	
			if(n.board[10]==1){S-=ScoreTest[10];}
				else if(n.board[10]==2){S+=ScoreTest[10];}	
			if(n.board[11]==1){S-=ScoreTest[11];}
				else if(n.board[11]==2){S+=ScoreTest[11];}	
			if(n.board[12]==1){S-=ScoreTest[12];}
				else if(n.board[12]==2){S+=ScoreTest[12];}	
			if(n.board[13]==1){S-=ScoreTest[13];}
				else if(n.board[13]==2){S+=ScoreTest[13];}	
			if(n.board[14]==1){S-=ScoreTest[14];}
				else if(n.board[14]==2){S+=ScoreTest[14];}	
			if(n.board[15]==1){S-=ScoreTest[15];}
				else if(n.board[15]==2){S+=ScoreTest[15];}	
			if(n.board[16]==1){S-=ScoreTest[16];}
				else if(n.board[16]==2){S+=ScoreTest[16];}	
			if(n.board[17]==1){S-=ScoreTest[17];}
				else if(n.board[17]==2){S+=ScoreTest[17];}	
			if(n.board[18]==1){S-=ScoreTest[18];}
				else if(n.board[18]==2){S+=ScoreTest[18];}	
			if(n.board[19]==1){S-=ScoreTest[19];}
				else if(n.board[19]==2){S+=ScoreTest[19];}	
			if(n.board[20]==1){S-=ScoreTest[20];}
				else if(n.board[20]==2){S+=ScoreTest[20];}
			
			}
			//棋子分數
			int w=0,b=0;
			for(int j=0;j<21;j++){
				if(n.board[j]==1) w++;
				else if(n.board[j]==2) b++;
			}
			S-=w*30000;
			S+=b*30000;
			//氣分數
			int BQI = 0;
			int WQI = 0;
			for(int index=0;index<21;index++){
				if(n.board[index]!=0) continue;
				if(index==0){ 	
					if(n.board[1]==1){WQI++;}  else if(n.board[1]==2){BQI++;}
					if(n.board[2]==1){WQI++;}  else if(n.board[2]==2){BQI++;}
					if(n.board[3]==1){WQI++;}  else if(n.board[3]==2){BQI++;}	
				}
				else if(index==1){
					if(n.board[0]==1){WQI++;}  else if(n.board[0]==2){BQI++;}
					if(n.board[2]==1){WQI++;}  else if(n.board[2]==2){BQI++;}
					if(n.board[4]==1){WQI++;}  else if(n.board[4]==2){BQI++;}	
				}
				else if(index==2){
					if(n.board[0]==1){WQI++;}  else if(n.board[0]==2){BQI++;}
					if(n.board[1]==1){WQI++;}  else if(n.board[1]==2){BQI++;}
					if(n.board[3]==1){WQI++;}  else if(n.board[3]==2){BQI++;}
					if(n.board[5]==1){WQI++;}  else if(n.board[5]==2){BQI++;}
				}
				else if(index==3){
					if(n.board[0]==1){WQI++;}  else if(n.board[0]==2){BQI++;}
					if(n.board[2]==1){WQI++;}  else if(n.board[2]==2){BQI++;}
					if(n.board[6]==1){WQI++;}  else if(n.board[6]==2){BQI++;}
				}
				else if(index==4){
					if(n.board[1]==1){WQI++;}  else if(n.board[1]==2){BQI++;}
					if(n.board[7]==1){WQI++;}  else if(n.board[7]==2){BQI++;}
					if(n.board[8]==1){WQI++;}  else if(n.board[8]==2){BQI++;}
				}
				else if(index==5){
					if(n.board[2]==1){WQI++;}  else if(n.board[2]==2){BQI++;}
					if(n.board[9]==1){WQI++;}  else if(n.board[9]==2){BQI++;}
					if(n.board[10]==1){WQI++;}  else if(n.board[10]==2){BQI++;}
					if(n.board[11]==1){WQI++;}  else if(n.board[11]==2){BQI++;}
				}
				else if(index==6){
					if(n.board[3]==1){WQI++;}  else if(n.board[3]==2){BQI++;}
					if(n.board[12]==1){WQI++;}  else if(n.board[12]==2){BQI++;}
					if(n.board[13]==1){WQI++;}  else if(n.board[13]==2){BQI++;}
				}
				else if(index==7){
					if(n.board[4]==1){WQI++;}  else if(n.board[4]==2){BQI++;}
					if(n.board[8]==1){WQI++;}  else if(n.board[8]==2){BQI++;}
					if(n.board[14]==1){WQI++;}  else if(n.board[14]==2){BQI++;}
				}
				else if(index==8){
					if(n.board[4]==1){WQI++;}  else if(n.board[4]==2){BQI++;}
					if(n.board[7]==1){WQI++;}  else if(n.board[7]==2){BQI++;}
					if(n.board[9]==1){WQI++;}  else if(n.board[9]==2){BQI++;}
					if(n.board[14]==1){WQI++;}  else if(n.board[14]==2){BQI++;}
				}
				else if(index==9){
					if(n.board[5]==1){WQI++;}  else if(n.board[5]==2){BQI++;}
					if(n.board[8]==1){WQI++;}  else if(n.board[8]==2){BQI++;}
					if(n.board[10]==1){WQI++;}  else if(n.board[10]==2){BQI++;}
					if(n.board[15]==1){WQI++;}  else if(n.board[15]==2){BQI++;}
				}
				else if(index==10){
					if(n.board[5]==1){WQI++;}  else if(n.board[5]==2){BQI++;}
					if(n.board[9]==1){WQI++;}  else if(n.board[9]==2){BQI++;}
					if(n.board[11]==1){WQI++;}  else if(n.board[11]==2){BQI++;}
					if(n.board[15]==1){WQI++;}  else if(n.board[15]==2){BQI++;}
				}
				else if(index==11){
					if(n.board[5]==1){WQI++;}  else if(n.board[5]==2){BQI++;}
					if(n.board[10]==1){WQI++;}  else if(n.board[10]==2){BQI++;}
					if(n.board[12]==1){WQI++;}  else if(n.board[12]==2){BQI++;}
					if(n.board[15]==1){WQI++;}  else if(n.board[15]==2){BQI++;}
				}
				else if(index==12){
					if(n.board[6]==1){WQI++;}  else if(n.board[6]==2){BQI++;}
					if(n.board[11]==1){WQI++;}  else if(n.board[11]==2){BQI++;}
					if(n.board[13]==1){WQI++;}  else if(n.board[13]==2){BQI++;}
					if(n.board[16]==1){WQI++;}  else if(n.board[16]==2){BQI++;}
				}
				else if(index==13){
					if(n.board[6]==1){WQI++;}  else if(n.board[6]==2){BQI++;}
					if(n.board[12]==1){WQI++;}  else if(n.board[12]==2){BQI++;}
					if(n.board[16]==1){WQI++;}  else if(n.board[16]==2){BQI++;}
				}
				else if(index==14){
					if(n.board[7]==1){WQI++;}  else if(n.board[7]==2){BQI++;}
					if(n.board[8]==1){WQI++;}  else if(n.board[8]==2){BQI++;}
					if(n.board[17]==1){WQI++;}  else if(n.board[17]==2){BQI++;}
				}
				else if(index==15){
					if(n.board[9]==1){WQI++;}  else if(n.board[9]==2){BQI++;}
					if(n.board[10]==1){WQI++;}  else if(n.board[10]==2){BQI++;}
					if(n.board[11]==1){WQI++;}  else if(n.board[11]==2){BQI++;}
					if(n.board[18]==1){WQI++;}  else if(n.board[18]==2){BQI++;}
				}
				else if(index==16){
					if(n.board[12]==1){WQI++;}  else if(n.board[12]==2){BQI++;}
					if(n.board[13]==1){WQI++;}  else if(n.board[13]==2){BQI++;}
					if(n.board[19]==1){WQI++;}  else if(n.board[19]==2){BQI++;}
				}
				else if(index==17){
					if(n.board[14]==1){WQI++;}  else if(n.board[14]==2){BQI++;}
					if(n.board[18]==1){WQI++;}  else if(n.board[18]==2){BQI++;}
					if(n.board[20]==1){WQI++;}  else if(n.board[20]==2){BQI++;}
				}
				else if(index==18){
					if(n.board[15]==1){WQI++;}  else if(n.board[15]==2){BQI++;}
					if(n.board[17]==1){WQI++;}  else if(n.board[17]==2){BQI++;}
					if(n.board[19]==1){WQI++;}  else if(n.board[19]==2){BQI++;} 
					if(n.board[20]==1){WQI++;}  else if(n.board[20]==2){BQI++;}
				}
				else if(index==19){
					if(n.board[16]==1){WQI++;}  else if(n.board[16]==2){BQI++;}
					if(n.board[18]==1){WQI++;}  else if(n.board[18]==2){BQI++;}
					if(n.board[20]==1){WQI++;}  else if(n.board[20]==2){BQI++;}
				}
				else if(index==20){
					if(n.board[17]==1){WQI++;}  else if(n.board[17]==2){BQI++;}
					if(n.board[18]==1){WQI++;}  else if(n.board[18]==2){BQI++;}
					if(n.board[19]==1){WQI++;}  else if(n.board[19]==2){BQI++;}
				}	
			}	
			S+=(BQI-WQI)*30;
		//--AI為黑棋(2)
		if(PlayerMode.equals("Black")){
			S=S;			
		}
		//--AI為白棋(1)
		else{
			S=-S;	
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

