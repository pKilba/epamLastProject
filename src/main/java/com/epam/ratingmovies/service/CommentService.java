package com.epam.ratingmovies.service;

import com.epam.ratingmovies.dao.entity.Comment;
import com.epam.ratingmovies.dao.impl.CommentDaoImpl;
import com.epam.ratingmovies.dao.impl.UserDaoImpl;
import com.epam.ratingmovies.exception.DaoException;
import com.epam.ratingmovies.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CommentService {

    private static final String FIND_COMMENTS_PROBLEM ="Exception find comments";
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    CommentDaoImpl commentDao = new CommentDaoImpl();

    public List findAll() throws ServiceException {
        try {
            return commentDao.findAll();
        } catch (DaoException e) {
            logger.error(FIND_COMMENTS_PROBLEM+e);
            throw new ServiceException(FIND_COMMENTS_PROBLEM + e );
        }
    }
    public List findByMovieId( long id) throws ServiceException {
        try {
            return commentDao.findCommentByIdMovies(id);
        } catch (DaoException e) {
            logger.error(FIND_COMMENTS_PROBLEM +e);
            throw new ServiceException(FIND_COMMENTS_PROBLEM + e );

        }
    }

    public int findCommentsAmountByMovieId(long id) throws ServiceException {
        try {
            return commentDao.findCommentsAmountByMovieId( id);
        } catch (DaoException e) {
            logger.error(FIND_COMMENTS_PROBLEM + e);
            throw new ServiceException(FIND_COMMENTS_PROBLEM + e );
        }
    }

    public List findCommentByRange(int offset, int amount, List<Comment> comments){
        int count = 0;

        //todo проверить правильно или не!!!!
        
        List<Comment> result = new ArrayList<>();

               for (Comment comment :comments){
                   if (offset < count && count <= offset + amount) {
                       result.add(comment);
                   }
                   count++;
               }

        return result;

    }

    public Comment save (Comment comment) throws ServiceException {
        try {
            return commentDao.save(comment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    public int findCommentsAmount() throws ServiceException {
        try {
            return commentDao.findCommentsAmount();
        } catch (DaoException e) {
            logger.error(FIND_COMMENTS_PROBLEM + e);
            throw new ServiceException(FIND_COMMENTS_PROBLEM + e );
        }
    }
    public List<Comment> findCommentsRange(int amountQuery, int size) throws ServiceException {

        try {
            return commentDao.findCommentsRange(amountQuery, size);
        } catch (DaoException e) {
            logger.error(FIND_COMMENTS_PROBLEM +e);
            throw new ServiceException(FIND_COMMENTS_PROBLEM + e );
        }
    }

}
