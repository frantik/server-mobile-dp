package com.dvdprime.server.mobile.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.bo.MemberBO;
import com.sun.jersey.api.view.Viewable;

/**
 * 회원 목록 화면
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 12. 9. 오전 11:34:51
 * @history
 */
@Path("/web/members")
@Produces(MediaType.TEXT_HTML)
public class MembersWebView {
    /** Logger */
    private Logger logger = LoggerFactory.getLogger(MembersWebView.class);

    @Context
    HttpServletRequest request;

    /**
     * 회원 목록 화면
     * 
     * @return
     */
    @GET
    public Viewable doGet() {
        try {
            request.setAttribute("memberList", new MemberBO().searchMemberList(null));
            return new Viewable("/member/memberList.jsp");
        } catch (Exception e) {
            logger.error("caught a " + e.getClass() + " with message: " + e.getMessage(), e);
            return new Viewable("/index.jsp");
        }
    }

}
