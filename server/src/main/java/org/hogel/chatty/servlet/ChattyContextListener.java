package org.hogel.chatty.servlet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.eclipse.jetty.websocket.WebSocket;

@WebListener
public class ChattyContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        @SuppressWarnings("unchecked")
        List<WebSocket.Connection> connections = (List<WebSocket.Connection>) context.getAttribute("connections");
        for (WebSocket.Connection connection : connections) {
            connection.close();
        }
    }

    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        List<String> chatlog = new ArrayList<String>();
        List<WebSocket.Connection> connections = new LinkedList<WebSocket.Connection>();
        context.setAttribute("chatlog", chatlog);
        context.setAttribute("connections", connections);
    }

}
