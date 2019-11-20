package com.jerrysong.securitylearn.source;//package com.jerrysong.security.source;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
//import org.springframework.security.web.util.UrlUtils;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class LogoutFilter extends GenericFilterBean {
//
//    // ~ Instance fields
//    // ================================================================================================
//
//    private RequestMatcher logoutRequestMatcher;
//
//    private final LogoutHandler handler;
//    private final LogoutSuccessHandler logoutSuccessHandler;
//
//    // ~ Constructors
//    // ===================================================================================================
//
//    /**
//     * Constructor which takes a <tt>LogoutSuccessHandler</tt> instance to determine the
//     * target destination after logging out. The list of <tt>LogoutHandler</tt>s are
//     * intended to perform the actual logout functionality (such as clearing the security
//     * context, invalidating the session, etc.).
//     */
//    public LogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
//                        LogoutHandler... handlers) {
//
//        //聚合所有的handlers
//        this.handler = new CompositeLogoutHandler(handlers);
//        Assert.notNull(logoutSuccessHandler, "logoutSuccessHandler cannot be null");
//        this.logoutSuccessHandler = logoutSuccessHandler;
//        setFilterProcessesUrl("/logout");
//    }
//
//    public LogoutFilter(String logoutSuccessUrl, LogoutHandler... handlers) {
//        this.handler = new CompositeLogoutHandler(handlers);
//        Assert.isTrue(
//                !StringUtils.hasLength(logoutSuccessUrl)
//                        || UrlUtils.isValidRedirectUrl(logoutSuccessUrl),
//                () -> logoutSuccessUrl + " isn't a valid redirect URL");
//        SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
//        if (StringUtils.hasText(logoutSuccessUrl)) {
//            urlLogoutSuccessHandler.setDefaultTargetUrl(logoutSuccessUrl);
//        }
//        logoutSuccessHandler = urlLogoutSuccessHandler;
//        setFilterProcessesUrl("/logout");
//    }
//
//    // ~ Methods
//    // ========================================================================================================
//
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//
//        if (requiresLogout(request, response)) {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//            if (logger.isDebugEnabled()) {
//                logger.debug("Logging out user '" + auth
//                        + "' and transferring to logout destination");
//            }
//
//            //迭代执行这些handler的方法
//            this.handler.logout(request, response, auth);
//
//            logoutSuccessHandler.onLogoutSuccess(request, response, auth);
//
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    /**
//     * Allow subclasses to modify when a logout should take place.
//     *
//     * @param request the request
//     * @param response the response
//     *
//     * @return <code>true</code> if logout should occur, <code>false</code> otherwise
//     */
//    protected boolean requiresLogout(HttpServletRequest request,
//                                     HttpServletResponse response) {
//        return logoutRequestMatcher.matches(request);
//    }
//
//    public void setLogoutRequestMatcher(RequestMatcher logoutRequestMatcher) {
//        Assert.notNull(logoutRequestMatcher, "logoutRequestMatcher cannot be null");
//        this.logoutRequestMatcher = logoutRequestMatcher;
//    }
//
//    public void setFilterProcessesUrl(String filterProcessesUrl) {
//        this.logoutRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
//    }
//}
