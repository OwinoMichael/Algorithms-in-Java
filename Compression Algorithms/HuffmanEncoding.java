import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Node {
    public final int frequency; // Each node should have a frequency
    private Node leftNode;      // A node comprises Left & Right child
    private Node rightNode;

    // Constructor to initialize a node with just a frequency
    public Node(int frequency) {
        this.frequency = frequency;  // Initialize frequency
    }

    Node(Node leftNode, Node rightNode) {
        this.frequency = leftNode.getFrequency() + rightNode.getFrequency(); // Initialize frequency to sum of Left + right node
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }


    public int getFrequency() { //get Frequency to be used for sorting
        return frequency;
    }

    //we need getter function for Left & Right Node for encoding and building the huffman tree
    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }
}



class Leaf extends Node { // Leaf is subclass of Node i.e. It contains a character
    private final char character;

    //Leaf is a subset of Node, shared the frequency attribute from Node
    public Leaf(char character, int frequency) {
        super(frequency);
        this.character = character;
    }

    // Getter for character
    public char getCharacter() {
        return character;
    }

}

public class HuffmanEncoding {

    private Node root; // always start from root
    private String text; // text to be encoded & decoded //to store the Input file to this text
    private final List<Leaf> leaves = new ArrayList<>(); // store leaf nodes
    private final List<Node> nodeList = new ArrayList<>(); // list for nodes also(leaf)
    private final List<String> huffmanCodes = new ArrayList<>();
    private String encodedText;

//    public HuffmanEncoding(/*String text*/) {
//       // this.text = text; //EDITED SINCE WE WILL INPUT FILE INSTEAD OF TEXT
//    }

    //The methods does the following
    //  1. Read the initial file character by character instead of line by line
    //  2. Create a frequency file and stores the frequency of all characters
    public void readTxt(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the input text file (with format e.g .txt): ");
        String filename = scanner.next();

        String absolutePath = "//Users////IdeaProjects//Huffman//src//main/java//";
        String pathRead = absolutePath + filename; //Concatenate the Keyboard input with path

        //VERY KEY POINT
        StringBuilder stringBuilder = new StringBuilder(); //To store the input text file in a string


        int [] frequency = new int[256]; //ASCII Characters 256 //
        int whitespaceCount = 0; //count the whitespace separately to combine all types of spaces

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathRead));
            int ch; //KEY POINT

            while ((ch = reader.read()) != -1){

                char character = (char) ch; //re-assign ch(int) to char

                // Append the character to the stringBuilder
                stringBuilder.append(character); //NEVER FORGET TO UTILIZE THE STRING BUILDER

                character = Character.toLowerCase(character); //store all alphabetical characters in same freq, both upper & small case
                // Filter to include only A–Z, a–z, 0–9, comma, period, and whitespace
                if ((character >= 'a' && character <= 'z') ||
                        (character >= '0' && character <= '9') ||
                        character == ',' ||
                        character == '.') {

                    // Increment frequency for valid non-whitespace character
                    frequency[character]++;
                } else if (Character.isWhitespace(character)) {
                    // Count all whitespace (space, newline, tab, etc.) together
                    whitespaceCount++;
                }
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Store the entire text in the field
        text = stringBuilder.toString();

        String fileName = "frequency.txt";
        String pathWrite = absolutePath + fileName;

        //store the frequencies in frequency.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathWrite))){
            // Write character frequencies to the output file, including all relevant characters
            for (char c = 'a'; c <= 'z'; c++) {
                writer.write(c + ": " + frequency[c] + "\n");
            }

            for (char c = '0'; c <= '9'; c++) {
                writer.write(c + ": " + frequency[c] + "\n");
            }

            // Include comma and period in the output
            writer.write(", : " + frequency[','] + "\n");
            writer.write(". : " + frequency['.'] + "\n");

            // Write the combined whitespace frequency
            writer.write("Whitespace: " + whitespaceCount + "\n");

            // For any additional characters (optional)
            for (int i = 0; i < frequency.length; i++) {
                if (frequency[i] > 0 &&
                        !(i >= 'a' && i <= 'z') &&
                        !(i >= '0' && i <= '9') &&
                        i != ',' &&
                        i != '.') {
                    writer.write((char) i + ": " + frequency[i] + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing frequency file: " + e.getMessage());
        }

    }

    // Read frequencies from a colon-separated file and create leaf nodes
    public void readFrequenciesFromFile(String filePath) throws IOException {
        //filePath = "//Users//mikeowino//IdeaProjects//Huffman//src//main/java//frequency.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":"); // Now using colon as separator
                if (parts.length == 2) {
                    char character = parts[0].trim().charAt(0);  // First part is the character, remove extra spaces
                    int frequency = Integer.parseInt(parts[1].trim()); // Second part is the frequency, remove extra spaces
                    leaves.add(new Leaf(character, frequency)); // Create a new leaf node
                }
            }
        }
    }

    //crucial part, we do the Huffman Encoding
    public void encode() {
        nodeList.addAll(leaves); // Initialize the nodeList with leaf nodes

        while (nodeList.size() > 1) {
            customMergeSort(nodeList); // Custom function to sort the list based on node frequency
            Node left = nodeList.remove(0);  // Remove smallest frequency node
            Node right = nodeList.remove(0); // Remove second smallest frequency node

            Node newNode = new Node(left, right); // Combine the two smallest nodes
            nodeList.add(newNode);                // Add the new node back into the list
        }

        root = nodeList.get(0);
        generateHuffmanCodes(root, "");
        encodedText = getEncodedText();
    }

