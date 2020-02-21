/*
 *   Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License").
 *   You may not use this file except in compliance with the License.
 *   A copy of the License is located at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file. This file is distributed
 *   on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *   express or implied. See the License for the specific language governing
 *   permissions and limitations under the License.
 */

package com.amazon.opendistroforelasticsearch.sql.executor.format;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DateFieldFormatterTest {

  @Test
  public void testKibanaSampleDataEcommerceOrderDateField()
  {
    String columnName = "order_date";
    DateFormat dateFormat = DateFormat.DATE_OPTIONAL_TIME;
    String originalDateValue = "2020-02-24T09:28:48+00:00";
    String expectedDateValue = "2020-02-24 09:28:48.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testKibanaSampleDataFlightsTimestampField()
  {
    String columnName = "timestamp";
    DateFormat dateFormat = DateFormat.DATE_OPTIONAL_TIME;
    String originalDateValue = "2020-02-03T00:00:00";
    String expectedDateValue = "2020-02-03 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testKibanaSampleDataLogsUtcDateField()
  {
    String columnName = "utc_date";
    DateFormat dateFormat = DateFormat.DATE_OPTIONAL_TIME;
    String originalDateValue = "2020-02-02T00:39:02.912Z";
    String expectedDateValue = "2020-02-02 00:39:02.912";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testEpochMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.EPOCH_MILLIS;
    String originalDateValue = "727430805000";
    String expectedDateValue = "1993-01-19 08:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testEpochSecond()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.EPOCH_SECOND;
    String originalDateValue = "727430805";
    String expectedDateValue = "1993-01-19 08:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testDateOptionalTimeDateOnly()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_OPTIONAL_TIME;
    String originalDateValue = "1993-01-19";
    String expectedDateValue = "1993-01-19 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testDateOptionalTimeDateAndTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_OPTIONAL_TIME;
    String originalDateValue = "1993-01-19T00:06:45.123-0800";
    String expectedDateValue = "1993-01-19 08:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicDate()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_DATE;
    String originalDateValue = "19930119";
    String expectedDateValue = "1993-01-19 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicDateTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_DATE_TIME;
    String originalDateValue = "19930119T120645.123-0800";
    String expectedDateValue = "1993-01-19 20:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicDateTimeNoMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_DATE_TIME_NO_MILLIS;
    String originalDateValue = "19930119T120645-0800";
    String expectedDateValue = "1993-01-19 20:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicOrdinalDate()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_ORDINAL_DATE;
    String originalDateValue = "1993019";
    String expectedDateValue = "1993-01-19 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicOrdinalDateTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_ORDINAL_DATE_TIME;
    String originalDateValue = "1993019T120645.123-0800";
    String expectedDateValue = "1993-01-19 20:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicOrdinalDateTimeNoMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_ORDINAL_DATE_TIME_NO_MILLIS;
    String originalDateValue = "1993019T120645-0800";
    String expectedDateValue = "1993-01-19 20:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_TIME;
    String originalDateValue = "120645.123-0800";
    String expectedDateValue = "1970-01-01 20:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicTimeNoMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_TIME_NO_MILLIS;
    String originalDateValue = "120645-0800";
    String expectedDateValue = "1970-01-01 20:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicTTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_T_TIME;
    String originalDateValue = "T120645.123-0800";
    String expectedDateValue = "1970-01-01 20:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicTTimeNoMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_T_TIME_NO_MILLIS;
    String originalDateValue = "T120645-0800";
    String expectedDateValue = "1970-01-01 20:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicWeekDate()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_WEEK_DATE;
    String originalDateValue = "1993W042";
    String expectedDateValue = "1993-01-19 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicWeekDateTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_WEEK_DATE_TIME;
    String originalDateValue = "1993W042T120645.123-0800";
    String expectedDateValue = "1993-01-19 20:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testBasicWeekDateTimeNoMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.BASIC_WEEK_DATE_TIME_NO_MILLIS;
    String originalDateValue = "1993W042T120645-0800";
    String expectedDateValue = "1993-01-19 20:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testDate()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE;
    String originalDateValue = "1993-01-19";
    String expectedDateValue = "1993-01-19 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testDateHour()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_HOUR;
    String originalDateValue = "1993-01-19T12";
    String expectedDateValue = "1993-01-19 12:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testDateHourMinute()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_HOUR_MINUTE;
    String originalDateValue = "1993-01-19T12:06";
    String expectedDateValue = "1993-01-19 12:06:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testDateHourMinuteSecond()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_HOUR_MINUTE_SECOND;
    String originalDateValue = "1993-01-19T12:06:45";
    String expectedDateValue = "1993-01-19 12:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testDateHourMinuteSecondFraction()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_HOUR_MINUTE_SECOND_FRACTION;
    String originalDateValue = "1993-01-19T12:06:45.123";
    String expectedDateValue = "1993-01-19 12:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testDateHourMinuteSecondMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_HOUR_MINUTE_SECOND_MILLIS;
    String originalDateValue = "1993-01-19T12:06:45.123";
    String expectedDateValue = "1993-01-19 12:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testDateTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_TIME;
    String originalDateValue = "1993-01-19T12:06:45.123-0800";
    String expectedDateValue = "1993-01-19 20:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testDateTimeNoMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_TIME_NO_MILLIS;
    String originalDateValue = "1993-01-19T12:06:45-0800";
    String expectedDateValue = "1993-01-19 20:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testHour()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.HOUR;
    String originalDateValue = "12";
    String expectedDateValue = "1970-01-01 12:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testHourMinute()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.HOUR_MINUTE;
    String originalDateValue = "12:06";
    String expectedDateValue = "1970-01-01 12:06:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testHourMinuteSecond()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.HOUR_MINUTE_SECOND;
    String originalDateValue = "12:06:45";
    String expectedDateValue = "1970-01-01 12:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testHourMinuteSecondFraction()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.HOUR_MINUTE_SECOND_FRACTION;
    String originalDateValue = "12:06:45.123";
    String expectedDateValue = "1970-01-01 12:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testHourMinuteSecondMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.HOUR_MINUTE_SECOND_MILLIS;
    String originalDateValue = "12:06:45.123";
    String expectedDateValue = "1970-01-01 12:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testOrdinalDate()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.ORDINAL_DATE;
    String originalDateValue = "1993-019";
    String expectedDateValue = "1993-01-19 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testOrdinalDateTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.ORDINAL_DATE_TIME;
    String originalDateValue = "1993-019T12:06:45.123-0800";
    String expectedDateValue = "1993-01-19 20:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testOrdinalDateTimeNoMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.ORDINAL_DATE_TIME_NO_MILLIS;
    String originalDateValue = "1993-019T12:06:45-0800";
    String expectedDateValue = "1993-01-19 20:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.TIME;
    String originalDateValue = "12:06:45.123-0800";
    String expectedDateValue = "1970-01-01 20:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testTimeNoMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.TIME_NO_MILLIS;
    String originalDateValue = "12:06:45-0800";
    String expectedDateValue = "1970-01-01 20:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testTTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.T_TIME;
    String originalDateValue = "T12:06:45.123-0800";
    String expectedDateValue = "1970-01-01 20:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testTTimeNoMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.T_TIME_NO_MILLIS;
    String originalDateValue = "T12:06:45-0800";
    String expectedDateValue = "1970-01-01 20:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testWeekDate()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.WEEK_DATE;
    String originalDateValue = "1993-W04-2";
    String expectedDateValue = "1993-01-19 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testWeekDateTime()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.WEEK_DATE_TIME;
    String originalDateValue = "1993-W04-2T12:06:45.123-0800";
    String expectedDateValue = "1993-01-19 20:06:45.123";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testWeekDateTimeNoMillis()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.WEEK_DATE_TIME_NO_MILLIS;
    String originalDateValue = "1993-W04-2T12:06:45-0800";
    String expectedDateValue = "1993-01-19 20:06:45.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testWeekyear()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.WEEK_YEAR;
    String originalDateValue = "1993";
    String expectedDateValue = "1993-01-01 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testWeekyearWeek()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.WEEKYEAR_WEEK;
    String originalDateValue = "1993-W04";
    String expectedDateValue = "1993-01-17 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testWeekyearWeekDay()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.WEEKYEAR_WEEK_DAY;
    String originalDateValue = "1993-W04-2";
    String expectedDateValue = "1993-01-19 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testYear()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.YEAR;
    String originalDateValue = "1993";
    String expectedDateValue = "1993-01-01 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testYearMonth()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.YEAR_MONTH;
    String originalDateValue = "1993-01";
    String expectedDateValue = "1993-01-01 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testYearMonthDay()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.YEAR_MONTH_DAY;
    String originalDateValue = "1993-01-19";
    String expectedDateValue = "1993-01-19 00:00:00.000";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testIncorrectFormat()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_OPTIONAL_TIME;
    String originalDateValue = "1581724085";
    // Invalid format for date value; should return original value
    String expectedDateValue = "1581724085";

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  @Test
  public void testNullDateData()
  {
    String columnName = "date_field";
    DateFormat dateFormat = DateFormat.DATE_OPTIONAL_TIME;
    String originalDateValue = null;
    // Nulls should be preserved
    String expectedDateValue = null;

    verifyFormatting(columnName, dateFormat, originalDateValue, expectedDateValue);
  }

  private void verifyFormatting(String columnName, DateFormat dateFormat, String originalDateValue, String expectedDateValue)
  {
    List<Schema.Column> columns = buildColumnList(columnName);
    Map<String, String> dateFieldFormatMap = buildDateFieldFormatMap(columnName, dateFormat);

    Map<String, Object> rowSource = new HashMap<>();
    rowSource.put(columnName, originalDateValue);

    DateFieldFormatter dateFieldFormatter = new DateFieldFormatter(dateFieldFormatMap, columns);
    executeFormattingAndCompare(dateFieldFormatter, rowSource, columnName, expectedDateValue);
  }

  private void executeFormattingAndCompare(
      DateFieldFormatter formatter,
      Map<String, Object> rowSource,
      String columnToCheck,
      String expectedDateValue) {
    formatter.applyJDBCDateFormat(rowSource);
    assertEquals(expectedDateValue, rowSource.get(columnToCheck));
  }

  private List<Schema.Column> buildColumnList(String columnName) {
    return ImmutableList.<Schema.Column>builder()
        .add(new Schema.Column(columnName, null, Schema.Type.DATE))
        .build();
  }

  private Map<String, String> buildDateFieldFormatMap(String columnName, DateFormat dateFormat) {
    return ImmutableMap.<String, String>builder()
        .put(columnName, dateFormat.nameLowerCase())
        .build();
  }
}