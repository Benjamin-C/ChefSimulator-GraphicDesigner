package data;

import shapes.GraphicalShape;

public class ShapeSaver {
	
	public ShapeSaver() {
		
	}

	public String getJsonFromShape(GraphicalShape s) {
		return "{\"" + ShapeDataKey.SHPAETYPE_KEY + "\":\"" + s.getShapeType() + "\","
				 + "\"" + ShapeDataKey.X_KEY + "\":\"" + s.getX() + "\","
				 + "\"" + ShapeDataKey.Y_KEY + "\":\"" + s.getY() + "\","
				 + "\"" + ShapeDataKey.W_KEY + "\":\"" + s.getW() + "\","
				 + "\"" + ShapeDataKey.H_KEY + "\":\"" + s.getH() + "\","
				 + "\"" + ShapeDataKey.C_KEY + "\":\"" + s.getC() + "\","
				 + "\"" + ShapeDataKey.R_KEY + "\":\"" + s.getR() + "\","
				 + "\"" + ShapeDataKey.G_KEY + "\":\"" + s.getG() + "\","
				 + "\"" + ShapeDataKey.B_KEY + "\":\"" + s.getB() + "\"}";
	}
}
