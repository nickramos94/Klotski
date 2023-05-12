import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            new Game();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}