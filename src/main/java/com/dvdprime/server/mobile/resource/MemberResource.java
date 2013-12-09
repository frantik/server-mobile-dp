/**
 * Copyright 2013 작은광명
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dvdprime.server.mobile.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.bo.MemberBO;
import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.model.MemberDTO;

/**
 * 회원 관리 API
 * 
 * @author 작은광명
 * 
 */
@Path("/member")
@Produces(MediaType.APPLICATION_JSON)
public class MemberResource
{
    /** Logger */
    private final Logger logger = LoggerFactory.getLogger(MemberResource.class);
    
    @Context
    HttpServletRequest request;
    
    @GET
    public Response doGet()
    {
        return Response.ok(ResponseMessage.NOT_FOUND).build();
    }
    
    /**
     * 나쁜 회원 등록
     * 
     * @param id
     *            회원 아이디
     * @return
     */
    @POST
    public Response doPost(@FormParam("id")
    String id)
    {
        logger.info("Member POST params: {}", id);
        
        try
        {
            if (new MemberBO().creationMemberOne(new MemberDTO(id)))
            {
                return Response.ok(ResponseMessage.SUCCESS).build();
            }
            else
            {
                return Response.ok(ResponseMessage.FAIL).build();
            }
        }
        catch (Exception e)
        {
            return Response.ok(ResponseMessage.SERVER_ERROR).build();
        }
    }
    
    @PUT
    public Response doPut()
    {
        return Response.ok(ResponseMessage.NOT_FOUND).build();
    }
    
    /**
     * 회원 정보 삭제
     * 
     * @param id
     *            회원 아이디
     * @return
     */
    @DELETE
    public Response doDelete(@QueryParam("id")
    String id)
    {
        logger.info("Member DELETE params: {}", id);
        
        try
        {
            if (new MemberBO().removeMemberOne(id))
            {
                return Response.ok(ResponseMessage.SUCCESS).build();
            }
            else
            {
                return Response.ok(ResponseMessage.FAIL).build();
            }
        }
        catch (Exception e)
        {
            return Response.ok(ResponseMessage.SERVER_ERROR).build();
        }
    }
}
