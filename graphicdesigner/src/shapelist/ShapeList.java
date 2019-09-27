package shapelist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import data.FoodState;
import graphics.Texture;
import shapes.GraphicalShape;
import shapes.Shape;
import shapes.ShapeType;

public class ShapeList extends JPanel {
	
	/**  */ private static final long serialVersionUID = -242513008438104799L;
	
	private List<ShapeListElement> elems;
	
	private Runnable onUpdate;
	
	private JPanel controls;
	
	private JButton add;
	
	public ShapeList() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		elems = new ArrayList<ShapeListElement>();

		add = new JButton("+");
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GraphicalShape ns = new GraphicalShape(ShapeType.SOLID_RECTANTLE, 0, 0, 0, 0, 0, 0, 0, 0);
				ns.createEditDialog(new Runnable() {
					
					@Override
					public void run() {
						addShape(ns);
						update();
						//me.getParent().get
					}
				});
			}
		});
		add(add);
		
		controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		controls.add(new JTextArea("ddtet"));
		add(controls);
		
		update();
	}

	public void setOnUpdate(Runnable onUpdate) {
		this.onUpdate = onUpdate;
	}
	
	public void addShape(GraphicalShape s) {
		if(s != null) {
			ShapeListElement se = new ShapeListElement(s, onUpdate, this);
			elems.add(se);
			update();
			controls.add(se);
		} else {
			System.out.println("Shape was null. Skipping");
		}
	}
	
	public void setAll(Texture t) {
		// TODO remove old shapes first
		for(Shape s : t.getList().get(FoodState.RAW)) {
			addShape(new GraphicalShape(s));
		}
	}
	public void swap(GraphicalShape s, int dir) {
		int begin = 0;
		int end = elems.size();
		if(dir > 0) {
			end = end - dir;
		} else if(dir < 0) {
			begin = begin - dir;
		} else if(dir == 0) {
			return;
		}
		int i = find(s, begin, end);
		if(i >= 0) {
			System.out.println("Moving " + i + " to " + (i + dir));
			printelems();
			ShapeListElement se = elems.get(i);
			elems.set(i, elems.get(i+dir));
			elems.set(i+dir, se);
			printelems();
			//Collections.swap(elems, i, i+dir);
			if(onUpdate != null) {
				onUpdate.run();
			}
			update();
		}
	}
	public void remove(GraphicalShape s) {
		int n = find(s);
		if(n >= 0) {
			elems.remove(n);
		}
		update();
	}
	public int find(GraphicalShape s) {
		return find(s, 0, elems.size());
	}
	public int find(GraphicalShape s, int begin, int end) {
		for(int i = begin; i < end; i++) {
			System.out.println("Searching element " + i);
			if(elems.get(i).getShape().equals(s)) {
				return i;
			}
		}
		return -1;
	}
	public void printelems() {
		String print = "ShapeLiseElement[";
		for(int i = 0; i < elems.size(); i++) {
			if(i != 0) {
				print = print + ",";
			}
			print = print + elems.get(i).getShape();
		}
		System.out.println(print);
	}
	public int shapeCount() {
		return elems.size();
	}
	public void update() {
		controls.removeAll();
		for(int i = 0; i < elems.size(); i++) {
			controls.add(elems.get(i));
		}
		try {
			SwingUtilities.getWindowAncestor(this).pack();
		} catch(NullPointerException e) {
			
		}
		if(onUpdate != null) {
			onUpdate.run();
		}
	}
	public void redrawShape() {
		if(onUpdate != null) {
			onUpdate.run();
		}
	}
	public List<ShapeListElement> getShapeList() {
		return elems;
	}
}
