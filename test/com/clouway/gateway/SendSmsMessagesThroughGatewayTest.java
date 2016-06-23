package com.clouway.gateway;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Vasil Mitov (v.mitov.clouway@gmail.com)
 */
public class SendSmsMessagesThroughGatewayTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  // Collaborators
  Sender sender = context.mock(Sender.class);
  Validator validator =  context.mock(Validator.class);

  // Object Under Test
  Gateway gateway = new Gateway(validator, sender);

  @Test
  public void sendSimpleMessage() throws Exception {

    SMS anyCorrectSmsMessage = new SMS("hello","359899123123123","Hello");

    context.checking(new Expectations() {{
      oneOf(validator).isValid(anyCorrectSmsMessage);
      will(returnValue(true));

      oneOf(sender).send(anyCorrectSmsMessage);
      will(returnValue(true));
    }});


    gateway.send(anyCorrectSmsMessage);
  }


  @Test
  public void validationFails() throws Exception {
    SMS anyNotValidSmsMessage = new SMS("","123123","");

    context.checking(new Expectations() { {
        oneOf(validator).isValid(anyNotValidSmsMessage);
        will(returnValue(false));
      }});

    Gateway gateway = new Gateway(validator, sender);
    gateway.send(anyNotValidSmsMessage);
  }

  @Test(expected = MessageNotDeliveredException.class)
  public void sendFails() throws Exception {
    SMS sms = new SMS("","123123","");

    context.checking(new Expectations() { {
      oneOf(validator).isValid(sms);
      will(returnValue(true));

      oneOf(sender).send(sms);
      will(returnValue(false));

    }});

    Gateway gateway = new Gateway(validator, sender);
    gateway.send(sms);
  }


}
