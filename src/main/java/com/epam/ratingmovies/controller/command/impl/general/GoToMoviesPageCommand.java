package com.epam.ratingmovies.controller.command.impl.general;

import com.epam.ratingmovies.controller.command.CommandResponse;
import com.epam.ratingmovies.controller.command.api.Command;
import com.epam.ratingmovies.controller.command.request.RequestContext;
import com.epam.ratingmovies.exception.ServiceException;
import com.epam.ratingmovies.service.MovieService;
import com.epam.ratingmovies.service.MoviesPagesWithPagination;

public class GoToMoviesPageCommand implements Command {

    public static final MovieService movieService = MovieService.getInstance();
    public static final String MOVIES = "/jsp/pages/movies.jsp" ;

    @Override
    public CommandResponse execute(RequestContext request) throws ServiceException {


        MoviesPagesWithPagination moviesPagesWithPagination = MoviesPagesWithPagination.getInstance();
        moviesPagesWithPagination.processCommandWithPagination(request);
        return CommandResponse.forward(MOVIES);
    }
}
