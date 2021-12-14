package com.epam.ratingmovies.service;

import com.epam.ratingmovies.dao.entity.Movie;
import com.epam.ratingmovies.dao.impl.MovieDaoImpl;
import com.epam.ratingmovies.dao.impl.UserDaoImpl;
import com.epam.ratingmovies.exception.DaoException;
import com.epam.ratingmovies.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieService {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private static final String FIND_MOVIE_PROBLEM = "Exception find movie  ";
    private static final String SAVE_MOVIE_PROBLEM = "Exception save movie  ";
    MovieDaoImpl movieDao = new MovieDaoImpl();

    public int findMoviesAmount() throws ServiceException {
        try {
            return movieDao.findMoviesAmount();
        } catch (DaoException e) {
            logger.error(FIND_MOVIE_PROBLEM + e );
            throw new ServiceException(FIND_MOVIE_PROBLEM + e);
        }
    }

    public Movie findMovieById(long id) throws ServiceException {
        Optional<Movie> movie;
        try {
            movie = movieDao.findById(id);
        } catch (DaoException e) {
            logger.error(FIND_MOVIE_PROBLEM + e);
            throw new ServiceException(FIND_MOVIE_PROBLEM + e);
        }
        if (movie.isPresent()) {
            return movie.get();
        } else {
            logger.error(FIND_MOVIE_PROBLEM );
            throw new ServiceException(FIND_MOVIE_PROBLEM );
        }
    }


    public void save(Movie movie) throws ServiceException {
        try {
            movieDao.save(movie);
        } catch (DaoException e) {
            logger.error(SAVE_MOVIE_PROBLEM + e);
            throw new ServiceException(SAVE_MOVIE_PROBLEM + e);

        }
    }

    public List findMovies() throws ServiceException {
        try {
            return movieDao.findAll();
        } catch (DaoException e) {
            logger.error(SAVE_MOVIE_PROBLEM +e );
            throw new ServiceException(SAVE_MOVIE_PROBLEM + e);
        }
    }

    public List<Movie> findMoviesRange(int amountQuery, int size, List<Movie> movies) {
        int count = 0;

        //todo проверить правильно или не!!!!

        List<Movie> result = new ArrayList<>();

        for (Movie movie : movies) {
            if (amountQuery < count && count <= amountQuery + size) {
                result.add(movie);
            }
            count++;
        }

        return result;


    }
}
