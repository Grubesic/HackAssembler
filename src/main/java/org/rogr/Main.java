package org.rogr;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.stream.Stream;

public class Main {

    private static String ART = " _   _    _    ____ _  __     _                           _     _           \n" +
            "| | | |  / \\  / ___| |/ /    / \\   ___ ___  ___ _ __ ___ | |__ | | ___ _ __ \n" +
            "| |_| | / _ \\| |   | ' /    / _ \\ / __/ __|/ _ \\ '_ ` _ \\| '_ \\| |/ _ \\ '__|\n" +
            "|  _  |/ ___ \\ |___| . \\   / ___ \\\\__ \\__ \\  __/ | | | | | |_) | |  __/ |   \n" +
            "|_| |_/_/   \\_\\____|_|\\_\\ /_/   \\_\\___/___/\\___|_| |_| |_|_.__/|_|\\___|_|  ";

    private HashMap<String, String> symbolsTable = new HashMap<>();

    private static int counter = 0;
    private static int variableCounter = 16;


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar <jarfile> <inputfile.asm> ");
        }
        System.out.println(ART);
        Main program = new Main();
        program.compile(args[0]);
    }

    public Main(){
        symbolsTable.put("SP", "0");
        symbolsTable.put("LCL", "1");
        symbolsTable.put("ARG", "2");
        symbolsTable.put("THIS", "3");
        symbolsTable.put("THAT", "4");
        symbolsTable.put("R0", "0");
        symbolsTable.put("R1", "1");
        symbolsTable.put("R2", "2");
        symbolsTable.put("R3", "3");
        symbolsTable.put("R4", "4");
        symbolsTable.put("R5", "5");
        symbolsTable.put("R6", "6");
        symbolsTable.put("R7", "7");
        symbolsTable.put("R8", "8");
        symbolsTable.put("R9", "9");
        symbolsTable.put("R10", "10");
        symbolsTable.put("R11", "11");
        symbolsTable.put("R12", "12");
        symbolsTable.put("R13", "13");
        symbolsTable.put("R14", "14");
        symbolsTable.put("R15", "15");
        symbolsTable.put("SCREEN", "16384");
        symbolsTable.put("KBD", "24576");
    }

    public void compile(String inputPath) {

        StringBuilder builder = new StringBuilder();

        Path inputFilePath = Path.of(inputPath);
        firstPass(inputFilePath);
        try (Stream<String> lines = Files.lines(inputFilePath)) {
            lines.map(e -> e.replaceAll(" ",  "")).forEach(e -> {
                if (!e.startsWith("//") && !e.isBlank() && !e.isEmpty() && !e.startsWith("(")) {
                    String instruction;
                    if (isAInstruction(e)) {
                        instruction = handleAInstruction(e);
                    } else {
                        instruction = handleCInstruction(e);
                    }
                    builder.append(instruction);
                    builder.append("\n");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        String outputFileName = inputFilePath.getFileName().toString().replace(".asm", ".hack");
        Path outputFilePath = (inputFilePath.getParent() != null) ?
                inputFilePath.getParent().resolve(outputFileName) :
                Path.of(outputFileName);

        try {
            Files.writeString(outputFilePath,
                    builder.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isAInstruction(String s) {
        return s.startsWith("@");
    }

    public String handleAInstruction(String s) {
        StringBuilder instruction = new StringBuilder();
        String[] tokenized = s.split("@");
        instruction.append("0");
        try {
            int value = Integer.parseInt(tokenized[1]);
            String binaryString = Integer.toBinaryString(value);
            instruction.append(binaryString);
        } catch (NumberFormatException e) {
            String symbol = tokenized[1];
            if (symbolsTable.containsKey(symbol)) {
                int value = Integer.parseInt(symbolsTable.get(symbol));
                String binaryString = Integer.toBinaryString(value);
                instruction.append(binaryString);
            } else {
                symbolsTable.put(symbol, String.valueOf(variableCounter));
                int value = Integer.parseInt(symbolsTable.get(symbol));
                String binaryString = Integer.toBinaryString(value);
                instruction.append(binaryString);
                variableCounter++;
            }
        }
        while (instruction.length() != 16) {
            instruction.insert(1, "0");
        }
        return instruction.toString();
    }

    public String handleCInstruction(String s) {
        StringBuilder instruction = new StringBuilder();
        instruction.append("111");

        String destination;
        String computation;
        String jump;
        String[] tokenized = s.split("[=;]");
        if(!s.contains("=")){
            destination = "null";
            computation = tokenized[0];
            jump = tokenized[1];
        } else if(!s.contains(";")){
            destination = tokenized[0];
            computation = tokenized[1];
            jump = "null";
        } else {
            destination = tokenized[0];
            computation = tokenized[1];
            jump = tokenized[2];
        }

        //Computation
        String comp0 = Util.COMP0.get(computation);
        if(comp0 == null){
            String comp1 = Util.COMP1.get(computation);
            if(comp1 != null){
                instruction.append("1");
                instruction.append(comp1);
            } else {
                throw new IllegalArgumentException("Invalid instruction: " + s);
            }

        } else {
            instruction.append("0");
            instruction.append(comp0);
        }

        //Destination
        String binaryDestination = Util.DEST.get(destination);
        instruction.append(binaryDestination);

        //Jump
        String jmpBinary = Util.JMP.get(jump);
        instruction.append(jmpBinary);

        return instruction.toString();
    }

    private void firstPass(Path inputFilePath) {
        try (Stream<String> lines = Files.lines(inputFilePath)) {
            lines.map(e -> e.replaceAll(" ",  "")).forEach(e -> {
                if (!e.startsWith("//") && !e.isBlank() && !e.isEmpty() && !e.startsWith("(")) {
                    counter++;
                } else if (e.startsWith("(")) {
                    String label = e.substring(1, e.length() - 1);
                    symbolsTable.put(label, String.valueOf(counter));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}