package ro.ctrl.javafx.calculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import ro.ctrl.javafx.calculator.operations.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class CalculatorController {

    @FXML
    public TextArea calculatorOperationsArea;
    @FXML
    public Label errorsLabel;

    private void writeDigit (String digit){
        checkNewOperation();
        if (replaceZero(digit)) {
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat(digit));
        }
        setPositionCaret();
    }

    private void setPositionCaret() {
        calculatorOperationsArea.positionCaret(calculatorOperationsArea.getText().length());
    }



    @FXML
    public void writeOne(ActionEvent actionEvent) {
        writeDigit("1");
    }


    @FXML
    public void writeTwo(ActionEvent actionEvent) {
        writeDigit("2");
    }

    @FXML
    public void writeThree(ActionEvent actionEvent) {
        writeDigit("3");
    }

    @FXML
    public void writeFour(ActionEvent actionEvent) {
        writeDigit("4");
    }

    @FXML
    public void writeFive(ActionEvent actionEvent) {
        writeDigit("5");
    }

    @FXML
    public void writeSix(ActionEvent actionEvent) {
        writeDigit("6");
    }

    @FXML
    public void writeSeven(ActionEvent actionEvent) {
        writeDigit("7");
    }

    @FXML
    public void writeEight(ActionEvent actionEvent) {
        writeDigit("8");
    }

    @FXML
    public void writeNine(ActionEvent actionEvent) {
        writeDigit("9");
    }

    @FXML
    public void writeZero(ActionEvent actionEvent) {
        checkNewOperation();
        if(!calculatorOperationsArea.getText().equalsIgnoreCase("0")){
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("0"));
        }
        setPositionCaret();

    }

    private boolean replaceZero (String replacement){
        boolean zeroReplaced = false;
        if (calculatorOperationsArea.getText().equalsIgnoreCase("0")) {
            calculatorOperationsArea.setText(replacement);
            zeroReplaced = true;
        }
        return !zeroReplaced;
    }

    @FXML
    public void writeComma(ActionEvent actionEvent) {
        if(!commaAlreadyPresetOnOperand(calculatorOperationsArea.getText())){
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("."));
        }
        setPositionCaret();

    }

    private boolean commaAlreadyPresetOnOperand(String text) {
        if(mathOperationsNotPresent()){
            return text.contains("."); // verificam operandul din partea stanga a operatorului
        } else { // verificam operandul din dreapta

            String[] operands =  {};
            for (String mathOperation : operationsCharacters){
                if( operands.length == 2){
                    break;
                }
                operands = splitOperation(text, mathOperation);
            }
            return operands[1].contains(".");


        }
    }


    private void checkNewOperation() {
        if(calculatorOperationsArea.getText().contains("=")){
            calculatorOperationsArea.setText("");
        }
    }

    @FXML
    public void addition(ActionEvent actionEvent) {
        if(mathOperationsNotPresent()) {
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("+"));
        }
        setPositionCaret();
    }

    @FXML
    public void substraction(ActionEvent actionEvent) {
        if(mathOperationsNotPresent()) {
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("-"));
        }
        setPositionCaret();
    }

    @FXML
    public void division(ActionEvent actionEvent) {
        if(mathOperationsNotPresent()) {
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("/"));
        }
        setPositionCaret();
    }

    @FXML
    public void multiplication(ActionEvent actionEvent) {
        if(mathOperationsNotPresent()) {
            calculatorOperationsArea.setText(calculatorOperationsArea.getText().concat("x"));
        }
        setPositionCaret();
    }

    @FXML
    public void clearCalculatorOperationsArea(ActionEvent actionEvent) {
        calculatorOperationsArea.setText("");

    }

    private boolean mathOperationsNotPresent() {
        return !calculatorOperationsArea.getText().contains("+") &&
                !calculatorOperationsArea.getText().contains("-") &&
                !calculatorOperationsArea.getText().contains("/") &&
                !calculatorOperationsArea.getText().contains("x");
    }


    @FXML
    public void evaluate(ActionEvent actionEvent) {
        String operation = calculatorOperationsArea.getText();
        if(!operation.isEmpty()){
            if (operation.contains("+")){
                performAddition(operation);
            } else if (operation.contains("-")){
                performSubtraction(operation);

            } else if (operation.contains("x")){
                performMultiplication(operation);

            } else if (operation.contains("/")) {
                performDivision(operation);
            }

            else {
                errorsLabel.setText("Operatie necunoscuta!");
            }

        }
    }

    private void performAddition(String operation) {
        String operands[] = splitOperation(operation, "+");
        if(operands.length == 2){
            doOperation(operands, Operation.ADDITION);
        }
    }

    private void performSubtraction(String operation) {
        String operands[] = splitOperation(operation, "-");
        if(operands.length == 2){
            doOperation(operands, Operation.SUBSTRACTION);
        }
    }

    private void performMultiplication(String operation) {
        String operands[] = splitOperation(operation, "x");
        if(operands.length == 2){
            doOperation(operands, Operation.MULTIPLICATION);
        }
    }

    private void performDivision(String operation) {
        String operands[] = splitOperation(operation, "/");
        if(operands.length == 2){
            doOperation(operands, Operation.DIVISION);
        }
    }



    private String[] splitOperation(String operation, String splitter) {
        String [] operands = {};
        try{
            if(Arrays.asList("+", "-", "x", "/").contains(splitter)){
                operation = operation.replace(splitter, "----");
            }
            operands = operation.split("----");

        }catch (Exception ex){
            errorsLabel.setText("Operanzi nedetectati");
            ex.printStackTrace();
        }
        // errorsLabel.setText(operands[0] + " " + operands[1]);
        return operands;
    }




    private void doOperation(String[] operands, Operation operation) {
        try {
            BigDecimal firstOperand = new BigDecimal(cleanOperand(operands[0]));
            BigDecimal secondOperand = new BigDecimal(cleanOperand(operands[1]));

            switch (operation){
                case ADDITION:
                    writeResult(firstOperand.add(secondOperand));
                    break;
                case SUBSTRACTION:
                    writeResult(firstOperand.subtract(secondOperand));
                    break;
                case MULTIPLICATION:
                    writeResult(firstOperand.multiply(secondOperand));
                    break;
                case DIVISION:
                    writeResult(firstOperand.divide(secondOperand, RoundingMode.HALF_DOWN));
                    break;
            }

        } catch (NumberFormatException nfe){
            errorsLabel.setText("Operanzi gresiti");
        }
    }

    private String cleanOperand(String operand){
        return operand.replaceAll("\n", "");
    }

    private void writeResult(BigDecimal result) {
        calculatorOperationsArea.setText(calculatorOperationsArea.getText()
                .concat(" = ")
                .replaceAll("\n","")
                .replaceAll("\r","")
                .concat(result.toString()));
    }


    @FXML
    public void handleKeyTyped(KeyEvent keyEvent) {
        if(allowedCharacter(keyEvent.getCharacter())){

            handleDigitCharacter(keyEvent);
            handleComma(keyEvent);
            handleOperations(keyEvent);
            handleEvaluationKeys(keyEvent);


        } else {
            keyEvent.consume();
        }
    }

    private void handleEvaluationKeys(KeyEvent keyEvent) {
        if (keyEvent.getCharacter().equalsIgnoreCase("=") || keyEvent.getCharacter().equalsIgnoreCase("\r")){
            keyEvent.consume();
            evaluate(new ActionEvent());
        }
    }

    private void handleOperations(KeyEvent keyEvent) {
        if(operationCharacter(keyEvent.getCharacter())){
            if(!mathOperationsNotPresent()){
               keyEvent.consume();
            }
      }
    }

    private void handleComma(KeyEvent keyEvent) {
        if (keyEvent.getCharacter().equalsIgnoreCase(".")){
            writeComma(new ActionEvent());
            keyEvent.consume();
        }
    }

    private void handleDigitCharacter(KeyEvent keyEvent) {
        if(isDigitCharacter(keyEvent.getCharacter())){
            switch (keyEvent.getCharacter()){
                case "0":
                    writeZero(new ActionEvent());
                    break;
                case "1":
                    writeOne(new ActionEvent());
                    break;
                case "2":
                    writeTwo(new ActionEvent());
                    break;
                case "3":
                    writeThree(new ActionEvent());
                    break;
                case "4":
                    writeFour(new ActionEvent());
                    break;
                case "5":
                    writeFive(new ActionEvent());
                    break;
                case "6":
                    writeSix(new ActionEvent());
                    break;
                case "7":
                    writeSeven(new ActionEvent());
                    break;
                case "8":
                    writeEight(new ActionEvent());
                    break;
                case "9":
                    writeNine(new ActionEvent());
                    break;


            }

            keyEvent.consume();



        }
    }

    private List<String> allowedCharacters = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "x",".", "/","=", "\r", "\n" );

    private boolean allowedCharacter(String character){
        return allowedCharacters.contains(character);
    }

    private List<String> operationsCharacters = Arrays.asList("+", "-", "x", "/");

    private boolean operationCharacter(String character){
        return operationsCharacters.contains(character);
    }

    private List<String> digitCharacter = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    private boolean isDigitCharacter (String character){
        return digitCharacter.contains(character);
    }
}
