package controller;

import model.ControlUnit;
import model.SourceDecode;
import utils.Converter;
import view.SimulatorWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Simulator implements Observer {

	private ControlUnit controlUnit;
	private SimulatorWindow window;
	private Thread cycleThread;
	private final SourceDecode dec;

	public Simulator() throws IOException {
		dec = new SourceDecode();
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
		new Thread (new Runnable() {
			public void run() {
				updateMemoryFromWindow();

				String operation = (String) arg;
				if (operation.equals("Single Step")) {
					cycleThread = new Thread(() -> {
						try {
							controlUnit.executeNextInstruction();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
					cycleThread.start();
				} else if (operation.equals("Execute")) {
					cycleThread = new Thread(() -> {
						try {
							controlUnit.startCycle();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
					cycleThread.start();
				} else if (operation.equals("Character In")) {
					window.notifyObservers();
				}

				// Update the GUI components when fetch-execute cycle is finished.
				window.setMemoryDump(controlUnit.memoryDump);
				Map<String, String> addressingBits = new HashMap<>();
				addressingBits.put("N", controlUnit.getMyNFlag() + "");
				addressingBits.put("Z", controlUnit.getMyZFlag() + "");
				addressingBits.put("V", controlUnit.getMyVFlag() + "");
				addressingBits.put("C", controlUnit.getMyCFlag() + "");
				window.setAddressingBits(addressingBits);
			}}).start();
		}
	private void updateMemoryFromWindow() {
		// String objectCode = window.getObjectCodeArea().getText().replace("\n",
		// "").replace(" ", "");
		// String srcCode = window.getSrcCodeArea().getText().replace("\n",
		// "").replace(" ", "");

		/**
		 * if (srcCode.equals("") || srcCode == null) { srcCode =
		 * Converter.hexToBinary(objectCode); window.setSrcCodeArea(srcCode); } else {
		 */
		if (!window.getSrcCodeArea().getText().equals("") || window.getSrcCodeArea().getText() != null) {
			String[] srcCode = window.getSrcCodeArea().getText().split("\\r?\\n");
			String code = "";
			try {
				String objectCode = dec.assemblyToHex(srcCode);
				System.out.println(objectCode);
				code = dec.format(objectCode);
				window.setObjectCodeArea(code);
				controlUnit.memoryDump.updateMemory(code);
				window.getMemoryArea().setText(controlUnit.memoryDump.toString());
				window.getMemoryArea().setCaretPosition(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
