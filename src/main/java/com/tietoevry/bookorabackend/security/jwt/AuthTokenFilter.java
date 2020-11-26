package com.tietoevry.bookorabackend.security.jwt;

import com.tietoevry.bookorabackend.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//OncePerRequestFilter makes a single execution for each request to our API

//doFilterInternal() method that we will implement parsing & validating JWT,
//loading User details (using UserDetailsService), checking Authorization (using UsernamePasswordAuthenticationToken).

/**
 * Class that used to filter every request.
 */
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * doFilterInternal do 6 things:
     * 1. Get JWT from the Authorization header (by removing Bearer prefix)
     * 2. if the request has JWT, validate it
     * 3. Fetch username from the JWT
     * 4. From username, get UserDetails to create an Authentication object
     * 5. Add request into the authentication
     * 6. Set the current UserDetails in SecurityContext
     *
     * @param request A HttpServletRequest request
     * @param response A HttpServletResponse response
     * @param filterChain A FilterChain
     * @throws ServletException if there is a servlet error
     * @throws IOException if there is a IO error
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            //1. get JWT from the Authorization header (by removing Bearer prefix)
            String jwt = parseJwt(request);

            //2. if the request has JWT, validate it
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                //3. parse username from it
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                //4. From username, get UserDetails to create an Authentication object
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                //5. Add request into the authentication
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //6. set the current UserDetails in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);

                //After setting in the authentication, you can get UserDetail by calling SecurityContextHolder
/*				userDetails =
						(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				userDetails.getUsername();
				userDetails.getPassword();
				userDetails.getAuthorities(); */
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        //Causes the next filter in the chain to be invoked
        filterChain.doFilter(request, response);
    }

    // Method to get JWT from the Authorization header (by removing Bearer prefix)
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
