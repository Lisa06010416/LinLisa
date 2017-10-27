package Watermelon;
import java.io.IOException;
import java.util.Arrays;

//(��)  ���F��K����C���y�{�ڥ[�F�o��class�t�d�C���y�{�������\��
public class Game {

	public static ChessBoard t =new ChessBoard(); //�ϥε���������class
	
	public test m = new test();	
	public  Game (){

    	
	}	
	public void Start(){
		Thread thread = new Thread(t); //�o��thread�O�������e���@����s�Ϊ�
    	thread.start();		
    	//m.start();
	}
	//*** �Ѥ�����o�ϥΪ̿�J
	public Node GetUserInput(Node CorrentNode){
		int[] NewBoard = new int[21];	//�s�L��
		Node nextStep = new Node();		//�O���U�@�B�L����ƪ��`�I
		while(true) //���ݨϥΪ̿�J
		{
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(t.GetRefleshSignal()) //�ϥΪ̿�J�F
			{
				System.out.println("�ϥΪ̿�J�F");
				NewBoard = t.GetBoardArray();//< = ���o�ϥΪ̿�J���s�L�� 
				
				System.out.println("�ϥΪ̿�J���s�L��:");				
				for(int j=0;j<21;j++){
					System.out.print(NewBoard[j]+",");
				}
			    System.out.println();
			    
				break;
			}
		} 
		t.SetRefleshSignal(false);
		nextStep.board = Eat(NewBoard); //�P�_�O�_���Y�l	
		t.PlayerEatBoardArray(nextStep.board);//��Y�l�᪺board�g��e��
		
		return nextStep; 
	}
	//*** �N�q������Jshow��ù��W
	public void ShowComputer(){

	}
	//*** �N�̲ת���Ĺshow��ù��W
	public void ShowWinLose(String WinORLose){
		t.ShowWinORLose(WinORLose);
	}

	public static String WinOrLose(Node n){
		String WinOrLose = "Non";
		
		//��X�¥դl���ƶq
		int w=0,b=0;
		for(int j=0;j<21;j++){
			if(n.board[j]==1) w++;
			else if(n.board[j]==2) b++;
		}
		//�P�_�O�_��������  �ÿ�X�ϥΪ̿�Ĺ�����G  Win / Lose / Non(�٨S���X�ӭt)
		if(Main.PlayerMode.equals("Black")){
			if(w<=2) 
			{
				WinOrLose = "Win";
			}
			else if(b<=2) 
			{
				WinOrLose = "Lose";
			}
		}
		if(Main.PlayerMode.equals("White")){
			if(w<=2)
			{
				WinOrLose = "Lose";
			}
			else if(b<=2)
			{
				WinOrLose = "Win";
			}
		}
		return WinOrLose;
	}
	
	public static void iniFunction(){
		//��l�Ʀa��
		t.init();
		Main.CorrentNode.board = Arrays.copyOf(Main.intB, Main.intB.length); 
		ChessBoard.CurrentBoardArray = Arrays.copyOf(Main.intB, Main.intB.length); 
		Main.CorrentNode.level=0;
		ChessBoard.ChessPosition = Arrays.copyOf(ChessBoard.iniChessPosition, ChessBoard.iniChessPosition.length);
				
		//��l�Ʀa�ϭ��4�B�����
		Arrays.fill(l_4, 0); len = 0; MoveChessNumber = -1; MoveChessNewPosition = -1;
		Arrays.fill(l_4_2, 0); len_2 = 0; MoveChessNumber2 = -1; MoveChessNewPosition2 = -1;
		AlphaBetaAlgorithm.L_4 = 0; 	
		AlphaBetaAlgorithm2.L_4 = 0; 	
	}
	
	public static void TestEnd(Node n){
		//�ݿ�Ĺ
		int w=0,b=0;
    	
    	//��X�¥դl���ƶq    		
    	for(int j=0;j<21;j++){
    		if(n.board[j]==1) w++;
    		else if(n.board[j]==2) b++;
    	}
    	
    	System.out.println("�մѼ� : "+w+" �´Ѽ� : "+b);
    	//�p�G�մ�Ĺ  �����쥻�´Ѫ����I�Ѽ�
    	if(w>b){
    		System.out.println("�����Ѽ�...");
    		for(int i=0;i<21;i++){   			
    			int x = AlphaBetaAlgorithm2.ScoreTest[20-i] - AlphaBetaAlgorithm.ScoreTest[i];
    		    AlphaBetaAlgorithm.ScoreTest[i] += x/5; 
    		}
    	}
    	
    	//��ܸ������G
		for(int i=0;i<21;i++)  	System.out.print(AlphaBetaAlgorithm.ScoreTest[i]+" , ");		
		System.out.println();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//    	
	}
	
