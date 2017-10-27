package Watermelon;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//(��)  �o�̧ڪ�����쥻��Node�MList�ﱼ   Node<String> childNode2 = new Node<String>("Child 2"); 
public class AlphaBetaAlgorithm { 
	
	public String PlayerMode = Main.PlayerMode ;  //PlayerMode���ϥΪ̫���
	Node B = new Node();  //�̨�Node
	Node Root = new Node();  //Root���{�b������
	//***************************************************************
	static int[] ScoreTest = {98 , 110 , 97 , 115 , 101 , 126 , 121 , 97 , 106 , 108 , 117 , 109 , 108 , 91 , 101 , 75 , 105 , 83 , 113 , 69 , 71};  //�Q���������I��m�Ѽ�		
	//***************************************************************
	static int L_4 = 0; //�P�_���S������4��  1��  0�L
	static int MoveChessNewPosition = 0;
	public AlphaBetaAlgorithm(String PlayerMode,Node Root){ //(��) PlayerMode���ϥΪ̫���  Root���{�b������
		PlayerMode = this.PlayerMode; 
		Root = this.Root;
	}
	
//	(��) �o��Function��AlphaBetaTree�t��k
	Node[] BestThreadAnswer = new Node[Main.T];
	
	public void threadAB(Node N, int depth, int alpha, int beta){		
		//-----------------------------THREAD----------------��Ĥ@�h�l�I
		//�P�_�U�@�B�O1����2��
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

	    //��Ҧ��l�I
	    ArrayList<Node> ChildNode = AddChildNode(Main.CorrentNode,mode);
	 	
	    
	    for(int i=0;i<ChildNode.size();i++){
	    	if(Game.WinOrLose(ChildNode.get(i))=="Win"){
	    		B = ChildNode.get(i);
	    		System.out.println("Computer Win!!!");	
	    		return;
	    	}
	    }
	        
	    //----------------------------- THREAD START ----------------�}�lTHREAD
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
	  	//----------------------------- THREAD END ----------------��X�̤j��
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
		
		Node BB = new Node();  //�̨�Node
		Node Root = new Node();  //Root���{�b������
		ArrayList<Node> ThreadLevelOneChildNode = new ArrayList();
		int index = -1;


		
		T_AB(ArrayList<Node> ThreadChildNode,int i)
        {

            this.ThreadLevelOneChildNode= ThreadChildNode;
            this.index = i;
            
        }

		@Override
		public void run() {
			alphaBeta( Main.CorrentNode, Main.D, -java.lang.Integer.MAX_VALUE, java.lang.Integer.MAX_VALUE);//�q����X�U�@�B					
			//-----------------��B��i�����}�C---------------
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
		    //�P�_�U�@�B�O1����2��
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

		    //��Ҧ��l�I
		    ArrayList<Node> ChildNode = AddChildNode(N,mode);
		    //�p�G�O�Ĥ@�h  ��ThreadLevelOneChildNode
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
	    //�P�_�U�@�B�O1����2��
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

	    //��Ҧ��l�I
	    ArrayList<Node> ChildNode = AddChildNode(N,mode);
	    //�p�G�U�@�B�NĹ  �����U
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
	//	(��) �o��Function�t�d�N����ChildNode�[����I���U
	public ArrayList<Node> AddChildNode(Node Parent,int mode){  //Parent�����I  mode= 1 or 2 ���n�䪺�O�¦�Υզ⪺�l�I ex mode=2��¦�l�I
		//int[] NewBoard = new int[21]; //���U�@�B���L�� 
		
		ArrayList<Node> ChildNode = new ArrayList<Node>();
		//��X�Ҧ�ChildNode 
		for(int i=0;i<21;i++){
			 //�p�G���X�l���Ʋ���4��  �����ӺX�l���ʪ�tree
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
	
	public void FindNextStep(int[] map,int index, ArrayList<Node> ChildNode,Node Parent){ //�b�Ϥ���m��index���I  �i�઺���k(ChildNode)		
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
		
	// (��) �o��Function�t�d�O���@���I�������
	public void NodeScore(Node n)
	{
		int Score = -1;
		//������������  --���I����		
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
			//--AI���´�(2)
			if(PlayerMode.equals("Black")){
				S=S;
				if(Game.WinOrLose(n).equals("Win")) S = java.lang.Integer.MAX_VALUE;
				else if(Game.WinOrLose(n).equals("Win")) S = java.lang.Integer.MIN_VALUE;			
			}
			//--AI���մ�(1)
			else{
				S=-S;
				if(Game.WinOrLose(n).equals("Win")) S = java.lang.Integer.MAX_VALUE;
				else if(Game.WinOrLose(n).equals("Win")) S = java.lang.Integer.MIN_VALUE;
			}
			//--AI���´�(2)
			if(n.level%2==1){
					S=-S;
			}
			//--AI���մ�(1)
			else{
				S=S;
			}
			//�O������
			n.score = S; 
	}	
}

