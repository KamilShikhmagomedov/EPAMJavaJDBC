package OptionalTask1;

// Задание:
// В БД хранится англо-русский словарь, в котором для одного английского слова может быть указано
// несколько его значений и наоборот. Со стороны клиента вводятся последовательно английские (русские) слова.
// Для каждого из них вывести на консоль все русские (английские) значения слова.

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Общая инициализация
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/dictionary";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "2553924");
        properties.put("autoReconnect", true);
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        //Выполнение задачи
        List<String> listEngWords = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, properties);
            Statement statement = connection.createStatement()){
            String justSentence = "Просто сделай это"; // Переводим слова на английский
            StringTokenizer tokenizer = new StringTokenizer(justSentence.toLowerCase(), " ");
            while (tokenizer.hasMoreTokens()){
                String justSQLQuery = "SELECT eng FROM eng_rus WHERE rus =" + "'" + tokenizer.nextToken() + "'";
                ResultSet resultSet = statement.executeQuery(justSQLQuery);
                if (resultSet.next()){
                    String returnWord = resultSet.getNString("eng");
                    listEngWords.add(returnWord);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(Collections.singleton(listEngWords));
    }
}
