package data;

import shapes.Shape;

public class ShapeSaver {
	
	public ShapeSaver() {
		
	}

	public String getJsonFromShape(Shape s) {
		return "{\"" + ShapeDataKey.SHPAETYPE_KEY + "\":\"" + s.getShapeType() + "\","
				 + "\"" + ShapeDataKey.X_KEY + "\":\"" + s.getX() + "\","
				 + "\"" + ShapeDataKey.Y_KEY + "\":\"" + s.getY() + "\","
				 + "\"" + ShapeDataKey.W_KEY + "\":\"" + s.getW() + "\","
				 + "\"" + ShapeDataKey.H_KEY + "\":\"" + s.getH() + "\","
				 + "\"" + ShapeDataKey.R_KEY + "\":\"" + s.getR() + "\","
				 + "\"" + ShapeDataKey.G_KEY + "\":\"" + s.getG() + "\","
				 + "\"" + ShapeDataKey.B_KEY + "\":\"" + s.getB() + "\"}";
	}
}
