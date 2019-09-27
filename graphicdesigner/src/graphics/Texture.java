package graphics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.FoodState;
import shapes.Shape;

public class Texture {
	
	private Map<FoodState, List<Shape>> txtr;
	
	public Texture() {
		txtr = new HashMap<FoodState, List<Shape>>();
	}
	
	public void put(FoodState state, List<Shape> s) {
		txtr.put(state, s);
	}
	
	public List<Shape> get(FoodState state) {
		if(txtr.containsKey(state)) {
			return txtr.get(state);
		}
		return null;
	}
	
	public int size() {
		return txtr.size();
	}
	
	public Map<FoodState, List<Shape>> getList() {
		return txtr;
	}
	
	public void printAll() {
		for(FoodState f : FoodState.values()) {
			System.out.println(f + " " + get(f));
		}
	}
}