//    // Custom function to sort nodes by frequency
//    private void sortNodeList(List<Node> nodeList) {
//        nodeList.sort((n1, n2) -> n1.getFrequency() - n2.getFrequency()); // Sort based on frequency
//    }

    // Custom merge sort for nodes based on frequency
    public void customMergeSort(List<Node> nodeList) {
        if (nodeList.size() > 1) {
            int mid = nodeList.size() / 2;
            List<Node> left = new ArrayList<>(nodeList.subList(0, mid));
            List<Node> right = new ArrayList<>(nodeList.subList(mid, nodeList.size()));

            customMergeSort(left);
            customMergeSort(right);
            merge(nodeList, left, right);
        }
    }

    // Merging two halves based on the frequency of nodes
    private void merge(List<Node> nodeList, List<Node> left, List<Node> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getFrequency() <= right.get(j).getFrequency()) {
                nodeList.set(k++, left.get(i++));
            } else {
                nodeList.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) {
            nodeList.set(k++, left.get(i++));
        }
        while (j < right.size()) {
            nodeList.set(k++, right.get(j++));
        }
    }

    // Generate Huffman codes for characters
    private void generateHuffmanCodes(Node node, String code) {
        if (node instanceof Leaf leaf) {
            huffmanCodes.add(leaf.getCharacter() + ": " + code);
            return;
        }
        generateHuffmanCodes(node.getLeftNode(), code + "0");
        generateHuffmanCodes(node.getRightNode(), code + "1");
    }

    //Does the encoding on Input File
    public String getEncodedText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char character : text.toCharArray()) {
            for (String code : huffmanCodes) {
                if (code.charAt(0) == character) {
                    stringBuilder.append(code.split(": ")[1]);
                }
            }
        }
        return stringBuilder.toString();
    }

    // Save Huffman codes and encoded text to a single output file
    public void saveResultsToFile(String outputPath, String outputTable) throws IOException {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
                BufferedWriter writeTable = new BufferedWriter(new FileWriter(outputTable))
        ) {
            //writer.write("\nEncoded Text:\n");
            writer.write(encodedText);

            // Write the Huffman codes to the file
            //writeTable.write("Huffman Codes Table:\n");
            for (String code : huffmanCodes) {
                writeTable.write(code + "\n");
            }

        }
    }

    public void huffmanTreeToAscii(String filePath, String outputPath){
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputPath))
        ) {

            String delimiter = "00000000";

            // Write the delimiter to the beginning of the output file
            bufferedWriter.write(delimiter);
            bufferedWriter.newLine();

            String line; // To read each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // example  "a: 0000010" we can split the line
                if (line.contains(":")) {
                    // Split the line
                    String[] parts = line.split(":");
                    char character = parts[0].trim().charAt(0); // Fetch the character
                    String huffmanCode = parts[1].trim(); // Get the Huffman code

                    // Convert the character to its ASCII binary representation of 8 bits
                    String binaryAscii = String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0');

                    // Write binary ASCII + delimiter + Huffman code to the output file
                    bufferedWriter.write(binaryAscii + delimiter + huffmanCode);
                    bufferedWriter.newLine();
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to locate file: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Unable to read or write file: " + e.getMessage());
        }
    }

    public void convertTextToBinaryFile(String filePath, String outputBinaryPath){
        try(
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            FileOutputStream fileOutputStream = new FileOutputStream(outputBinaryPath)
        ){
            StringBuilder binaryStringBuilder = new StringBuilder();
            String line;

            //Read all lines of the encoded Text
            while((line = bufferedReader.readLine()) != null){
                //Append the line of binary values to the String Builder
                binaryStringBuilder.append(line.trim());
            }

            // Convert the String Builder to a binary String
            String binaryString = binaryStringBuilder.toString();

            // Ensure the length is a multiple of 8 by padding with zeros if necessary
            int length = binaryString.length();
            if (length % 8 != 0) {
                // Pad with zeros to make it a multiple of 8
                int paddingLength = 8 - (length % 8);
                binaryString = binaryString + "0".repeat(paddingLength);
            }

            // Convert the binary string to bytes
            byte[] bytes = new byte[binaryString.length() / 8];
            for (int i = 0; i < binaryString.length(); i += 8) {
                String byteString = binaryString.substring(i, i + 8);
                // Use bitwise operation OR to convert string to byte
                bytes[i / 8] = (byte) Integer.parseInt(byteString, 2);
            }

            // Write the bytes to the output binary file
            fileOutputStream.write(bytes);


        }
        catch (IOException e) {
            throw new RuntimeException("Unable to read/write file: " + e.getMessage());
        }


    }

    public void convertTextToBinaryFileUsingBitMask(String filePath, String outputBinaryPath) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
                FileOutputStream fileOutputStream = new FileOutputStream(outputBinaryPath)
        ) {
            StringBuilder binaryStringBuilder = new StringBuilder();
            String line;

            // Read all lines of the encoded text
            while ((line = bufferedReader.readLine()) != null) {
                // Append the line of binary values to the String Builder
                binaryStringBuilder.append(line.trim());
            }

            // Convert the String Builder to a binary String
            String binaryString = binaryStringBuilder.toString();

            // Ensure the length is a multiple of 8 by padding with zeros if necessary
            int length = binaryString.length();
            if (length % 8 != 0) {
                // Pad with zeros to make it a multiple of 8
                int paddingLength = 8 - (length % 8);
                binaryString = binaryString + "0".repeat(paddingLength);
            }

            // Convert the binary string to bytes using bitmasking
            int byteValue = 0; // Initialize the byte value
            int bitCount = 0; // Initialize the bit counter

            for (char bit : binaryString.toCharArray()) {
                // Update the byte value using bitmasking
                byteValue = (byteValue << 1) | (bit - '0'); // Shift left and add the new bit
                bitCount++;

                // Every 8 bits, write a byte to the file
                if (bitCount == 8) {
                    fileOutputStream.write(byteValue); // Write the constructed byte
                    byteValue = 0; // Reset for the next byte
                    bitCount = 0; // Reset the bit counter
                }
            }

            // Handle any remaining bits (less than 8)
            if (bitCount > 0) {
                byteValue <<= (8 - bitCount); // Shift to fill the byte
                fileOutputStream.write(byteValue); // Write the final byte
            }

        } catch (IOException e) {
            throw new RuntimeException("Unable to read/write file: " + e.getMessage());
        }
    }


    public static void main(String[] args) {

        HuffmanEncoding huffman = new HuffmanEncoding();

        // Step 1: Read text file and generate frequency file
        huffman.readTxt();

        // Step 2: Read frequencies from frequency file
        try {
            huffman.readFrequenciesFromFile("//Users////IdeaProjects//Huffman//src//main/java//frequency.txt"); // Null is passed; file path is hardcoded inside the method
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Step 3: Perform Huffman encoding
        huffman.encode();

        // Step 4: Save results to output file
        String outputFilePath = "//Users////IdeaProjects//Huffman//src//main//java//encoded_Text.txt";
        String outputTable = "//Users////IdeaProjects//Huffman//src//main//java//huffman_table.txt";
        try {
            huffman.saveResultsToFile(outputFilePath, outputTable);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file" + e.getMessage());
        }
        System.out.println("Huffman codes and encoded text saved to " + outputFilePath);

        // Step 5: Convert Huffman Table to Binary Representation
        huffman.huffmanTreeToAscii(

                "//Users////IdeaProjects//Huffman//src//main//java//huffman_table.txt",
                "//Users////IdeaProjects//Huffman//src//main//java//huffman_binary.txt"
                );

        //Step 6: Compress the encoded txt file to binary compressed file
       huffman.convertTextToBinaryFile(
               "//Users//mikeowino//IdeaProjects//Huffman//src//main//java//encoded_Text.txt",
               "//Users//mikeowino//IdeaProjects//Huffman//src//main//java//encoded_binary.bin"
       );

        //Step 6: Compress the encoded txt file to binary compressed file
        huffman.convertTextToBinaryFileUsingBitMask(
                "//Users////IdeaProjects//Huffman//src//main//java//encoded_Text.txt",
                "//Users////IdeaProjects//Huffman//src//main//java//encoded_binary.bin"
        );
    }


}


