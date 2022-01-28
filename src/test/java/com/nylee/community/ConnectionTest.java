package com.nylee.community;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootTest
public class ConnectionTest {

    // MySQL Connector 의 클래스. DB 연결 드라이버 정의
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // DB 경로
    private static final String URL = "jdbc:mysql://localhost:3306/ny?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "nylee";
    private static final String PASSWORD = "1234";

    @Test
    public void testConnection() throws Exception {

        // DBMS에게 DB 연결 드라이버의 위치를 알려주기 위한 메소드
        Class.forName(DRIVER);
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Autowired
//    private SqlSessionFactory sqlSessionFactory;
//
//    @Test
//    public void connection_test(){
//        try(Connection con = sqlSessionFactory.openSession().getConnection()){
//            System.out.println("커넥션 성공");
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
}
