package model;

import utils.Converter;

public class CharOut extends Instruction {

	private static String output = "";

	public CharOut(String opCode, String operand) {
		super(opCode, operand);
	}

	public CharOut(String opCode, String registerSpec, String operand) {
		super(opCode, registerSpec, operand);
	}

	@Override
	public void execute(ControlUnit controlUnit) {
		String operand = controlUnit.getNextInstruction().getOperand();
		if (this.getRegisterSpecifier().equals("001")) {	
			char character = (char) Converter.binToDecimal(operand);
			output += character;
		} else if (this.getRegisterSpecifier().equals("000")) {
			MemoryDump memory = controlUnit.getMemoryDump();
			char character = (char) Converter.hexToDecimal(memory.getMemory(Converter.binToDecimal(operand)));
			output += character;
		}
	}

	public String getOutput() {
		return output;
	}
}
