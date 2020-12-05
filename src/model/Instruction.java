package model;

public abstract class Instruction {
    private String myRegisterSpecifier = "";

    private String myOperand = "";

    private String myAddressingMode = "";

    private String myOpcode = "";

    public Instruction(String opCode, String operand) {
        this.myOpcode = opCode;
        this.myOperand = operand;
    }

    public Instruction(String opCode, String registerSpec, String operand) {
        this.myOpcode = opCode;
        this.myRegisterSpecifier = registerSpec;
        this.myOperand = operand;
    }

    public Instruction(String opCode, String registerSpec, String addressingMode, String operand) {
        this.myOpcode = opCode;
        this.myRegisterSpecifier = registerSpec;
        this.myOperand = operand;
        this.myAddressingMode = addressingMode;
    }

    public abstract void execute(ControlUnit controlUnit);

    @Override
    public String toString() {
        return this.myOpcode + this.myRegisterSpecifier + this.myAddressingMode + this.myOperand;
    }

    public String getAddressingMode() {
        return (myAddressingMode == null)? "": myAddressingMode;
    }

    public String getOpcode() {
        return (myOpcode == null)? "": myOpcode;
    }
    public String getOperand() {
        return (myOperand == null)? "": myOperand;
    }
    public String getRegisterSpecifier() {
        return (myRegisterSpecifier == null)? "": myRegisterSpecifier;
    }
}
