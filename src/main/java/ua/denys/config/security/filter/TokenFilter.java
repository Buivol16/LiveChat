package ua.denys.config.security.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TokenFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    var request = (HttpServletRequest) servletRequest;
    var response = (HttpServletResponse) servletResponse;

    if (request.getRequestURI().equals("/token")) {
      var token = request.getHeader("Authorization");
      if (!token.equals("TOKEN")) {
        response.sendError(228);
        return;
      }
      request.authenticate(response);
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }
}
