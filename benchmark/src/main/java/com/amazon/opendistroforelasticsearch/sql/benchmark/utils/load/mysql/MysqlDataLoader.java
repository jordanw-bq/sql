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

package com.amazon.opendistroforelasticsearch.sql.benchmark.utils.load.mysql;

import com.amazon.opendistroforelasticsearch.sql.benchmark.BenchmarkService;
import com.amazon.opendistroforelasticsearch.sql.benchmark.utils.BenchmarkConstants;
import com.amazon.opendistroforelasticsearch.sql.benchmark.utils.load.DataFormat;
import com.amazon.opendistroforelasticsearch.sql.benchmark.utils.load.DataLoader;
import com.datastax.driver.core.Cluster;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Data loader for MySQL database.
 */
public class MysqlDataLoader implements DataLoader {

  private static final String URL = "jdbc:mysql://localhost/";
  private String authUrl;
  private Connection connection = null;
  private Statement statement = null;

  /**
   * Constructor for MysqlDataLoader.
   */
  public MysqlDataLoader() {
    authUrl = URL + "?user=" + BenchmarkService.mysqlUsername + "&password="
        + BenchmarkService.mysqlPassword;
  }

  /**
   * Load function for MySQL databases.
   *
   * @param data Data to load in MysqlDataFormat.
   * @throws Exception Throws an Exception if data does not match expected type.
   */
  @Override
  public void loadData(DataFormat data) throws Exception {
    if (!(data instanceof MysqlDataFormat)) {
      throw new IllegalArgumentException("Wrong data format for MySQL.");
    }

    Class.forName("com.mysql.cj.jdbc.Driver");
    connection = DriverManager.getConnection(authUrl);
    statement = connection.createStatement();

    createDatabase();
    createTables();

    Map<String, LinkedList<String>> list = ((MysqlDataFormat) data).getTableDataFilesList();

    for (String table : list.keySet()) {
      for (String file : list.get(table)) {
        String query =
            "LOAD DATA LOCAL INFILE '" + file + "' INTO TABLE " + MysqlTpchSchema.databaseName + "."
                + table + " FIELDS TERMINATED BY ',' ENCLOSED BY '\"'"
                + " LINES TERMINATED BY '\n'";
        statement.executeUpdate(query);
      }
    }
  }

  private void createDatabase() throws Exception {
    statement.executeUpdate("SET GLOBAL local_infile=1;");
    statement.executeUpdate("create database " + MysqlTpchSchema.databaseName);
  }

  private void createTables() throws Exception {
    for (String tablename : MysqlTpchSchema.schemaMap.keySet()) {
      String sql = "create table " + MysqlTpchSchema.databaseName + "." + tablename + " (";

      StringJoiner fields = new StringJoiner(", ", "", "");
      MysqlTpchSchema.schemaMap.get(tablename).entrySet()
          .forEach(field -> fields.add(field.getKey() + " " + field.getValue()));
      sql += fields.toString();

      StringJoiner primaryKey = new StringJoiner(", ", ", primary key (", ")");
      MysqlTpchSchema.primaryKeyMap.get(tablename).forEach(table -> primaryKey.add(table));
      sql += primaryKey.toString();

      if (MysqlTpchSchema.foreignKeyMap.containsKey(tablename)) {
        for (String field : MysqlTpchSchema.foreignKeyMap.get(tablename).keySet()) {
          sql += ", foreign key (" + field + ") references ";
          for (String key : MysqlTpchSchema.foreignKeyMap.get(tablename).get(field).keySet()) {
            sql +=
                key + "(" + MysqlTpchSchema.foreignKeyMap.get(tablename).get(field).get(key) + ")";
          }
        }
      }
      sql += ");";
      statement.executeUpdate(sql);
    }
  }
}
