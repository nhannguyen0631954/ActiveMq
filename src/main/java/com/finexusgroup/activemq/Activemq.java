/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.finexusgroup.activemq;


import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.qpid.jms.JmsConnectionFactory;


/**
 *
 * @author User
 */
public class Activemq {

    public static void main(String[] args) throws Exception  {
        System.out.println("Create a ConnectionFactory");
       Connection connection = null;
      ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://localhost:5672");
        System.out.println("Create a Connection");
        connection = connectionFactory.createConnection("admin","admin");
        connection.start();
        System.out.println("Create a Session");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        System.out.println("Create a Topic/ Queue based on the given parameter");
        Destination destination = null;

        destination = session.createQueue("gpcoder-jms-queue");
//            destination = session.createTopic("gpcoder-jms-topic");
        System.out.println("Create a Producer to send messages to one Topic or Queue.");
        MessageProducer producer = session.createProducer(destination);

        System.out.println("Start sending messages ... ");
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
            String response;
            do {
                System.out.print("Enter message: ");
                response = br.readLine().trim();
                TextMessage msg = session.createTextMessage(response);
                producer.send(msg);
            } while (!response.equalsIgnoreCase("close"));
        }

        System.out.println("Shutdown JMS connection and free resources");
        connection.close();
        System.exit(1);
    }
}
