package com.clouway.gateway;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Vasil Mitov (v.mitov.clouway@gmail.com)
 */
public class ValidateSmsMessagesTest {

  private RealSmsValidator smsValidator = new RealSmsValidator();

  @Test
  public void happyPath() throws Exception {
    assertThat(smsValidator.isValid(new SMS("Hello", "+359899000001", "::message body::")), is(equalTo(true)));
  }

  @Test
  public void missingTitle() throws Exception {
    assertThat(smsValidator.isValid(new SMS("", "+359899000002", "::message body::")), is(equalTo(false)));
  }

  @Test
  public void numberIsMissing() throws Exception {
    assertThat(smsValidator.isValid(new SMS("Hello", "::title which is not mobile number::", "::message body::")), is(equalTo(false)));
  }

  @Test
  public void bodyIsMissing() throws Exception {
    assertThat(smsValidator.isValid(new SMS("Hello", "+359899000003", "")), is(equalTo(false)));
  }

  @Test
  public void longBody() throws Exception {
    assertThat(smsValidator.isValid(new SMS("Hello", "+359899000003", generateString(101))), is(equalTo(false)));
  }

  private String generateString(int length) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length;i++) {
      builder.append("a");
    }
    return builder.toString();
  }

}