	public Node GetComputerInput(Node CorrentNode){
		Node nextStep = new Node();
		AlphaBetaAlgorithm AB = new AlphaBetaAlgorithm(Main.PlayerMode,CorrentNode);
		
		//----------------------THREAD-----------------------------
			//AB.alphaBeta(CorrentNode, Main.D, -java.lang.Integer.MAX_VALUE, java.lang.Integer.MAX_VALUE);//�q����X�U�@�B
			AB.threadAB(CorrentNode, Main.D, -java.lang.Integer.MAX_VALUE, java.lang.Integer.MAX_VALUE);
		//----------------------THREAD-----------------------------
		nextStep = AB.B;
		//�����Ӧ^��4��
				if(Limit(nextStep.board)) {System.out.println("123");}  //�p�G�N�n�Ӧ^��4��  �������I
		//

		System.out.print("�q����X��nextStep : ");
		for(int j=0;j<21;j++){
			System.out.print(AB.B.board[j]+",");
		}
	    System.out.println();
		t.ComputerSetBoardArray(nextStep.board);//��J�q���U�@�B����
		System.out.println("�q���p�⧹��!");
		return nextStep; 
	}
	
	public Node GetComputerInput2(Node CorrentNode){
		Node nextStep = new Node();
		AlphaBetaAlgorithm2 AB = new AlphaBetaAlgorithm2(Main.PlayerMode,CorrentNode);
		
		
		AB.alphaBeta(CorrentNode, Main.D, -java.lang.Integer.MAX_VALUE, java.lang.Integer.MAX_VALUE);//�q����X�U�@�B
		
		nextStep = AB.B;
		//�����Ӧ^��4��
				if(Limit2(nextStep.board)) {System.out.println("123");}  //�p�G�N�n�Ӧ^��4��  �������I
		//

		System.out.print("�q����X��nextStep : ");
		for(int j=0;j<21;j++){
			System.out.print(AB.B.board[j]+",");
		}
	    System.out.println();
		t.ComputerSetBoardArray2(nextStep.board);//��J�q���U�@�B����
		System.out.println("�q���p�⧹��!");
		return nextStep; 
	}
	
	//�����Ӧ^��4��
	static int[] l_4 = new int[3];
	static int len = 0;
	static int MoveChessNumber = -1;
	static int MoveChessNewPosition = -1;
	public boolean Limit(int[] BoardArrayFromBack){
	
		System.out.println("MoveChessNewPosition "+MoveChessNumber);
		
		//�P�_���S�����ƨ�
		if(len==0){
			l_4[len]=MoveChessNumber;   //�O��������m
			len++;
		}
		else if(len==1){
			if(l_4[0]==MoveChessNumber) {		//�p�G��1�B�����0�B
				l_4[len]=MoveChessNumber;   //�O��������m
				len++;
			}
			else {										//�p�G��1�B�������0�B
				Arrays.fill(l_4, 0);		 //�M���O��
				len=0;
				l_4[len]=MoveChessNumber;   //�N�o���O�����Ĥ@��
				len++;
			}
		}
		else if(len==2){
			if(l_4[0]==MoveChessNumber) {		//�p�G��3�B�����0�B
				Arrays.fill(l_4, 0);		 //�M���O��
				len=0;
				AlphaBetaAlgorithm.L_4 = 1 ;  //�����U�@���n�����P����
			}
			else {										//�p�G��3�B�������0�B
				Arrays.fill(l_4, 0);		 //�M���O��
				len=0;
				l_4[len]=MoveChessNumber;   //�N�o���O�����Ĥ@��
				len++;
			}
		}
	
		//
		for(int i=0;i<len;i++) System.out.print(l_4[i]+" ");
		System.out.println();			
		//
		return false;		
	}

