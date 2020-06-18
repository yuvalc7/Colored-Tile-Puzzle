import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;



public class InputReder {

	private  boolean [] setColorforvalues;
	private  State InitialState;
	private  Algorithem algorithem;
	private String path;
	private Color color; 
	private boolean time = true;
	private long StartTime , EndTime;
	private String [] splitCurrentLine;
	private String currentLine;
	public String Time = ""; 
	
	
	//constructor
    public InputReder(String path) {
		this.path = path;
	}

	public void Read() throws NumberFormatException, IOException {
		File inputFile = new File(this.path);
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		int numberLine = 1 ;
		while((currentLine = reader.readLine()) != null) {
			currentLine = currentLine.trim();
			switch (numberLine) {
			case 1: // name of algorithm 
				setAlgorithem(currentLine);
				break;	
			case 2: // with or without time
				if(currentLine.equals("no time")) {time = false;}
				break;
			case 3: // with or without open list
				if(currentLine.equals("no open")) {this.algorithem.setIsOpenList(false);}
				break;	
			case 4: // size of board
				setSizeofBoard();
				break;
			case 5: // black tile 
				setRedOrBlackColor(currentLine, 0);
				break;	
			case 6: // red tile
				setRedOrBlackColor(currentLine, 1);
				setGreen();
				break;
			default: // set position for each Block 
				splitCurrentLine = currentLine.split(",");
				setInitialPosition(numberLine-7 , splitCurrentLine);
			}
			numberLine++;	
		}

		reader.close();
	}
	
	
	private void setSizeofBoard() {
		splitCurrentLine = currentLine.split("x");
		int row = Integer.parseInt(splitCurrentLine[0]);
		int column = Integer.parseInt(splitCurrentLine[1]);
		color = new Color(row, column);
		setColorforvalues = new boolean [row*column];
		algorithem.setGoal(row , column);
		InitialState = new State(row, column);
	} 
	
	private void setRedOrBlackColor(String currentLine , int color ) {
		currentLine = (currentLine.substring(currentLine.indexOf(':') + 1) != null) ? currentLine.substring(currentLine.indexOf(':') + 1) : "";
		currentLine = currentLine.trim();
		if(!currentLine.isEmpty()) { 
			splitCurrentLine = (currentLine.contains(",")) ? currentLine.split(",") : currentLine.split(" ");
			setColor( color , splitCurrentLine );
		} 
	}

	private  void  setColor(int Color , String [] values) {
		int parse ;
		for(String value : values) {
			parse = Integer.parseInt(value);	
			color.insertColor(parse, Color);
			setColorforvalues[parse] = true;
		}
	}

	private  void setGreen() {
		for(int i = 1 ; i < setColorforvalues.length ; i++) {
			if(!setColorforvalues[i]) {
				color.insertColor(i, 2);
			}
		}
	}

	/**
	 * build initial state and call setTile function
	 * @param row -  specific row where the tile
	 * @param values - number value of tile in the specific row
	 */
	private void setInitialPosition(int row ,String [] values) {
		int value;
		for(int i = 0; i < values.length ; i++) {
			if(values[i].equals("_")) {//blank
				InitialState.insert(row, i, 0); 
			}
			else {
				value = Integer.parseInt(values[i]);
				InitialState.insert(row, i, value);
			}
		}

	}
    
	/**
	 * initial the specific algorithm
	 * @param algo - the algorithm
	 */
	private  void setAlgorithem (String algo) {
		switch (algo) {
		case "BFS":
			algorithem = new BFS();
			break;
		case "DFBnB":
			algorithem = new DFBnB();
			break;	
		case "A*":
			algorithem = new A_STAR();
			break;
		case "DFID":
			algorithem = new DFID();
			break;		 
		case "IDA*":
			algorithem = new IDA_STAR();
			break;
		default:
			break;
		}
	}
	
    /**
     * set color and tile to algorithm , start time and run the algorithm
     */
	public void setStartime() {
		InitialState.set_HashCode();
		algorithem.setColor(color);
		StartTime = System.nanoTime();
		algorithem.setAlgorithem(InitialState);
		algorithem.runAlgorithem();
	} 

    public  void stopTime() {
		EndTime = System.nanoTime();
		if(time) {
			Time = Double.toString((double)((EndTime - StartTime)/1_000_000_000.0)).substring(0,5);
			this.algorithem.setTime(Time);
		}
	}

	public Algorithem getAlgo() {
		return this.algorithem;
	}  
}
