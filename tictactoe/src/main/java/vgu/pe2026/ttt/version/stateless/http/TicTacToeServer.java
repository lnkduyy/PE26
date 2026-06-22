package vgu.pe2026.ttt.version.stateless.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import vgu.pe2026.ttt.Board.Board;
import vgu.pe2026.ttt.Player.ComputerPlayer;

public class TicTacToeServer {
    private static final int PORT = 1817;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/play", new PlayHandler());
        // server.setExecutor(null); // creates a default single-thread executor
        server.start();
        System.out.println("[HTTP Stateless] HttpServer started on port " + PORT);
    }

    static class PlayHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                if (query != null) {
                    String[] params = query.split("&");
                    int pos = -1;
                    String boardStr = "";
                    for (String param : params) {
                        String[] kv = param.split("=");
                        if (kv.length == 2) {
                            if (kv[0].equals("pos")) pos = Integer.parseInt(kv[1]);
                            if (kv[0].equals("board")) boardStr = kv[1];
                        }
                    }

                    if (pos != -1 && !boardStr.isEmpty()) {
                        Board board = new Board();
                        String[] values = boardStr.split(",");
                        int[] cells = new int[9];
                        for (int i = 0; i < 9; i++) {
                            cells[i] = Integer.parseInt(values[i]);
                        }
                        board.setCells(cells);

                        ComputerPlayer computer = new ComputerPlayer(2);
                        int serverMove = -1;
                        if (!board.isFull() && !board.hasWon(1)) {
                            try {
                                serverMove = computer.chooseMove(board);
                                if (serverMove != -1) {
                                    board.markCell(serverMove, 2);
                                }
                            } catch (Exception ignored) {}
                        }

                        cells = board.getCells();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < cells.length; i++) {
                            sb.append(cells[i]);
                            if (i < cells.length - 1) sb.append(",");
                        }
                        
                        String responseBody = serverMove + "|" + sb.toString();
                        
                        exchange.getResponseHeaders().set("Content-Type", "text/plain");
                        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                        exchange.sendResponseHeaders(200, responseBody.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(responseBody.getBytes());
                        os.close();
                        return;
                    }
                }
            }
            
            String bad = "Bad Request";
            exchange.sendResponseHeaders(400, bad.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(bad.getBytes());
            os.close();
        }
    }
}
