package shootTrainer;


public class LokalKoordinatsystem {
	   int maxX = 0;
	   int maxY = 0;
	   int center_x = 0;
	   int center_y = 0;
    
	public LokalKoordinatsystem(int in_maxX,int in_maxY) {
		// Constructor
		   maxX = in_maxX;
		   maxY = in_maxY;
		   center_x = maxX /2;
		   center_y = maxY /2;
	}
	
	public int relX(int x){
		// relativ X
		return center_x + x;
	}
	public int relY(int y){
		// relativ Y
		return center_y + y;
	}
	
}
