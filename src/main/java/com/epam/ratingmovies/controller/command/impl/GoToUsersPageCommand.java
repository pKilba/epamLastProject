package com.epam.ratingmovies.controller.command.impl;

import com.epam.ratingmovies.Attribute;
import com.epam.ratingmovies.controller.ParameterTaker;
import com.epam.ratingmovies.controller.command.Command;
import com.epam.ratingmovies.controller.command.CommandResponse;
import com.epam.ratingmovies.controller.command.Parameter;
import com.epam.ratingmovies.controller.command.request.RequestContext;
import com.epam.ratingmovies.dao.entity.User;
import com.epam.ratingmovies.dao.impl.UserDaoImpl;
import com.epam.ratingmovies.service.UserService;
import com.google.protobuf.ServiceException;

import java.util.List;

public class GoToUsersPageCommand implements Command {
    public static final String USERS = "/jsp/pages/users.jsp";
    UserService userService = new UserService();


    //todo мб вынести в отдельный класс
    @Override
    public CommandResponse execute(RequestContext request) throws ServiceException {
        int page = ParameterTaker.takeNumber(Parameter.PAGE, request);
        int size = ParameterTaker.takeNumber(Parameter.SIZE, request);
        int amount = userService.findUsersAmount();
        int amountQuery = (page - 1) * size;
        if (amountQuery > amount) {
        }
        if (amount < size) {
            size = (int) amount;
        }
        List<User> userList = userService.findUsersRange(amountQuery, size);
        int maxPage = (int) (amount / size);
        if (amount % size != 0) {
            ++maxPage;
        }
        request.addAttribute(Attribute.USER_LIST, userList);
        request.addAttribute(Attribute.CURRENT_PAGE, page);
        request.addAttribute(Attribute.AMOUNT_OF_PAGE, size);
        request.addAttribute(Attribute.MAX_PAGE, maxPage);
        request.addAttribute(Attribute.USER_LIST, userList);

        return CommandResponse.forward(USERS);
    }
}