	//�����Ӧ^��4��#2
	static int[] l_4_2 = new int[3];
	static int len_2 = 0;
	static int MoveChessNumber2 = -1;
	static int MoveChessNewPosition2 = -1;
	public boolean Limit2(int[] BoardArrayFromBack){
		
			System.out.println("MoveChessNewPosition2 "+MoveChessNumber2);
			
			//�P�_���S�����ƨ�
			if(len_2==0){
				l_4_2[len_2]=MoveChessNumber2;   //�O��������m
				len_2++;
			}
			else if(len_2==1){
				if(l_4_2[0]==MoveChessNumber2) {		//�p�G��1�B�����0�B
					l_4_2[len_2]=MoveChessNumber2;   //�O��������m
					len_2++;
				}
				else {										//�p�G��1�B�������0�B
					Arrays.fill(l_4_2, 0);		 //�M���O��
					len_2=0;
					l_4_2[len_2]=MoveChessNumber2;   //�N�o���O�����Ĥ@��
					len_2++;
				}
			}
			else if(len_2==2){
				if(l_4_2[0]==MoveChessNumber2) {		//�p�G��2�B�����0�B
					Arrays.fill(l_4_2, 0);		 //�M���O��
					len_2=0;
					AlphaBetaAlgorithm2.L_4 = 1 ;  //�����U�@���n�����P����
				}
				else {										//�p�G��2�B�������0�B
					Arrays.fill(l_4_2, 0);		 //�M���O��
					len_2=0;
					l_4_2[len_2]=MoveChessNumber2;   //�N�o���O�����Ĥ@��
					len_2++;
				}
			}
		
			//
			for(int i=0;i<len_2;i++) System.out.print(l_4_2[i]+" ");
			System.out.println();			
			//
			return false;		
		}
    //-------------------------�Y�l--------------------------------	
	int[] block ;
	int blockLength ;   
	int[] map;
	 public int[] Eat(int[] board){
		map = Arrays.copyOf(board, board.length);
		//��Board���R�ثe�L���P�_�O�_�i�H�Y�l 
	        for(int i = 0;i < 21; i++){
	     
	                if(map[i] == 0)  
	                   continue;
	                else{
	                	block = new int[21];
	                	blockLength = 1;                   
	                   	block[0] = i; 
	                   	//System.out.println("map :"+ i);
	                    recursion(i); 
	                    //System.out.println(i);
	                    if(hasQi()) 
	                    	continue;            
	                    else {
	                        for(int t = 0;t < blockLength; t++){
	                        	//System.out.println("block :"+ block[t]);
	                        	map[block[t]] = 0; 
	                        }
	                    }
	                }
	            
	        }
	   
		return map; 
	}
	
	public boolean isInBlock(int x){
		for(int i=0; i<blockLength ; i++){
			if(x==block[i]) return false;
		}		
		return true; 
	}
	
