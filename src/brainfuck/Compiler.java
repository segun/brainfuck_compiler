package brainfuck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author aardvocate
 */
public class Compiler {

    private final HashMap<Integer, Integer> STACK = new HashMap<>();
    private final HashMap<Integer, Integer> LOOP_INDICES = new HashMap<>();
    private int pointer = 0;
    private int value;
    private final Scanner SCANNER = new Scanner(System.in);
    private final StringBuilder buffer = new StringBuilder();

    public String compile(String fuckText) {

        int i = 0;

        findLoopIndices(fuckText);
        
        //System.err.println(LOOP_INDICES);

        while (i < fuckText.length()) {
            char c = fuckText.charAt(i);
            //System.err.println("Read Char [" + c + "] and index [" + i + "]");
            switch (c) {
                case '[':
                    i = doLoop(fuckText, i);
                    break;
                default:
                    doOthers(c);
            }
            i++;
        }

        return buffer.toString();
    }

    private int doLoop(String fuckText, int i) {
        int endLoop = LOOP_INDICES.get(i);
        //System.err.println("Starting Loop at [" + i + "], ending Loop at [" + endLoop + "]");
        int j = i + 1;
        while (j < endLoop) {
            if (j == i + 1 && getFromStack() == 0) {
                break;
            }

            char c = fuckText.charAt(j);
            //System.err.println("doLoop --> Read Char [" + c + "] and index [" + j + "]");
            switch (c) {
                case '[':
                    j = doLoop(fuckText, j);
                    j++;
                    break;
                default:
                    doOthers(c);
                    j++;
                    break;
            }

            if (j >= endLoop) {
                j = i + 1;
            }
        }

        return endLoop;
    }

    private int getFromStack() {
        if (STACK.get(pointer) == null) {
            STACK.put(pointer, 0);
        }

        return STACK.get(pointer);
    }

    private void doOthers(char c) {
        //System.out.println(STACK);
        switch (c) {
            case '>':
                pointer++;
                break;
            case '<':
                if (pointer == 0) {
                    throw new IndexOutOfBoundsException("Pointer is already at 0. Can't go beyond zero");
                } else {
                    pointer--;
                }
                break;
            case '+':
                value = getFromStack();
                STACK.put(pointer, ++value);
                break;
            case '-':
                value = getFromStack();
                STACK.put(pointer, --value);
                break;
            case '.':
                value = getFromStack();
                if (isPrintable(value)) {
                    buffer.append((char) value);
                    System.out.print((char) value);
                } else {
                    buffer.append(value);
                    System.out.print(value);
                }
                break;
            case ',':
                value = SCANNER.nextInt();
                STACK.put(pointer, value);
                break;
            default:
                throw new UnsupportedOperationException("Unknown character encountered");
        }
    }

    private boolean isPrintable(int value) {
        String strValue = String.valueOf((char) value);
        return StringUtils.isAsciiPrintable(strValue);
    }

    public void findLoopIndices(String fuckText) {
        int i = 0;

        ArrayList<Integer> foundIndices = new ArrayList<>();

        while (i < fuckText.length()) {
            char c = fuckText.charAt(i);

            switch (c) {
                case '[':
                    foundIndices.add(i);
                    break;
                case ']':
                    int key = foundIndices.get(foundIndices.size() - 1);
                    LOOP_INDICES.put(key, i);
                    foundIndices.remove(foundIndices.size() - 1);
            }
            i++;
        }
    }

    public static void main(String[] args) {
        String s = "Happy New Year\n";
        String bf = "++++++[>++++++++++++++++>+++++>+++++++++++++<<<-]>>>------.<<+.+++++++++++++++..+++++++++.>++.>++++++.>++++[<++++++>-]<-.<<--.>.>------------.++++++++++++.----.<<-----.>+.-.>>++++[<------>-]<.<.<----------.+++++++.+.-----------.>.<+++++++++++++++.-----.>.<--.--------..+++++++++++++++.>.<+++++.----------.++++++.>.<--.----..-.>+."; 
        
        //"72_97_112_112_121_32_78_101_119_32_89_101_97_114_10";
        //stores 32 (space) in cell 2.
        //stores 72 (H) in cell 3 and print it        
        
        
        //= "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.";//>++.";        
//        //stores 32 (space) in cell 2.
//        bf = "+++[->++++++++++<]>++<";
//        //stores 72 (H) in cell 3 and print it
//        bf += "+++++++[->>++++++++++<<]>>++.<<";
//        //stores 100 (d) in cell 4
//        bf += "++++++++++[->>>++++++++++<<<]";
//        //store 87 in cell 5
//        bf += "++++++++[->>>>++++++++++<<<<]>>>>+++++++<<<<";
//        //start printing 
//        //e = 101, move to cell 4, add 1 and print
//        bf += ">>>+.";
//        //l = 108, add 7 to cell 4 and print twice
//        bf += "+++++++..";
//        //o = 111, add 3 to cell 4 and print (cell 4 is now 111)
//        bf += "+++.";
//        //space is stored in cell 2, move to cell 2 and print
//        bf += "<<.";
//        //W is 87, move to cell 5 and print, note we're currently in cell 2
//        bf += ">>>.";
//        //o is 111, cell 4 is currently 111, move to cell 4 print
//        bf += "<.";
//        //r is 114, add 3 to cell 4 and print
//        bf += "+++.";
//        //l = 108, remove 6 from cell 4 and print
//        bf += "------.";
//        //d = 100, remove 8 from cell 4 and print
//        bf += "--------.";
        new Compiler().compile(bf);
    }
}