package com.epam.ratingmovies;

import com.epam.ratingmovies.dao.api.MovieDao;
import com.epam.ratingmovies.dao.connectionpool.api.ConnectionPool;
import com.epam.ratingmovies.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.ratingmovies.dao.entity.*;
import com.epam.ratingmovies.dao.impl.MovieDaoImpl;
import com.epam.ratingmovies.dao.impl.UserDaoImpl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        MovieDao movieDaoTest = new MovieDaoImpl();

     List lsit =  movieDaoTest.findAll();
        System.out.println(lsit);


    }
}
