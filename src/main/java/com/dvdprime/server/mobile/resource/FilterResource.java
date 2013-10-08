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

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.bo.FilterBO;
import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.request.FilterRequest;

/**
 * 필터 등록/삭제
 * 
 * @author 작은광명
 * 
 */
@Path("/filter")
@Produces(MediaType.APPLICATION_JSON)
public class FilterResource
{
    /** Logger */
    private final Logger logger = LoggerFactory.getLogger(FilterResource.class);
    
    @GET
    public Response Get()
    {
        return Response.ok(ResponseMessage.NOT_FOUND).build();
    }
    
    /**
     * 필터 정보 등록
     * 
     * @param id
     *            아이디
     * @param targetId
     *            지정 아이디
     * @param targetNick
     *            지정 닉네임
     * @return
     */
    @POST
    public Response doPost(@FormParam("id")
    String id, @FormParam("targetId")
    String targetId, @FormParam("targetNick")
    String targetNick)
    {
        FilterRequest params = new FilterRequest(id, targetId, targetNick);
        logger.info("Filter POST params: {0}", params);
        
        try
        {
            if (new FilterBO().creationFilterOne(params))
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
     * 필터 정보 삭제
     * 
     * @param id
     *            회원 아이디
     * @return
     */
    @DELETE
    public Response doDelete(@QueryParam("id")
    String id, @QueryParam("targetId")
    String targetId)
    {
        logger.info("Filter DELETE params: {0}, {1}", new Object[] { id, targetId });
        
        try
        {
            if (new FilterBO().removeFilterOne(id, targetId))
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
