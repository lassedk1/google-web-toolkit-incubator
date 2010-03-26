/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


package com.google.gwt.demos.validation.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.libideas.validation.client.ErrorHandler;
import com.google.gwt.libideas.validation.client.Subject;
import com.google.gwt.libideas.validation.client.ValidationException;
import com.google.gwt.libideas.validation.client.Validator;
import com.google.gwt.libideas.validation.client.ValidatorController;
import com.google.gwt.libideas.validation.client.validator.BuiltInValidator;
import com.google.gwt.libideas.validation.client.validator.DateRangeValidator;
import com.google.gwt.libideas.validation.client.validator.RegExValidator;
import com.google.gwt.libideas.validation.client.validator.USPhoneValidator;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ValidationDemo implements EntryPoint {

  /**
   * This is the entry point method.
   */
  
  private static final String CRIME_CODE_FORMAT = "[0-9]{2}-[0-9]{3}";
  private static final RegExValidator crimeCodeFormatValidator = new RegExValidator(
      CRIME_CODE_FORMAT) {

    public String createErrorMessage(String label, String answer) {
      return label
          + " with value "
          + answer
          + " is not a correctly formatted crime code. Crime codes are formatted 00-000.";
    }
  };

  RegExValidator v;
  private FlexTable booking;
  private int curRow;

  /**
   * This is a sample of a simple form using the validation framework.
   */
  public void onModuleLoad() {
    createBookingForm();
  }

  /**
   * Create a boring text field.
   */
  private TextBox addTextField(String field) {
    TextBox box = new TextBox();
    box.setTitle(field);
    booking.setText(curRow, 0, field + ":");
    booking.setWidget(curRow, 1, box);
    ++curRow;
    return box;
  }

  /**
   * Create the sample booking form.
   */
  private void createBookingForm() {

    // /////////////////////////////////////////////
    // World's most boring ui for a form.
    // ////////////////////////////////////////////
    booking = new FlexTable();
    booking.getColumnFormatter().setStyleName(0, "bookingLabel");
    RootPanel.get().add(booking);

    addTextField("Name");
    TextBox birth = addTextField("Birth");
    final TextBox crimeCode = addTextField("Crime Code");
    TextBox dateOfCrime = addTextField("Date of Crime");
    addTextField("Arresting Officer");
    TextBox phone = addTextField("Phone Number");

    // /////////////////////////////////////////////
    // Validation Support.
    // ////////////////////////////////////////////

    // Cannot book anyone younger than 18 or older than 100.
    Validator crimeValidator = new DateRangeValidator(offsetDate(100),
        offsetDate(18));
    ValidatorController crimeValidation = ValidatorController.addAsFocusListener(
        birth, crimeValidator);

    // Crime codes must be correctly formatted.
    ValidatorController.addAsFocusListener(crimeCode, crimeCodeFormatValidator);

    // Phone numbers can be pushed into shape if they are almost correctly
    // formatted.
    ValidatorController.addAsFocusListener(phone, new USPhoneValidator());

    // Creates a custom validator.
    Validator statuteValidator = createStatuteValidator(crimeCode);

    // Can add the same validator to multiple subjects to do multi-field
    // validation.
    crimeValidation.addValidator(statuteValidator);
    ValidatorController.addAsFocusListener(dateOfCrime, statuteValidator);

    // Cannot book someone for a crime more than 30 years old.
    ValidatorController.addAsFocusListener(dateOfCrime, new DateRangeValidator(
        offsetDate(30), new Date()));
  }

  /**
   * This validator checks that the statute of limitations has not run out for
   * the given crime code. The Mayberry judicial system encodes the statute of
   * limitations as the first two digits of the crime code; this is checked
   * against the date of the crime.
   */
  private Validator createStatuteValidator(final TextBox crimeCode) {
    // Now, to show a custom validator. Using BuiltInValidator because we want
    // to use the built-in error messages for Date conversion.
    Validator statuteValidator = new BuiltInValidator() {
      public void checkValid(Subject subject, ErrorHandler handler) {
        String code = crimeCode.getText();
        Date dateOfCrime = null;
        try {
          dateOfCrime = getDate(subject);
        } catch (ValidationException e) {
          // Not our problem if the dateOfCrime cannot be formatted as a date.
        }
        if (dateOfCrime == null) {
          return;
        }

        if (code.matches(CRIME_CODE_FORMAT)) {
          int years = statuteOfLimitations(code.trim());
          Date lastValidDate = offsetDate(years);
          if (dateOfCrime.before(lastValidDate)) {
            // We could get this information from a messages file in we needed
            // internationalization.
            handler.reportError(subject, "Statute of limitations has expired.");
          }
        }
      }

    };
    return statuteValidator;
  }

  private Date offsetDate(int years) {
    Date curDate = new Date();
    curDate.setYear(curDate.getYear() - years);
    return curDate;
  }

  /**
   * When does the statute of limitations run out for this crime?
   */
  private int statuteOfLimitations(String crimeCode) {
    // Mayberry encodes the statute of limitations in the first two digits of
    // its crime codes. In a real world example, this would probably be replaced
    // by either an RPC call or a Map of codes.
    String firstTwoDigits = crimeCode.substring(0, 2);
    return Integer.parseInt(firstTwoDigits);
  }
}
