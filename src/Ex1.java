import java.io.IOException;
import java.util.Arrays;

public class Ex1 {

	public static void main(String[] args) throws IOException {

		InputReder input = new InputReder("input.txt");
		Output output = new Output("output.txt");
		input.Read();
		input.setStartime();
		input.stopTime();
		output.out(input.getAlgo());
		
	

	}
}
