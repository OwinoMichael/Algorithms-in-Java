import java.io.*;

public class HuffmanDecoding {

    private String[] huffmanCodes; // Array to store the Huffman codes
    private char[] characters;     // Array to store corresponding characters

    public void decompressBinaryFileToTextBitMask(String inputBinaryPath, String outputTextPath) {
        try (
                FileInputStream fileInputStream = new FileInputStream(inputBinaryPath);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputTextPath))
        ) {
            int byteRead;

            // Read each byte from the binary file
            while ((byteRead = fileInputStream.read()) != -1) {
                // Convert the byte to a binary string
                String binaryString = String.format("%8s", Integer.toBinaryString(byteRead & 0xFF)).replace(' ', '0');

                // Write the binary string to the output text file
                bufferedWriter.write(binaryString);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read/write file: " + e.getMessage());
        }

    }

    public void decompressBinaryFileToText(String inputBinaryPath, String outputTextPath) {
        try (
                FileInputStream fileInputStream = new FileInputStream(inputBinaryPath);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputTextPath))
        ) {
            int byteRead;

            // Read each byte from the binary file
            while ((byteRead = fileInputStream.read()) != -1) {
                // Extract each bit from the byte using bit masking
                for (int i = 7; i >= 0; i--) {
                    // Create a mask that isolates the i-th bit: (1 << i) creates a mask for the i-th bit
                    int bit = (byteRead >> i) & 1;
                    // Append each bit to the output file
                    bufferedWriter.write(Integer.toString(bit));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read/write file: " + e.getMessage());
        }
    }

    public void binaryFileToHuffmanTree(String filePath, String outputPath) {
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputPath))
        ) {
            String delimiter = "00000000";
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                // Split the line using the delimiter (ASCII binary + delimiter + Huffman code)
                String[] parts = line.split(delimiter);

                if (parts.length == 2) {
                    String binaryAscii = parts[0].trim();  // Binary ASCII part
                    String huffmanCode = parts[1].trim();  // Huffman code part

                    // Convert the binary ASCII back to the original character
                    int asciiValue = Integer.parseInt(binaryAscii, 2);

                    // Check if the ASCII value is within the printable character range
                    if (asciiValue >= 32 && asciiValue <= 126) {
                        char originalChar = (char) asciiValue;

                        // Write the original character + ":" + Huffman code to the output file
                        bufferedWriter.write(originalChar + ": " + huffmanCode);
                    } else {
                        // Handle non-printable/control characters
                        bufferedWriter.write("[INVALID_ASCII]: " + huffmanCode);
                    }
                    bufferedWriter.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to locate file: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Unable to read or write file: " + e.getMessage());
        }
    }

    // Method to read the Huffman table and store it in arrays
    private void loadHuffmanTable(String tableFilePath) throws IOException {
        // Use a BufferedReader to read the file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tableFilePath))) {
            // First, we need to know how many lines are in the table to initialize the arrays
            int numberOfEntries = (int) bufferedReader.lines().count();
            huffmanCodes = new String[numberOfEntries];
            characters = new char[numberOfEntries];

            // Reset the reader to start reading from the beginning
            bufferedReader.close();
            BufferedReader br = new BufferedReader(new FileReader(tableFilePath));

            // Read the file line by line to fill the arrays
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                // Assuming each line is in the format "character: code"
                String[] parts = line.split(": ");
                characters[index] = parts[0].charAt(0);  // Store the character
                huffmanCodes[index] = parts[1];          // Store the Huffman code
                index++;
            }
        }
    }

    // Method to decode the encoded text
    private String decodeEncodedText(String encodedFilePath) throws IOException {
        StringBuilder decodedText = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(encodedFilePath))) {
            int ch;
            // Read the encoded text bit by bit
            while ((ch = bufferedReader.read()) != -1) {
                currentCode.append((char) ch); // Accumulate bits

                // Now check if the current code matches any Huffman code
                for (int i = 0; i < huffmanCodes.length; i++) {
                    if (currentCode.toString().equals(huffmanCodes[i])) {
                        // If a match is found, append the corresponding character
                        decodedText.append(characters[i]);

                        // Clear the currentCode for the next character
                        currentCode.setLength(0);
                        break;
                    }
                }
            }
        }

        return decodedText.toString();
    }

    // Method to save the decoded text to an output file
    private void saveDecodedText(String decodedText, String outputFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(decodedText);
        }
    }

    // Main method to handle the decoding process
    public void decode(String encodedFilePath, String huffmanTableFilePath, String outputFilePath) throws IOException {
        // Load the Huffman table into the arrays
        loadHuffmanTable(huffmanTableFilePath);

        // Decode the encoded text
        String decodedText = decodeEncodedText(encodedFilePath);

        // Save the decoded text to the output file
        saveDecodedText(decodedText, outputFilePath);
    }



    public static void main(String[] args) {
        HuffmanDecoding decode = new HuffmanDecoding();

       //Step 1: decompress the bin file
       decode.decompressBinaryFileToText(
       "//Users//....//IdeaProjects//Huffman//src//main//java//encoded_binary.bin",
       "//Users//....//IdeaProjects//Huffman//src//main//java//decoded_Text.txt"
       );

       //Step 1: decompress the bin file
       decode.decompressBinaryFileToTextBitMask(
               "//Users//....//IdeaProjects//Huffman//src//main//java//encoded_binary.bin",
               "//Users//m..../IdeaProjects//Huffman//src//main//java//decoded_Text.txt"
       );

       //Step 2: convert the huffman Binary Txt to ASCII characters
       decode.binaryFileToHuffmanTree(
               "//Users//....//IdeaProjects//Huffman//src//main//java//huffman_binary.txt" ,
               "//Users//....//IdeaProjects//Huffman//src//main//java//huffman_tree.txt"
       );

        //Step 3: Load the txt file containing the Huffman Tree
        //Step 4: Load the txt file containing the encoded text
        //Step 5: Decode the encoded text using huffman Tree
        try {
            decode.decode(
                    "//Users//....//IdeaProjects//Huffman//src//main//java//decoded_Text.txt",
                    "//Users//....//IdeaProjects//Huffman//src//main//java//huffman_table.txt",
                    "//Users//....//IdeaProjects//Huffman//src//main//java//decoded_huffman.txt"
            );
        } catch (IOException e) {
            throw new RuntimeException("Un able to load some/all of the files" + e.getMessage());
        }

    }
}
