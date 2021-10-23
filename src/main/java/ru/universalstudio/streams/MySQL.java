package ru.universalstudio.streams;

import java.sql.*;
import java.util.*;
import ru.universalstudio.streams.common.*;
import ru.universalstudio.streams.youtube.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class MySQL {

    private String host;
    private String username;
    private String password;
    private String database;
    private Connection connection;
    private String port;

    public MySQL(String host, String username, String password, String database, String port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.execute("CREATE TABLE IF NOT EXISTS `UStreams` (`id` int NOT NULL AUTO_INCREMENT, `username` varchar(255) NOT NULL, `url` varchar(255) NOT NULL,  PRIMARY KEY (`id`)) DEFAULT CHARSET=utf8 COLLATE utf8_bin AUTO_INCREMENT=0");
            this.startUpd();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed() && this.connection.isValid(20)) {
                return this.connection;
            }
            Properties var1 = new Properties();
            var1.setProperty("user", this.username);
            var1.setProperty("password", this.password);
            var1.setProperty("useUnicode", "true");
            var1.setProperty("characterEncoding", "cp1251");
            var1.setProperty("autoReconnect", "true");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, var1);
            return this.connection;
        }catch(Exception ex) {
            return null;
        }
    }

    public void execute(String query) {
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startUpd() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, Stream> map = new HashMap<>();
                    ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM `UStreams`");
                    while (resultSet.next()) {
                        String string = resultSet.getString("username");
                        Stream stream = (Stream) StreamManager.getStreams().get(string.toLowerCase());
                        stream = new Stream(string, resultSet.getString("url"));
                        map.put(string.toLowerCase(), stream);
                    }
                    StreamManager.getStreams().clear();
                    StreamManager.getStreams().putAll(map);
                    resultSet.close();
                    Thread.sleep(20L);
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (Stream stream : StreamManager.getStreams().values()) {
                        Live live = new Live(stream.getUrl());
                        stream.setActive(true);
                        stream.setViews(live.getViews());
                        stream.setTitle(live.getTitle());
                        stream.setDislikes(live.getDisLike());
                        stream.setLikes(live.getLikes());
                    }
                    Thread.sleep(300000L);
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void disconnect() {
        try {
            this.getConnection().close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
