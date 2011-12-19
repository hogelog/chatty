package org.hogel.chatty.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

@WebServlet("/chatty")
public class ChattyServlet extends WebSocketServlet {
    private static final long serialVersionUID = 1L;

    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
        return new ChattyWebSocket(getServletContext());
    }

}
