

public class Color {


	private  boolean[] Red;
	private  boolean[] Black;
	private  boolean[] Green;
	private final  int price_Red = 30;
	private final  int price_Green = 1;
	private int numOfBlack;

	public Color (int row , int col) {
		Red = new boolean[row*col];
		Black = new boolean[row*col];
		Green = new boolean[row*col];
		numOfBlack = 0 ; 
	}

	// color : 0 = black , 1 =red , 2 = green 
	public void insertColor(int position , int color) {
		switch (color) {
		case 0:
			Black[position] = true;
			numOfBlack++;
			break;
		case 1:
			Red[position] = true;
			break;
		case 2:
			Green[position] = true;
		default:
			break;
		}
	}
	public int getPrice(int number) {
		int price = 0; 
		switch (RedOrGreen(number)) {
		case 1:
			price = price_Red;
			break;
		case 2:
			price = price_Green;
			break;
		default:
			break;
		}
		return price;
	}
	public  int RedOrGreen(int position) {
		if(Red[position]) {return 1;}
		else return 2;
	}
    public  boolean isBlack(int position) {
    	return Black[position];
    }
    public int numOfBlack() {
    	return this.numOfBlack;
    }



	}

