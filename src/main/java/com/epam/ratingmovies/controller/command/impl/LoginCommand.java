package com.epam.ratingmovies.controller.command.impl;

import com.epam.ratingmovies.Attribute;
import com.epam.ratingmovies.LineHasher;
import com.epam.ratingmovies.controller.ParameterTaker;
import com.epam.ratingmovies.controller.command.Command;
import com.epam.ratingmovies.controller.command.CommandName;
import com.epam.ratingmovies.controller.command.CommandResponse;
import com.epam.ratingmovies.controller.command.Parameter;
import com.epam.ratingmovies.controller.command.request.RequestContext;
import com.epam.ratingmovies.dao.entity.User;
import com.epam.ratingmovies.dao.entity.UserRole;
import com.epam.ratingmovies.dao.entity.UserStatus;
import com.epam.ratingmovies.service.UserService;
import java.util.Optional;

public class LoginCommand implements Command {

    private static final String PROFILE_PAGE_COMMAND = "ratingMovies?command=" + CommandName.PROFILE_PAGE + "&id=";
    private static final String INCORRECT_DATA_KEY = "incorrect";
    private static final String FREEZE_USER_KEY = "banned";
    private static final UserService service = UserService.getInstance();
    public static final String LOGIN = "/jsp/pages/login.jsp";


    @Override
    public CommandResponse execute(RequestContext request) {
        String login = ParameterTaker.takeString(Parameter.LOGIN, request);
        String pass = ParameterTaker.takeString(Parameter.PASSWORD, request);
        LineHasher lineHasher = new LineHasher();

        //todo пароль не солирую хуйня праоль
        String hashPass = lineHasher.hashingLine(pass);
        Optional<User> user = service.findUserByLoginAndPassword(login,pass);
        if (user.isPresent()) {
            if (user.get().getUserStatus() != UserStatus.BANNED) {
                long id = user.get().getId();
                UserRole role = user.get().getUserRole();
                request.addSession(Attribute.USER_ID, id);
                request.addSession(Attribute.ROLE, role);
                request.addSession(Attribute.LOGIN, user.get().getLogin());
                return CommandResponse.redirect(PROFILE_PAGE_COMMAND + id);
            }
            request.addAttribute(Attribute.ERROR_MESSAGE, FREEZE_USER_KEY);
        } else {
            request.addAttribute(Attribute.ERROR_MESSAGE, INCORRECT_DATA_KEY);
        }
        request.addAttribute(Attribute.SAVED_LOGIN, login);
        return CommandResponse.forward(LOGIN);
    }
    }

