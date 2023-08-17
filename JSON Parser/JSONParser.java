import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JSONParser {

    public static Map<String, Object> parse(String json) {
        return (Map<String, Object>) parseValue(new Tokenizer(json));
    }

    private static Object parseValue(Tokenizer tokenizer) {
        tokenizer.skipWhitespaces();
        char current = tokenizer.peek();

        if (current == '{') {
            return parseObject(tokenizer);
        } else if (current == '[') {
            return parseArray(tokenizer);
        } else if (current == '\"') {
            return parseString(tokenizer);
        } else if (Character.isDigit(current) || current == '-') {
            return parseInteger(tokenizer);
        } else if (current == 't' || current == 'f') {
            return parseBoolean(tokenizer);
        }

        throw new RuntimeException("Unexpected token: " + current);
    }

    private static Map<String, Object> parseObject(Tokenizer tokenizer) {
        Map<String, Object> result = new HashMap<>();

        tokenizer.expect('{');
        tokenizer.skipWhitespaces();

        while (tokenizer.peek() != '}') {
            String key = parseString(tokenizer);
            tokenizer.skipWhitespaces();
            tokenizer.expect(':');
            Object value = parseValue(tokenizer);
            result.put(key, value);

            tokenizer.skipWhitespaces();
            if (tokenizer.peek() == ',') {
                tokenizer.next(); // consume ','
                tokenizer.skipWhitespaces();
            } else {
                break;
            }
        }

        tokenizer.expect('}');
        return result;
    }

    private static List<Object> parseArray(Tokenizer tokenizer) {
        List<Object> result = new ArrayList<>();

        tokenizer.expect('[');
        tokenizer.skipWhitespaces();

        while (tokenizer.peek() != ']') {
            result.add(parseValue(tokenizer));

            tokenizer.skipWhitespaces();
            if (tokenizer.peek() == ',') {
                tokenizer.next(); // consume ','
                tokenizer.skipWhitespaces();
            } else {
                break;
            }
        }

        tokenizer.expect(']');
        return result;
    }

    private static String parseString(Tokenizer tokenizer) {
        tokenizer.expect('\"');
        StringBuilder sb = new StringBuilder();
        while (true) {
            char c = tokenizer.next();
            if (c == '\"') {
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static int parseInteger(Tokenizer tokenizer) {
        StringBuilder sb = new StringBuilder();
        char current = tokenizer.peek();

        while (Character.isDigit(current) || current == '-') {
            sb.append(current);
            tokenizer.next();
            current = tokenizer.peek();
        }

        return Integer.parseInt(sb.toString());
    }

    private static boolean parseBoolean(Tokenizer tokenizer) {
        if (tokenizer.peek() == 't') {
            tokenizer.consume("true");
            return true;
        } else {
            tokenizer.consume("false");
            return false;
        }
    }

    static class Tokenizer {
        private final String input;
        private int pos = 0;

        Tokenizer(String input) {
            this.input = input;
        }

        char peek() {
            return pos < input.length() ? input.charAt(pos) : (char) -1;
        }

        char next() {
            return input.charAt(pos++);
        }

        void expect(char c) {
            if (peek() != c) {
                throw new RuntimeException("Expected '" + c + "' but found '" + peek() + "'");
            }
            pos++;
        }

        void consume(String str) {
            for (char c : str.toCharArray()) {
                expect(c);
            }
        }

        void skipWhitespaces() {
            while (Character.isWhitespace(peek())) {
                pos++;
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "./input.json"; // Replace with the actual file path, it can also take .txt files
        
        try {
            String input = new String(Files.readAllBytes(Paths.get(filePath)));  //Takes in file content as string
            System.out.println(input);

            Map<String, Object> output = JSONParser.parse(input);
            assert output.get("debug").equals("on") : "debug";
            assert ((Map<String, Object>)output.get("window")).get("title").equals("sample");
            assert ((Map<String, Object>)output.get("window")).get("size").equals(500);
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        
        
    }
}
