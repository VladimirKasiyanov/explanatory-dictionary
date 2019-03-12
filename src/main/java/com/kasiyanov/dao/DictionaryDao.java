package com.kasiyanov.dao;

import com.kasiyanov.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DictionaryDao {

    private static final DictionaryDao INSTANCE = new DictionaryDao();
    private static final String GET_ALL_FROM_DICTIONARY_1 = "SELECT word, reference_id FROM dictionary.dictionary_1";
    private static final String GET_ALL_FROM_DICTIONARY_2 = "SELECT word, reference_id FROM dictionary.dictionary_2";
    private static final String GET_ALL_FROM_BOTH_DICTIONARIES =
            "SELECT word, reference_id " +
                    "FROM dictionary.dictionary_1 " +
                    "UNION " +
                    "SELECT word, reference_id " +
                    "FROM dictionary.dictionary_2 " +
                    "WHERE word IN (SELECT word " +
                    "               FROM dictionary.dictionary_2 " +
                    "                 EXCEPT " +
                    "               SELECT word " +
                    "               FROM dictionary.dictionary_1)";

    public Map<String, Integer> getAllByJavaMerge() {
        Map<String, Integer> resultMap = new HashMap<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatementByFirstTable = connection.prepareStatement(GET_ALL_FROM_DICTIONARY_1);
             PreparedStatement preparedStatementBySecondTable = connection.prepareStatement(GET_ALL_FROM_DICTIONARY_2)) {
            ResultSet resultSet = preparedStatementByFirstTable.executeQuery();
            while (resultSet.next()) {
                resultMap.put(resultSet.getString("word"), resultSet.getInt("reference_id"));
            }
            resultSet = preparedStatementBySecondTable.executeQuery();
            while (resultSet.next()) {
                resultMap.putIfAbsent(resultSet.getString("word"), resultSet.getInt("reference_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public Map<String, Integer> getAllBySqlMerge() {
        Map<String, Integer> resultMap = new HashMap<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatementByFirstTable = connection.prepareStatement(GET_ALL_FROM_BOTH_DICTIONARIES)) {
            ResultSet resultSet = preparedStatementByFirstTable.executeQuery();
            while (resultSet.next()) {
                resultMap.put(resultSet.getString("word"), resultSet.getInt("reference_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static DictionaryDao getINSTANCE() {
        return INSTANCE;
    }
}