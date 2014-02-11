
public class BrainReader {
	
	public String[] lex(String input){
		String[] tokenlist = input.trim().split("(?=[\\s-+*/^(){}<>=,:;])|(?<=[\\s-+*/^(){}<>=,:;])");
		return tokenlist;
	}
	
	public void parse(String[] tokenlist){
		for(int i = 0; i<tokenlist.length;i++) {
			if(tokenlist[i].equals("")) {
				continue;
			}
			if(tokenlist[i].matches("\\s")) {
				continue;
			}
		}
	}
}
