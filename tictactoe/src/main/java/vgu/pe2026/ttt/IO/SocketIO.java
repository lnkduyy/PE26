package vgu.pe2026.ttt.IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketIO implements GameIO {

    private final BufferedReader in;
    private final PrintWriter out;

    public SocketIO(Socket socket) throws IOException {
        this.in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public String readLine() throws IOException {
        return in.readLine();
    }

    @Override
    public void println(String message) {
        out.println(message);
    }
}
