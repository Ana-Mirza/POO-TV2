import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;
import program.Program;

import java.io.File;
import java.io.IOException;

public final class Main {
    private Main() { }

    /**
     * Openes input file and calls entry point of program
     *
     * @param args contain input and output files
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode output = objectMapper.createArrayNode();
        Input inputData = objectMapper.readValue(new File(args[0]), Input.class);

        /* Entry point of implementation */
        Program program = Program.getInstance();
        program.start(inputData, output);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
    }
}
