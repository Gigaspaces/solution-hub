package com.vaannila.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.vaannila.dao.UserDAO;
import com.vaannila.domain.User;

public class UserController extends MultiActionController {

	private UserDAO userDAO;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response, User user) throws Exception {
		userDAO.saveUser(user);
		return new ModelAndView("redirect:list.htm");
	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("userList", userDAO.listUser());
		modelMap.addAttribute("user", new User());
		return new ModelAndView("userForm", modelMap);
	}
}