    public void recursion(int index){   
    	if(index == 0 ){ 	
			if(map[1]==map[index]&& isInBlock(1)){block[blockLength] = 1;   blockLength++;   recursion(1);} 
			if(map[2]==map[index]&& isInBlock(2)){block[blockLength] = 2;   blockLength++;   recursion(2);} 
			if(map[3]==map[index]&& isInBlock(3)){block[blockLength] = 3;   blockLength++;   recursion(3);} 			
		}
		else if(index==1){
			if(map[0]==map[index]&& isInBlock(0)){block[blockLength] = 0;   blockLength++;   recursion(0);} 
			if(map[2]==map[index]&& isInBlock(2)){block[blockLength] = 2;   blockLength++;   recursion(2);} 
			if(map[4]==map[index]&& isInBlock(4)){block[blockLength] = 4;   blockLength++;   recursion(4);} 
		}
		else if(index==2){
			if(map[0]==map[index]&& isInBlock(0)){block[blockLength] = 0;   blockLength++;   recursion(0);} 
			if(map[1]==map[index]&& isInBlock(1)){block[blockLength] = 1;   blockLength++;   recursion(1);} 
			if(map[3]==map[index]&& isInBlock(3)){block[blockLength] = 3;   blockLength++;   recursion(3);} 
			if(map[5]==map[index]&& isInBlock(5)){block[blockLength] = 5;   blockLength++;   recursion(5);} 
		}
		else if(index==3){
			if(map[0]==map[index]&& isInBlock(0)){block[blockLength] = 0;   blockLength++;   recursion(0);} 
			if(map[2]==map[index]&& isInBlock(2)){block[blockLength] = 2;   blockLength++;   recursion(2);} 
			if(map[6]==map[index]&& isInBlock(6)){block[blockLength] = 6;   blockLength++;   recursion(6);} 
		}
		else if(index==4){
			if(map[1]==map[index]&& isInBlock(1)){block[blockLength] = 1;   blockLength++;   recursion(1);} 
			if(map[7]==map[index]&& isInBlock(7)){block[blockLength] = 7;   blockLength++;   recursion(7);} 
			if(map[8]==map[index]&& isInBlock(8)){block[blockLength] = 8;   blockLength++;   recursion(8);} 
		}
		else if(index==5){
			if(map[2]==map[index]&& isInBlock(2)){block[blockLength] = 2;   blockLength++;   recursion(2);} 
			if(map[9]==map[index]&& isInBlock(9)){block[blockLength] = 9;   blockLength++;   recursion(9);} 
			if(map[10]==map[index]&& isInBlock(10)){block[blockLength] = 10;   blockLength++;   recursion(10);} 
			if(map[11]==map[index]&& isInBlock(11)){block[blockLength] = 11;   blockLength++;   recursion(11);} 
		}
		else if(index==6){
			if(map[3]==map[index]&& isInBlock(3)){block[blockLength] = 3;   blockLength++;   recursion(3);} 
			if(map[12]==map[index]&& isInBlock(12)){block[blockLength] = 12;   blockLength++;   recursion(12);} 
			if(map[13]==map[index]&& isInBlock(13)){block[blockLength] = 13;   blockLength++;   recursion(13);} 
		}
		else if(index==7){
			if(map[4]==map[index]&& isInBlock(4)){block[blockLength] = 4;   blockLength++;   recursion(4);} 
			if(map[8]==map[index]&& isInBlock(8)){block[blockLength] = 8;   blockLength++;   recursion(8);} 
			if(map[14]==map[index]&& isInBlock(14)){block[blockLength] = 14;   blockLength++;   recursion(14);} 
		}
		else if(index==8){
			if(map[4]==map[index]&& isInBlock(4)){block[blockLength] = 4;   blockLength++;   recursion(4);} 
			if(map[7]==map[index]&& isInBlock(7)){block[blockLength] = 7;   blockLength++;   recursion(7);} 
			if(map[9]==map[index]&& isInBlock(8)){block[blockLength] = 9;   blockLength++;   recursion(9);} 
			if(map[14]==map[index]&& isInBlock(14)){block[blockLength] = 14;   blockLength++;   recursion(14);} 
		}
		else if(index==9){
			if(map[5]==map[index]&& isInBlock(5)){block[blockLength] = 5;   blockLength++;   recursion(5);} 
			if(map[8]==map[index]&& isInBlock(8)){block[blockLength] = 8;   blockLength++;   recursion(8);} 
			if(map[10]==map[index]&& isInBlock(10)){block[blockLength] = 10;   blockLength++;   recursion(10);} 
			if(map[15]==map[index]&& isInBlock(15)){block[blockLength] = 15;   blockLength++;   recursion(15);} 
		}
		else if(index==10){
			if(map[5]==map[index]&& isInBlock(5)){block[blockLength] = 5;   blockLength++;   recursion(5);} 
			if(map[9]==map[index]&& isInBlock(9)){block[blockLength] = 9;   blockLength++;   recursion(9);} 
			if(map[11]==map[index]&& isInBlock(11)){block[blockLength] = 11;   blockLength++;   recursion(11);} 
			if(map[15]==map[index]&& isInBlock(15)){block[blockLength] = 15;   blockLength++;   recursion(15);} 
		}
		else if(index==11){
			if(map[5]==map[index]&& isInBlock(5)){block[blockLength] = 5;   blockLength++;   recursion(5);} 
			if(map[10]==map[index]&& isInBlock(10)){block[blockLength] = 10;   blockLength++;   recursion(10);} 			
			if(map[12]==map[index]&& isInBlock(12)){block[blockLength] = 12;   blockLength++;   recursion(12);} 
			if(map[15]==map[index]&& isInBlock(15)){block[blockLength] = 15;   blockLength++;   recursion(15);} 
		}
		else if(index==12){
			if(map[6]==map[index]&& isInBlock(6)){block[blockLength] = 6;   blockLength++;   recursion(6);} 
			if(map[11]==map[index]&& isInBlock(11)){block[blockLength] = 11;   blockLength++;   recursion(11);} 
			if(map[13]==map[index]&& isInBlock(13)){block[blockLength] = 13;   blockLength++;   recursion(13);} 
			if(map[16]==map[index]&& isInBlock(16)){block[blockLength] = 16;   blockLength++;   recursion(16);}  
		}
		else if(index==13){
			if(map[6]==map[index]&& isInBlock(6)){block[blockLength] = 6;   blockLength++;   recursion(6);} 
			if(map[12]==map[index]&& isInBlock(12)){block[blockLength] = 12;   blockLength++;   recursion(12);} 
			if(map[16]==map[index]&& isInBlock(16)){block[blockLength] = 16;   blockLength++;   recursion(16);} 
		}
		else if(index==14){
			if(map[7]==map[index]&& isInBlock(7)){block[blockLength] = 7;   blockLength++;   recursion(7);} 
			if(map[8]==map[index]&& isInBlock(8)){block[blockLength] = 8;   blockLength++;   recursion(8);} 
			if(map[17]==map[index]&& isInBlock(17)){block[blockLength] = 17;   blockLength++;   recursion(17);} 
		}
		else if(index==15){
			if(map[9]==map[index]&& isInBlock(9)){block[blockLength] = 8;   blockLength++;   recursion(9);} 
			if(map[10]==map[index]&& isInBlock(10)){block[blockLength] = 10;   blockLength++;   recursion(10);} 
			if(map[11]==map[index]&& isInBlock(11)){block[blockLength] = 11;   blockLength++;   recursion(11);} 
			if(map[18]==map[index]&& isInBlock(18)){block[blockLength] = 18;   blockLength++;   recursion(18);} 
		}
		else if(index==16){
			if(map[12]==map[index]&& isInBlock(12)){block[blockLength] = 12;   blockLength++;   recursion(12);} 
			if(map[13]==map[index]&& isInBlock(13)){block[blockLength] = 13;   blockLength++;   recursion(13);} 
			if(map[19]==map[index]&& isInBlock(19)){block[blockLength] = 19;   blockLength++;   recursion(19);} 
		}
		else if(index==17){
			if(map[14]==map[index]&& isInBlock(14)){block[blockLength] = 14;   blockLength++;   recursion(14);} 
			if(map[18]==map[index]&& isInBlock(18)){block[blockLength] = 18;   blockLength++;   recursion(18);} 
			if(map[20]==map[index]&& isInBlock(20)){block[blockLength] = 20;   blockLength++;   recursion(20);} 
		}
		else if(index==18){
			if(map[15]==map[index]&& isInBlock(15)){block[blockLength] = 15;   blockLength++;   recursion(15);} 
			if(map[17]==map[index]&& isInBlock(17)){block[blockLength] = 17;   blockLength++;   recursion(17);} 
			if(map[19]==map[index]&& isInBlock(19)){block[blockLength] = 19;   blockLength++;   recursion(19);} 
			if(map[20]==map[index]&& isInBlock(20)){block[blockLength] = 20;   blockLength++;   recursion(20);} 
		}
		else if(index==19){
			if(map[16]==map[index]&& isInBlock(16)){block[blockLength] = 16;   blockLength++;   recursion(16);} 
			if(map[18]==map[index]&& isInBlock(18)){block[blockLength] = 18;   blockLength++;   recursion(18);} 
			if(map[20]==map[index]&& isInBlock(20)){block[blockLength] = 20;   blockLength++;   recursion(20);} 
		}
		else if(index==20){
			if(map[17]==map[index]&& isInBlock(17)){block[blockLength] = 17;   blockLength++;   recursion(17);} 
			if(map[18]==map[index]&& isInBlock(18)){block[blockLength] = 18;   blockLength++;   recursion(18);} 
			if(map[19]==map[index]&& isInBlock(19)){block[blockLength] = 19;   blockLength++;   recursion(19);} 
		}	      
    }

