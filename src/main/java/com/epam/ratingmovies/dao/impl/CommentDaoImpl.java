package com.epam.ratingmovies.dao.impl;

import com.epam.ratingmovies.dao.api.CommentDao;
import com.epam.ratingmovies.dao.connectionpool.api.ConnectionPool;
import com.epam.ratingmovies.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.ratingmovies.dao.entity.Comment;
import com.epam.ratingmovies.dao.entity.Movie;
import com.epam.ratingmovies.exception.DaoException;
import com.epam.ratingmovies.dao.mapper.api.RowMapper;
import com.epam.ratingmovies.dao.mapper.impl.CommentRowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentDaoImpl implements CommentDao {


    private final RowMapper<Comment> mapper = new CommentRowMapper();
    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
    private static final String
            SQL_SAVE_COMMENT = "INSERT INTO comments(message,movie_id," +
            "user_id,create_time)" +
            " values (?,?,?,?)";
    private static final String SQL_FIND_ALL_COMMENTS = "SELECT * FROM comments";

    private static final String SQL_FIND_BY_ID_MOVIES =
            "SELECT * FROM comments WHERE movie_id = ?";

    private static final String SQL_FIND_COMMENTS_RANGE =
            "SELECT * FROM comments ORDER BY " +
                    "create_time DESC LIMIT ?,?" ;



    @Override
    public Comment save(Comment comment) throws DaoException {
        Connection connection = connectionPool.takeConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_COMMENT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, comment.getMessage());
            preparedStatement.setLong(2, comment.getMovieId());
            preparedStatement.setLong(3, comment.getUserId());
            preparedStatement.setTimestamp(4, comment.getCreateTimeComment());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    comment.setId(generatedKeys.getLong(1));
                } else {
                    throw new DaoException("Creating user failed, no ID obtained.");
                }
                preparedStatement.close();
                connectionPool.returnConnection(connection);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return comment;
    }

    public List<Comment> findCommentsRange(int offset, int amount) throws DaoException {
        Connection connection = connectionPool.takeConnection();
        List<Comment> result = new ArrayList<>();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_COMMENTS_RANGE)) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, amount);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = mapper.map(resultSet);
                result.add(comment);
            }
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }


    public int findCommentsAmountByMovieId(long id) throws DaoException {
        ResultSet resultSet = null;
        int result = 0;
        Connection connection = connectionPool.takeConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID_MOVIES)) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                result++;
            }
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }

        }
        return result;
    }

    public int findCommentsAmount() throws DaoException {
        Connection connection = connectionPool.takeConnection();
        int counter = 0;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_COMMENTS);
                ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                counter++;
            }
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return counter;
    }


    public List<Comment> findCommentByIdMovies(long id) throws DaoException {
        ResultSet resultSet = null;
        ArrayList<Comment> result = new ArrayList<Comment>();
        Connection connection = connectionPool.takeConnection();
        try (
                PreparedStatement statement =
                        connection.prepareStatement(SQL_FIND_BY_ID_MOVIES)) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Comment comment = mapper.map(resultSet);

                result.add(comment);
            }
            resultSet.close();
            connectionPool.returnConnection(connection);

        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }

        }
        return result;
    }

    @Override
    public Comment update(Comment entity) {
        return null;
    }


    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Comment> findAll() throws DaoException {
        Connection connection = connectionPool.takeConnection();
        List<Comment> result = new ArrayList<>();
        try (
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_COMMENTS);
            ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                Comment comment = mapper.map(resultSet);
                result.add(comment);
            }
            connectionPool.returnConnection(connection);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<Comment> findById(Long idEntity) {
        return Optional.empty();
    }
}
