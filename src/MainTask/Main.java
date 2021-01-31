package MainTask;

import MainTask.Actor;

import java.sql.*;
import java.util.*;

//      Задание:
//      Найти все фильмы, вышедшие на экран в текущем и прошлом году.
//      Вывести информацию об актерах, снимавшихся в заданном фильме.
//      Вывести информацию об актерах, снимавшихся как минимум в N фильмах.
//      Удалить все фильмы, дата выхода которых была более заданного числа лет назад.

public class Main {
    public static void main(String[] args) {
        //Общая инициализация
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/videolibrary";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "2553924");
        properties.put("autoReconnect", true);
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        Scanner scanner = new Scanner(System.in);
        // Найти все фильмы, вышедшие на экран в текущем и прошлом году.
        System.out.println("TASK 1 - Найти все фильмы, вышедшие на экран в текущем и прошлом году.");
        try(Connection connection = DriverManager.getConnection(url, properties);
            Statement statement1 = connection.createStatement()){
            String justSQLQuery = "SELECT name FROM films WHERE YEAR(dateRelease) > YEAR(NOW()) - 2";
            ResultSet resultSet = statement1.executeQuery(justSQLQuery);
            List<String> filmsList = new ArrayList<>();
            while (resultSet.next()){
                filmsList.add(resultSet.getNString("name"));
            }
            System.out.println(Collections.singletonList(filmsList));
        } catch (SQLException throwable){
            throwable.printStackTrace();
        }
        // Вывести информацию об актерах, снимавшихся в заданном фильме.
        System.out.println("TASK 2 - Вывести информацию об актерах, снимавшихся в заданном фильме.");
        System.out.println("Введите фильм ");
        try(Connection connection = DriverManager.getConnection(url, properties);
            Statement statement2 = connection.createStatement()){
            String justSQLQuery = "SELECT actors.name, actors.dateBirthday FROM actors\n" +
                    "  INNER JOIN \n" +
                    "  actors_films\n" +
                    "    ON actors.idActors = actors_films.idActor\n" +
                    "  LEFT JOIN films\n" +
                    "    ON actors_films.idFilm = films.idFilm\n" +
                    "    WHERE films.name = " + "'" + scanner.nextLine() + "'" + "\n" +
                    "  GROUP BY actors.name";
            ResultSet resultSet = statement2.executeQuery(justSQLQuery);
            List<Actor> actorsList = new ArrayList<>();
            while (resultSet.next()){
                actorsList.add(new Actor(resultSet.getNString("name"), resultSet.getDate("dateBirthday")));
            }
            System.out.println(Collections.singletonList(actorsList));
        } catch (SQLException throwable){
            throwable.printStackTrace();
        }
        System.out.println("TASK 3 - Вывести информацию об актерах, снимавшихся как минимум в N фильмах.");
        System.out.println("Введите количество фильмов");
        try(Connection connection = DriverManager.getConnection(url, properties);
            Statement statement2 = connection.createStatement()){
            String justSQLQuery = "SELECT actors.name, actors.dateBirthday, COUNT(films.name) AS Quantity_films FROM actors\n" +
                    "INNER JOIN \n" +
                    "  actors_films\n" +
                    "    ON actors.idActors = actors_films.idActor\n" +
                    "  LEFT JOIN films\n" +
                    "    ON actors_films.idFilm = films.idFilm\n" +
                    "  GROUP BY actors.name HAVING COUNT(films.name) >" + "'" + scanner.nextLine() + "'" + "\n";
            ResultSet resultSet = statement2.executeQuery(justSQLQuery);
            List<Actor> actorsList = new ArrayList<>();
            while (resultSet.next()){
                actorsList.add(new Actor(resultSet.getNString("name"), resultSet.getDate("dateBirthday")));
            }
            System.out.println(Collections.singletonList(actorsList));
        } catch (SQLException throwable){
            throwable.printStackTrace();
        }
        System.out.println("TASK 4 - Удалить все фильмы, дата выхода которых была более заданного числа лет назад.");
        System.out.println("Введите количество лет в формате ГГГГ-ММ-ДД");
        try(Connection connection = DriverManager.getConnection(url, properties)){
            String justSQLQuery = "DELETE FROM films WHERE YEAR(dateRelease) > YEAR(NOW) -" + "'" + scanner.nextLine() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(justSQLQuery);
            int countDeleteFilms = preparedStatement.executeUpdate();
            System.out.println(countDeleteFilms);
        } catch (SQLException throwable){
            throwable.printStackTrace();
        }
    }
}
