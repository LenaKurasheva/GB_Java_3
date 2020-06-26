//1. Добавить в сетевой чат авторизацию через базу данных MySQL (SQLite).

package homework.lesson_2;

import java.sql.*;
import java.util.Properties;

//подключается к базе данных с инфомацией о всех пользовтелях чата
public class AuthService {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;


    private class User {
        private String login;
        private String passwd;
        private String nick;


        public User(String login, String passwd, String nick) {
            this.login = login;
            this.passwd = passwd;
            this.nick = nick;
        }
    }

    public AuthService()throws SQLException, ClassNotFoundException {
        getConn();
        createUsersTable();
        createCensorshipTable();
//        writeDB();
//        writeCensorshipDB();
        getDataFromCensorshipTable();
    }

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public void getConn () throws ClassNotFoundException, SQLException
    {
        conn = null;
        Properties prop = new Properties();
        prop.setProperty("useSSL", "false");
        prop.setProperty("serverTimezone", "Europe/Moscow");
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://root:root@localhost:3306/chat_db", prop);
        System.out.println("База Подключена!");
    }

    // --------Создание таблицы 'users'--------
    public void createUsersTable () throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists users \n" +
                "(id INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
                " login VARCHAR(50) unique,\n" +
                " pass VARCHAR(50), \n" +
                " nick VARCHAR(50));");

        System.out.println("Таблица users создана или уже существует.");
    }

    // --------Создание таблицы 'censorship'--------
    public void createCensorshipTable () throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists censorship \n" +
                "(id INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
                " badWord VARCHAR(50) unique,\n" +
                " changeTo VARCHAR(50));");

        System.out.println("Таблица censorship создана или уже существует.");
    }

    // Заполнение таблицы users (Первоначальное заполнение таблицы - используется 1 раз - для чата без возможности регистрации)
    public void writeDB () throws SQLException {
        statmt.execute("INSERT INTO users (login, pass, nick) VALUES ('login1', 'pass1', 'nick1'); ");
        statmt.execute("INSERT INTO users (login, pass, nick) VALUES ('login2', 'pass2', 'nick2'); ");
        statmt.execute("INSERT INTO users (login, pass, nick) VALUES ('login3', 'pass3', 'nick3'); ");

        System.out.println("Таблица users заполнена");
    }

    // Заполнение таблицы censorship (Первоначальное заполнение таблицы - используется 1 раз - для чата без возможности регистрации)
    public void writeCensorshipDB () throws SQLException {
        statmt.execute("INSERT INTO censorship (badWord, changeTo) VALUES ('дурак', 'censored:неумный'); ");
        statmt.execute("INSERT INTO censorship (badWord, changeTo) VALUES ('дурачок', 'censored:неумный'); ");
        statmt.execute("INSERT INTO censorship (badWord, changeTo) VALUES ('дурень', 'censored:(прост.) неумный'); ");

        System.out.println("Таблица censorship заполнена");
    }

    public void start() {
        System.out.println("Authentication service started");
    }

    public void getDataFromCensorshipTable(){
        try {
            resSet = statmt.executeQuery("SELECT * FROM censorship;");
            while (resSet.next()) {
                String badWord = resSet.getString("badWord");
                String changeTo = resSet.getString("changeTo");
                Censorship.censorshipMap.put(badWord,changeTo);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getNickByLoginAndPwd(String login, String passwd) {
        try {
            String sqlCommand = String.format("SELECT nick FROM users WHERE login = '%s' AND pass = '%s'", login, passwd);

            resSet = statmt.executeQuery(sqlCommand);
            if (resSet != null){
                resSet.next();  // передвигаем курсор на первую строку
                String nick = resSet.getString("nick");
                return nick;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    // Изменение ника пользователя в таблице
    public static void changeNickInDB(String login, String newNick) throws SQLException {
        String sqlCommand = String.format("UPDATE users SET nick = '%s' WHERE login = '%s'", newNick, login);
        statmt.execute(sqlCommand);
    }


    // --------Закрытие--------
    public void stop() throws ClassNotFoundException {
        try {
            resSet.close();
            statmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Соединения закрыты");
        System.out.println("Authentication service stopped");
    }
}
