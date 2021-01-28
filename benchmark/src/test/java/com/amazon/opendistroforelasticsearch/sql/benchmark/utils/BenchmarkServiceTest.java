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

package com.amazon.opendistroforelasticsearch.sql.benchmark.utils;

import com.amazon.opendistroforelasticsearch.sql.benchmark.BenchmarkService;
import org.junit.jupiter.api.Test;

public class BenchmarkServiceTest {

  String configFilePath = "config.json";

  @Test
  public void runbenchmarkService() {
    try {
      BenchmarkService.main(new String[]{configFilePath});
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}