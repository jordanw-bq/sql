/*
 *   Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

package com.amazon.opendistroforelasticsearch.sql.benchmark.utils.query.mock;

import com.amazon.opendistroforelasticsearch.sql.benchmark.utils.query.QueryRunner;

/**
 * Query runner for testing purposes.
 */
public class MockQueryRunner extends QueryRunner {

  private static final long MIN_WAIT_TIME_MILLISECONDS = 1500L;
  private static final long MAX_WAIT_TIME_MILLISECONDS = 5000L;
  private String query;

  /**
   * Function mock running query.
   */
  @Override
  public void runQuery() {
    try {
      final long randomDelay = (long) (
          (MAX_WAIT_TIME_MILLISECONDS - MIN_WAIT_TIME_MILLISECONDS) * Math.random());
      Thread.sleep(MIN_WAIT_TIME_MILLISECONDS + randomDelay);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Funtion to set query runner.
   *
   * @param query Query to run against the specified database.
   */
  @Override
  public void prepareQueryRunner(final String query) {
    this.query = query;
  }

  /**
   * Function to check query execution status.
   */
  @Override
  public void checkQueryExecutionStatus(final String benchmarkPath) {

  }
}