    public  boolean hasQi(){
        for(int index = 0;index < blockLength; index++){
        	if(block[index] == 0 ){ 	
    			if(map[1]==0){return true;} 
    			if(map[2]==0){return true;} 
    			if(map[3]==0){return true;} 			
    		}
    		else if(block[index]==1){
    			if(map[0]==0){return true;} 
    			if(map[2]==0){return true;} 
    			if(map[4]==0){return true;} 
    		}
    		else if(block[index]==2){
    			if(map[0]==0){return true;} 
    			if(map[1]==0){return true;} 
    			if(map[3]==0){return true;} 
    			if(map[5]==0){return true;} 
    		}
    		else if(block[index]==3){
    			if(map[0]==0){return true;} 
    			if(map[2]==0){return true;} 
    			if(map[6]==0){return true;} 
    		}
    		else if(block[index]==4){
    			if(map[1]==0){return true;} 
    			if(map[7]==0){return true;} 
    			if(map[8]==0){return true;} 
    		}
    		else if(block[index]==5){
    			if(map[2]==0){return true;} 
    			if(map[9]==0){return true;} 
    			if(map[10]==0){return true;} 
    			if(map[11]==0){return true;} 
    		}
    		else if(block[index]==6){
    			if(map[3]==0){return true;} 
    			if(map[12]==0){return true;} 
    			if(map[13]==0){return true;} 
    		}
    		else if(block[index]==7){
    			if(map[4]==0){return true;} 
    			if(map[8]==0){return true;} 
    			if(map[14]==0){return true;} 
    		}
    		else if(block[index]==8){
    			if(map[4]==0){return true;} 
    			if(map[7]==0){return true;} 
    			if(map[9]==0){return true;} 
    			if(map[14]==0){return true;} 
    		}
    		else if(block[index]==9){
    			if(map[5]==0){return true;} 
    			if(map[8]==0){return true;} 
    			if(map[10]==0){return true;} 
    			if(map[15]==0){return true;} 
    		}
    		else if(block[index]==10){
    			if(map[5]==0){return true;} 
    			if(map[9]==0){return true;} 
    			if(map[11]==0){return true;} 
    			if(map[15]==0){return true;} 
    		}
    		else if(block[index]==11){
    			if(map[5]==0){return true;} 
    			if(map[10]==0){return true;} 
    			if(map[12]==0){return true;} 
    			if(map[15]==0){return true;} 
    		}
    		else if(block[index]==12){
    			if(map[6]==0){return true;} 
    			if(map[11]==0){return true;} 
    			if(map[13]==0){return true;} 
    			if(map[16]==0){return true;} 
    		}
    		else if(block[index]==13){
    			if(map[6]==0){return true;} 
    			if(map[12]==0){return true;} 
    			if(map[16]==0){return true;} 
    		}
    		else if(block[index]==14){
    			if(map[7]==0){return true;} 
    			if(map[8]==0){return true;} 
    			if(map[17]==0){return true;} 
    		}
    		else if(block[index]==15){
    			if(map[9]==0){return true;} 
    			if(map[10]==0){return true;} 
    			if(map[11]==0){return true;} 
    			if(map[18]==0){return true;} 
    		}
    		else if(block[index]==16){
    			if(map[12]==0){return true;} 
    			if(map[13]==0){return true;} 
    			if(map[19]==0){return true;} 
    		}
    		else if(block[index]==17){
    			if(map[14]==0){return true;} 
    			if(map[18]==0){return true;} 
    			if(map[20]==0){return true;} 
    		}
    		else if(block[index]==18){
    			if(map[15]==0){return true;} 
    			if(map[17]==0){return true;} 
    			if(map[19]==0){return true;} 
    			if(map[20]==0){return true;} 
    		}
    		else if(block[index]==19){
    			if(map[16]==0){return true;} 
    			if(map[18]==0){return true;} 
    			if(map[20]==0){return true;}  
    		}
    		else if(block[index]==20){
    			if(map[17]==0){return true;} 
    			if(map[18]==0){return true;} 
    			if(map[19]==0){return true;} 
    		}	              
        }
        return false;
    }
}
