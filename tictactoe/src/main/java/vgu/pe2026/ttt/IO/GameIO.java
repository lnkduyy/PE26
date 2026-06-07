package vgu.pe2026.ttt.IO;

import java.io.IOException;

public interface GameIO {

    String readLine() throws IOException;

    void println(String message);
}
