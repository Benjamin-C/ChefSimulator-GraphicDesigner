package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dialog.MessageDialog;
import shapelist.ShapeList;
import shapes.Shape;
import shapes.ShapeType;
import data.ShapeDataKey;

public class ShapeLoader {
	
	public ShapeLoader() {
		
	}
	//  Methods
	/**
	 * Turns a JSON string into a Shape
	 * Must recive JSON in format {"key":"value","key":"value"}
	 * EX: {"shapetype":"solid_rectangle","x":"0.0","y":"0.0","w":"0.0","h":"0.0","r":"0","g":"0","b":"0"}
	 * 
	 * @param json the String JSON to interpert
	 * @return the Shape the JSON represents
	 */
		
	private boolean loadError = false;
	private String loadErrorText;
	
	public ShapeList loadShapeListFromFile(File file, ShapeList list) {
		if(list == null) {
			throw new NullPointerException("Must specify a ShapeList to load to from the file");
		}
		System.out.println("Opening: " + file.getName() + ".");
    	try {
			Scanner s = new Scanner(file);
			list.getShapeList().clear();
			ShapeLoader sload = new ShapeLoader();
			while(s.hasNextLine()) {
				list.addShape(sload.getShapeFromJSON(s.nextLine(), true, new Runnable() {
					@Override public void run() { list.redrawShape(); }}));
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public Shape getShapeFromJSON(String json) {
		return getShapeFromJSON(json, false, null);
	}
	public Shape getShapeFromJSON(String json, boolean mayFix, Runnable onFix) {
		// {"shapetype":"solid_rectangle","x":"0.0","y":"0.0","w":"0.0","h":"0.0","r":"0","g":"0","b":"0"}
		
		if(json.charAt(0) == '{' && json.charAt(json.length()-1) == '}' && json.length() > 2) {
			json = json.substring(1,json.length()-1);
			String bitsStr[] = json.split(",");
			Map<String, String> bits = new HashMap<String, String>();
			for(String s : bitsStr) {
				String parts[] = s.split(":");
				if(parts.length == 2
						&& parts[0].charAt(0) == '\"' && parts[0].charAt(parts[0].length()-1) == '\"'
						&& parts[1].charAt(0) == '\"' && parts[1].charAt(parts[1].length()-1) == '\"')
				{
					bits.put(parts[0].substring(1, parts[0].length()-1), parts[1].substring(1, parts[1].length()-1));
				} else {
					new MessageDialog("Image loading issues", "Error loading " + s + ". Skipping.");
				}
			}
			
			loadError = false; loadErrorText = "";
			
			float nx = getFloatValue(ShapeDataKey.X_KEY, bits);
			float ny = getFloatValue(ShapeDataKey.Y_KEY, bits);
			float nw = getFloatValue(ShapeDataKey.W_KEY, bits);
			float nh = getFloatValue(ShapeDataKey.H_KEY, bits);
			int nr = getColorValue(ShapeDataKey.R_KEY, bits);
			int ng = getColorValue(ShapeDataKey.G_KEY, bits);
			int nb = getColorValue(ShapeDataKey.B_KEY, bits);
			ShapeType nt = ShapeType.SOLID_RECTANTLE;
			if(bits.containsKey(ShapeDataKey.SHPAETYPE_KEY)) {
				nt = ShapeType.getShapeTypeFromString(bits.get(ShapeDataKey.SHPAETYPE_KEY));
				if(nt == null) { loadError = true; loadErrorText = loadErrorText + " " + ShapeDataKey.SHPAETYPE_KEY; nt = ShapeType.SOLID_RECTANTLE; System.out.println("Could not load " + ShapeDataKey.SHPAETYPE_KEY + " (ShapeType " + bits.get(ShapeDataKey.SHPAETYPE_KEY) + " is not valid). Returning default value SOLID_RECTANGLE"); }
			} else { loadError = true; loadErrorText = loadErrorText + " " + ShapeDataKey.SHPAETYPE_KEY; System.out.println("Could not load " + ShapeDataKey.SHPAETYPE_KEY + " (Key not found). Returning default value SOLID_RECTANGLE"); }
			Shape back = new Shape(nt, nx, ny, nw, nh, nr, ng, nb);
			
			if(loadError & mayFix) {
				back.createEditDialog(onFix, "Please fix:" + loadErrorText);
			}
			return back;
		} else {
			new MessageDialog("Image loading issues", "Error loading JSON.\nIt may be missing curley brackets\nor data. Skipping. Error JSON:\n" + json);
		}
		return null;
	}
	
	private int getColorValue(String key, Map<String, String> map) {
		return Math.min(Math.max(getIntValue(key, map), 0), 255);
	}
	private int getIntValue(String key, Map<String, String> map) {
		if(map.containsKey(key)) {
			try {
				return Integer.parseInt(map.get(key));
			} catch(NumberFormatException e) {
				loadError = true; loadErrorText = loadErrorText + " " + key;
				System.out.println("Could not load " + key + " (NumberFormatException). Returning default value 0");
			}
		} else {
			loadError = true; loadErrorText = loadErrorText + " " + key;
			System.out.println("Could not load " + key + " (Key not found). Returning default value 0");
		}
		return 0;
	}
	private float getFloatValue(String key, Map<String, String> map) {
		if(map.containsKey(key)) {
			try {
				return Float.parseFloat(map.get(key));
			} catch(NumberFormatException e) {
				loadError = true; loadErrorText = loadErrorText + " " + key;
				System.out.println("Could not load " + key + " (NumberFormatException). Returning default value 0");
			}
		} else {
			loadError = true; loadErrorText = loadErrorText + " " + key;
			System.out.println("Could not load " + key + " (Key not found). Returning default value 0");
		}
		return 0;
	}

}
