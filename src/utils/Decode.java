
package utils;

import model.*;
import model.Instruction;

/**
 * This class reads the input binary strings and assign them to correct
 * instruction obj
 */
public final class Decode {
	/**
	 * Decode the binary string and create the appropriate Instruction object.
	 *
	 * @throws IllegalArgumentException if value for immediate is out of bound.
	 */
	public static Instruction decodeInstruction(final String hex) throws IllegalArgumentException {
		String binaryString = Converter.hexToBinary(hex).replace(" ", "");

		if (binaryString.substring(0,8).equals("00000000")) { // Stop instruction
			return new Stop("0000", "0000");
		}
		String operand = binaryString.substring(8, 24);
		
		for (int i = 7; i >= 0; i--) {
			String possibleOpcode = binaryString.substring(0, i);
			switch (possibleOpcode) {
			case "0000010":
			case "0000011":
				//Not r
			case "0000100":
				//Negate r
			case "0000101":
				//Arithmetic shift L
			case "0000110":
				//Arithmetic shift R
			case "0000111":
				//Rotate L
			case "0001000":
				//Rotate R
			case "0001001":
				return new Branch(possibleOpcode, "", "" + binaryString.charAt(7), operand);
			case "0001010":
				return new Branch(possibleOpcode, "", "" + binaryString.charAt(7), operand);
			case "0001011":
				return new Branch(possibleOpcode, "", "" + binaryString.charAt(7), operand);
			case "0001100":
				return new Branch(possibleOpcode, "", "" + binaryString.charAt(7), operand);
			case "0001101":
				return new Branch(possibleOpcode, "", "" + binaryString.charAt(7), operand);
			case "0001110":
				return new Branch(possibleOpcode, "", "" + binaryString.charAt(7), operand);
			case "0001111":
				return new Branch(possibleOpcode, "", "" + binaryString.charAt(7), operand);
			case "0010000":
				return new Branch(possibleOpcode, "", "" + binaryString.charAt(7), operand);
			case "0010001":
				return new Branch(possibleOpcode, "", "" + binaryString.charAt(7), operand);
			case "00101":
			case "00110":
			case "00111":
			case "01000":
			case "01001":
				return new CharIn(possibleOpcode, binaryString.substring(5, 8), operand);
			case "01010":
				return new CharOut(possibleOpcode, binaryString.substring(5, 8), operand);
			case "01011":
				return new CharOut(possibleOpcode, binaryString.substring(5, 8), operand);
			case "0110":
			case "0111":
				return new Addr(possibleOpcode, "" + binaryString.charAt(4), binaryString.substring(5, 8), operand);
			case "1000":
				return new Subtract(possibleOpcode, "" + binaryString.charAt(4), binaryString.substring(5, 8), operand);
			case "1001":
				return new ANDr(possibleOpcode, "" + binaryString.charAt(4), binaryString.substring(5, 8), operand);
			case "1010":
				return new ORr(possibleOpcode, "" + binaryString.charAt(4), binaryString.substring(5, 8), operand);
			case "1011":
				//Compare
			case "1100":
				return new Ldr(possibleOpcode, "" + binaryString.charAt(4), binaryString.substring(5, 8), operand);
			case "1101":
				return new Ldr(possibleOpcode, "" + binaryString.charAt(4), binaryString.substring(5, 8), operand);
			case "1110":
				return new Str(possibleOpcode, "" + binaryString.charAt(4), binaryString.substring(5, 8), operand);
			case "1111":
				return new Str(possibleOpcode, "" + binaryString.charAt(4), binaryString.substring(5, 8), operand);
			}

		}
		return null;
	}
}
