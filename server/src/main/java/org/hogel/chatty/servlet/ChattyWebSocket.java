package org.hogel.chatty.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChattyWebSocket implements WebSocket.OnTextMessage {
    static final Logger LOG = LoggerFactory.getLogger(ChattyWebSocket.class);

    final List<String> chatlog;
    final List<Connection> connections;

    Connection connection;

    @SuppressWarnings("unchecked")
    public ChattyWebSocket(ServletContext context) {
        chatlog = (List<String>) context.getAttribute("chatlog");
        connections = (List<Connection>) context.getAttribute("connections");
    }

    public void onOpen(Connection connection) {
        LOG.info("open socket: {}", connection);
        this.connection = connection;
        connections.add(connection);
        for (String log : chatlog) {
            sendlog(log);
        }
    }

    public void onClose(int closeCode, String message) {
        connections.remove(connection);
        connection.close();
        LOG.info("close socket: {}", connection);
    }

    public void onMessage(String data) {
        LOG.info("receive data: {}", data);
        chatlog.add(data);
        sendlog(data);
    }

    private void sendlog(String data) {
        LOG.info("{} connections", connections.size());
        for (Connection c : connections) {
            try {
                LOG.info("send {}: {}", c, data);
                c.sendMessage(data);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

}
