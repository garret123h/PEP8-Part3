package controller;

import model.ControlUnit;
import utils.Converter;
import view.SimulatorWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Simulator implements Observer {

	private ControlUnit controlUnit;
	private SimulatorWindow window;

	public Simulator() throws IOException {
		JFrame frame = new JFrame();
		frame.setBackground(Color.BLACK);
		window = new SimulatorWindow(new ControlUnit(window).memoryDump);
		controlUnit = new ControlUnit(window);
		window.addObserver(this);
		window.addObserver(controlUnit);
		frame.add(window.getMainPanel());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		new Thread( new Runnable() {
            @Override
            public void run()
            {
				String objectCode = window.getObjectCodeArea().getText().replace("\n", "").replace(" ", "");
				String binaryCode = window.getBinCodeArea().getText().replace("\n", "").replace(" ", "");
				if(binaryCode.equals("") || binaryCode == null) {
					binaryCode = Converter.hexToBinary(objectCode);
					window.setBinCodeArea(binaryCode);
					controlUnit.memoryDump.updateMemory(objectCode);
					window.getMemoryArea().setText(controlUnit.memoryDump.toString());
					window.getMemoryArea().setCaretPosition(0);
					try {
						controlUnit.startCycle();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					objectCode = Converter.binToHex(binaryCode);
					window.setObjectCodeArea(objectCode);
					controlUnit.memoryDump.updateMemory(objectCode);
					window.getMemoryArea().setText(controlUnit.memoryDump.toString());
					window.getMemoryArea().setCaretPosition(0);
					try {
						controlUnit.startCycle();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				// Update the GUI components when fetch-execute cycle is finished.
				window.setMemoryDump(controlUnit.memoryDump);
            }}).start();
	}
}
