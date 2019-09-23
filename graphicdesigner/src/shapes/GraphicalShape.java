package shapes;

import dialog.CreationDialog;
import dialog.CreationDialogDoneRunnable;

public class GraphicalShape extends Shape {

	public GraphicalShape(ShapeType shp, float x, float y, float w, float h, int r, int g, int b) {
		super(shp, x, y, w, h, r, g, b);
		// TODO Auto-generated constructor stub
	}
	public void createEditDialog(Runnable onDone) {
		createEditDialog(onDone, "Change shape");
	}
	public void createEditDialog(Runnable onDone, String title) {
		String[] labels = {"X", "Y", "W", "H", "R", "G", "B"};
		String[] text = new String[7]; // Change this line if you add/subtract elements from the dialog
		text[0] = Float.toString(x);
		text[1] = Float.toString(y);
		text[2] = Float.toString(w);
		text[3] = Float.toString(h);
		text[4] = Integer.toString(r);
		text[5] = Integer.toString(g);
		text[6] = Integer.toString(b);
		
		new CreationDialog(title, new CreationDialogDoneRunnable() {
			@Override public void done(ShapeType sh, float[] vals) {
				shp = sh;
				setSize(vals[0], vals[1], vals[2], vals[3]);
				setColor((int) vals[4], (int) vals[5], (int) vals[6]);
				if(onDone != null) {
					onDone.run();
				}
			}
		}, shp, labels, text);
	}
}
