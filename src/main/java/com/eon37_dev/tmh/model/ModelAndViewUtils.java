package com.eon37_dev.tmh.model;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class ModelAndViewUtils {
  public static ModelAndView buildView(String viewName, Map<String, ?> model, HttpServletRequest request) {
    ModelAndView mav = new ModelAndView(viewName, model);

    mav.addObject("theme", retrieveTheme(request));

    return mav;
  }

  public static ModelAndView buildRedirect(String redirectName, Map<String, ?> model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
    model.forEach(redirectAttributes::addFlashAttribute);

    redirectAttributes.addFlashAttribute("theme", retrieveTheme(request));

    return new ModelAndView("redirect:" + redirectName);
  }

  public static ModelAndView buildRedirect(String redirectName, Map<String, ?> model, RedirectAttributes redirectAttributes, String theme) {
    model.forEach(redirectAttributes::addFlashAttribute);

    redirectAttributes.addFlashAttribute("theme", theme);

    return new ModelAndView("redirect:" + redirectName);
  }

  private static String retrieveTheme(HttpServletRequest request) {
    return Optional.ofNullable(request.getCookies())
            .flatMap(cookies -> Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("theme")).findFirst())
            .map(Cookie::getValue)
            .orElse("dark");
  }
}
