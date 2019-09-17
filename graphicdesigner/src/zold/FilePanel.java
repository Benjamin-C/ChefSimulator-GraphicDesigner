package zold;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import data.ShapeLoader;
import data.ShapeSaver;
import dialog.AreYouSureDialog;
import dialog.AreYouSureDialogRunnable;
import dialog.MessageDialog;
import shapelist.ShapeList;
import shapelist.ShapeListElement;

public class FilePanel extends JPanel {
	
	/**  */ private static final long serialVersionUID = 3123508518565453454L;

	public FilePanel(ShapeList list) {
		super();
		
		JButton openbutton = new JButton("Load");
		JButton savebutton = new JButton("Save");
		
		JFileChooser fc = new JFileChooser();
		
		add(openbutton);
		openbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AreYouSureDialog("Open file?", "Are you sure you want to open a new file?\nThis will delte any unsaved work", new AreYouSureDialogRunnable() {
					@Override public void yes() {
						//Handle open button action.
						fc.setApproveButtonText("Load");
						int returnVal = fc.showOpenDialog(FilePanel.this);

				        if (returnVal == JFileChooser.APPROVE_OPTION) {
				        	File file = fc.getSelectedFile();
				        	ShapeLoader sload = new ShapeLoader();
				        	sload.loadShapeListFromFile(file, list);
				        } else {
				        	System.out.println("Open command cancelled by user.");
				        }
					}
					@Override public void no() { } });
		}});
		savebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    //Handle open button action.
				fc.setApproveButtonText("Save");
		        int returnVal = fc.showOpenDialog(FilePanel.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            System.out.println("Saving to: " + file.getName() + ".");
		            try {
		            	PrintWriter pr = new PrintWriter(file);
		            	ShapeSaver ssave = new ShapeSaver();
						for(ShapeListElement s : list.getShapeList()) {
							pr.println(ssave.getJsonFromShape(s.getShape()));
						}
						pr.flush();
						pr.close();
						System.out.println("Done");
						new MessageDialog("Save texture", "Saving complete to " + file.getPath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        } else {
		        	System.out.println("Saving command cancelled by user.");
		        }
			}
			});		
	add(savebutton);
	}
}
