
import java.io.FileWriter;
import java.io.IOException;


public class Output {
    private String out;
    private FileWriter writer;
	public Output(String out) {
		this.out = out;
	}
	public void out(Algorithem algo) throws IOException {
    writer = new FileWriter(out, true); 
    String Path = algo.getPath();
    String Num = "Num : " + algo.getNum();
    String Cost = "Cost : " + algo.getTotalPrice();
    if(algo.getTotalPrice() > 0 && algo.isExistPath()) {
    writer.write(Path + "\n" + Num + "\n" + Cost + "\n" + algo.getTime() + "\n");
    }
    else if(algo.isExistPath()) {writer.write("the initial State is the Goal" + "\n" + Num);}
    else {
    writer.write("no path"+ "\n" + Num );	
    }
    this.writer.close();
	}
	
}
