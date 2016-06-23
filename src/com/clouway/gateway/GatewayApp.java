package com.clouway.gateway;

/**
 * @author Vasil Mitov (v.mitov.clouway@gmail.com)
 */
public class GatewayApp {

  public static void main(String[] args) {

    Gateway gateway = new Gateway(new RealSmsValidator(), sms -> {
      System.out.println("Message sent.");
      return true;
    });
    gateway.send(new SMS("Hello", "+359895444332", "Hello John, How are you?"));
  }
}
