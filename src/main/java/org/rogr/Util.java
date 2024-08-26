package org.rogr;

import java.util.Map;

public class Util {

    public static final Map<String, String> DEST = Map.of(
            "M", "001",
            "D", "010",
            "MD", "011",
            "A", "100",
            "AM", "101",
            "AD", "110",
            "AMD", "111",
            "null", "000"
    );

    public static final Map<String, String> JMP = Map.of(
            "JGT", "001",
            "JEQ", "010",
            "JGE", "011",
            "JLT", "100",
            "JNE", "101",
            "JLE", "110",
            "JMP", "111",
            "null", "000"
    );


    public static final Map<String, String> COMP0 = Map.ofEntries(
            Map.entry("0", "101010"),
            Map.entry("1", "111111"),
            Map.entry("-1", "111010"),
            Map.entry("D", "001100"),
            Map.entry("A", "110000"),
            Map.entry("!D", "001101"),
            Map.entry("!A", "110001"),
            Map.entry("-D", "001111"),
            Map.entry("-A", "110011"),
            Map.entry("D+1", "011111"),
            Map.entry("A+1", "110111"),
            Map.entry("D-1", "001110"),
            Map.entry("A-1", "110010"),
            Map.entry("D+A", "000010"),
            Map.entry("D-A", "010011"),
            Map.entry("A-D", "000111"),
            Map.entry("D&A", "000000"),
            Map.entry("D|A", "010101")

    );

    public static final Map<String, String> COMP1 = Map.ofEntries(
            Map.entry("M", "110000"),
            Map.entry("!M", "110001"),
            Map.entry("-M", "110011"),
            Map.entry("M+1", "110111"),
            Map.entry("M-1", "110010"),
            Map.entry("D+M", "000010"),
            Map.entry("D-M", "010011"),
            Map.entry("M-D", "000111"),
            Map.entry("D&M", "000000"),
            Map.entry("D|M", "010101")
    );
}
