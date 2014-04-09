package modelTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import model.Ant;
import model.AntBrain;
import model.AntBrainReader;
import model.Colour;
import model.Match;
import model.Player;
import model.World;
import model.WorldReader;
import model.tile.AntHillTile;
import model.tile.ClearTile;
import model.tile.RockTile;
import model.tile.Tile;

import org.junit.Test;

import controller.Game;

public class SimulationTest {

	@Test
	public void test() {
		World world = WorldReader.readWorld("tiny.world");
		assertNotNull("World file not valid/found", world);
		AntBrain brain = AntBrainReader.readBrain("sample.ant.txt");
		Player p1 = new Player("Player 1", brain);
		Player p2 = new Player("Player 2", brain);
		Match match = new Match(world, p1, p2);
		try{
			File dump = new File("dump.all");
			BufferedReader inputBuffer = new BufferedReader(new FileReader(dump));
			Game.seed = Integer.parseInt(inputBuffer.readLine().trim().substring(13));
			String line;
			int roundCount = 0;
			int lineCount = 0;
			while ((line = inputBuffer.readLine()) != null) {
				lineCount++;
				if(line.equals("")){
					continue;
				}
				if(line.substring(0, 12).equals("After round ")){
					if(roundCount != 0){
						match.nextRound();
					}
					assertEquals("Round number should be equal", Integer.parseInt(line.replace(".", "").substring(12)), match.getRoundNumber());
					roundCount++;
					continue;
				}
				if(line.substring(0, 5).equals("cell ")){
					int x = Integer.parseInt(line.substring(6, 7));
					int y = Integer.parseInt(line.substring(9, 10));
					String tileString =line.substring(13);
					Tile tile = new ClearTile();
					String[] tiledata = tileString.split("; ");
					if(tiledata[0].equals("")){
						tile = new ClearTile();
						continue;
					}
					if(tiledata[0].equals("rock")){
						tile = new RockTile();
						continue;
					}
					if(tiledata[0].substring(tiledata[0].length()-4, tiledata[0].length()).equals("food")){
						int foodCount = Integer.parseInt(tiledata[0].substring(0, tiledata[0].length()-5));
						((ClearTile)tile).setFood(foodCount);
					}
					if(tiledata[0].substring(0, 3).equals("red")){
						if(tiledata[0].equals("red hill")){
							int food = ((ClearTile)tile).getFood();
							tile = new AntHillTile(Colour.RED);
							((AntHillTile)tile).setFood(food);
						}
						if(tiledata[0].split(" ")[1].equals("marks:")){
							tile = new ClearTile();
							char[] marks = tiledata[0].split(" ")[2].toCharArray();
							for(int j = 0; j < marks.length; j++){
								((ClearTile)tile).placeMarker(Colour.RED, Integer.parseInt(""+marks[j]));
							}
						}
					}
					if(tiledata[0].substring(0, 5).equals("black")){
						if(tiledata[0].equals("black hill")){
							int food = ((ClearTile)tile).getFood();
							tile = new AntHillTile(Colour.BLACK);
							((AntHillTile)tile).setFood(food);
						}
						if(tiledata[0].split(" ")[1].equals("marks:")){
							tile = new ClearTile();
							char[] marks = tiledata[0].split(" ")[2].toCharArray();
							for(int j = 0; j < marks.length; j++){
								((ClearTile)tile).placeMarker(Colour.BLACK, Integer.parseInt(""+marks[j]));
							}
						}
					}
					for(int i = 0; i < tiledata.length; i++){
						if(tiledata[i].split(" ")[0].equals("black")){
							if(tiledata[i].split(" ")[1].equals("hill")){
								int food = ((ClearTile)tile).getFood();
								tile = new AntHillTile(Colour.BLACK);
								((AntHillTile)tile).setFood(food);
							}
							if(tiledata[i].split(" ")[1].equals("ant")){
								String[] antData = tiledata[i].split(", ");
								Ant bAnt = new Ant(p1, Integer.parseInt(antData[0].substring(16)));
								for(int j = 0; j < antData.length; j++){
									switch(antData[j].split(" ")[0]){
									case "dir":
										bAnt.setDirection(Integer.parseInt(antData[j].substring(4)));
										continue;
									case "food":
										bAnt.setFood(1 == Integer.parseInt(antData[j].substring(5)));
										continue;
									case "state":
										bAnt.setState(Integer.parseInt(antData[j].substring(6)));
										continue;
									case "resting":
										bAnt.setResting(Integer.parseInt(antData[j].substring(8)));
										continue;
									}
								}
								bAnt.setX(x);
								bAnt.setY(y);
								((ClearTile)tile).setAnt(bAnt);
							}
							if(tiledata[i].split(" ")[1].equals("marks:")){
								char[] marks = tiledata[i].split(" ")[2].toCharArray();
								for(int j = 0; j < marks.length; j++){
									((ClearTile)tile).placeMarker(Colour.BLACK, Integer.parseInt(""+marks[j]));
								}
							}
						}
						if(tiledata[i].split(" ")[0].equals("red")){
							if(tiledata[i].split(" ")[1].equals("hill")){
								int food = ((ClearTile)tile).getFood();
								tile = new AntHillTile(Colour.RED);
								((AntHillTile)tile).setFood(food);
							}
							if(tiledata[i].split(" ")[1].equals("ant")){
								String[] antData = tiledata[i].split(", ");
								Ant rAnt = new Ant(p2, Integer.parseInt(antData[0].substring(14)));
								for(int j = 0; j < antData.length; j++){
									switch(antData[j].split(" ")[0]){
									case "dir":
										rAnt.setDirection(Integer.parseInt(antData[j].substring(4)));
										continue;
									case "food":
										rAnt.setFood(1 == Integer.parseInt(antData[j].substring(5)));
										continue;
									case "state":
										rAnt.setState(Integer.parseInt(antData[j].substring(6)));
										continue;
									case "resting":
										rAnt.setResting(Integer.parseInt(antData[j].substring(8)));
										continue;
									}
								}
								rAnt.setX(x);
								rAnt.setY(y);
								((ClearTile)tile).setAnt(rAnt);
							}
							if(tiledata[i].split(" ")[1].equals("marks:")){
								char[] marks = tiledata[i].split(" ")[2].toCharArray();
								for(int j = 0; j < marks.length; j++){
									((ClearTile)tile).placeMarker(Colour.RED, Integer.parseInt(""+marks[j]));
								}
							}
						}
					}
					Tile worldTile = world.getTile(x, y);
					if(!tile.equals(worldTile)){
						System.out.println("Seed: " + Game.seed);
						System.out.println("Count: " + Game.count);
						System.out.println(x + "," + y);
						System.out.println("Round number: " + (roundCount-1) + ", " + match.getRoundNumber());
						System.out.println("Line number: " + lineCount);
						System.out.println(line);
						System.out.println("Is rocky?\t\t" + tile.isRocky() + ", " + worldTile.isRocky());
						if(!tile.isRocky()){
							System.out.println("Food? \t\t\t" + ((ClearTile)tile).getFood() + ", " + ((ClearTile)worldTile).getFood());
							for(int j = 0; j < 6; j++){
								System.out.println("\tRed Mark   "+j+"?\t" + ((ClearTile)tile).getMarker(Colour.RED, j) + ", " + ((ClearTile)worldTile).getMarker(Colour.RED, j));
								System.out.println("\tBlack Mark "+j+"?\t" + ((ClearTile)tile).getMarker(Colour.BLACK, j) + ", " + ((ClearTile)worldTile).getMarker(Colour.BLACK, j));
							}
							System.out.println("Has Ant? \t\t" + ((ClearTile)tile).hasAnt() + ", " + ((ClearTile)worldTile).hasAnt());
							if(((ClearTile)tile).hasAnt() && ((ClearTile)worldTile).hasAnt()){
								Ant tileAnt = ((ClearTile)tile).getAnt();
								Ant worldAnt = ((ClearTile)worldTile).getAnt();
								System.out.println("\tAnt brain same?\t" + tileAnt.getAntBrain().equals(worldAnt.getAntBrain()));
								System.out.println("\tAnt ID? \t" + tileAnt.getID() + ", " + worldAnt.getID());
								System.out.println("\tAnt X? \t\t" + tileAnt.getX() + ", " + worldAnt.getX());
								System.out.println("\tAnt Y? \t\t" + tileAnt.getY() + ", " + worldAnt.getY());
								System.out.println("\tAnt Colour?\t" + tileAnt.getColour() + ", " + worldAnt.getColour());
								System.out.println("\tAnt dir?\t" + tileAnt.getDirection() + ", " + worldAnt.getDirection());
								System.out.println("\tAnt food?\t" + tileAnt.hasFood() + ", " + worldAnt.hasFood());
								System.out.println("\tAnt state?\t" + tileAnt.getState() + ", " + worldAnt.getState());
								System.out.println("\tAnt resting?\t" + tileAnt.getResting() + ", " + worldAnt.getResting());
								assertTrue("Ant should match ant in world", tileAnt.equals(worldAnt));
							}
							System.out.println("Is AntHill?\t\t" + ((ClearTile)tile).isAntHill() + ", " + ((ClearTile)worldTile).isAntHill());
							if(((ClearTile)tile).isAntHill() && ((ClearTile)worldTile).isAntHill()){
								System.out.println("\tAntHill Colour? " + ((AntHillTile)tile).getColour() + ", " + ((AntHillTile)worldTile).getColour());
							}
						}
					}
					assertTrue("Tile should match the tile in world", tile.equals(world.getTile(x, y)));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
