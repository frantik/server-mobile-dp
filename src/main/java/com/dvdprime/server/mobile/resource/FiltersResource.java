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
import com.dvdprime.server.mobile.reponse.ListResponse;

/**
 * 필터 목록 조회
 * 
 * @author 작은광명
 * 
 */
@Path("/filters")
@Produces(MediaType.APPLICATION_JSON)
public class FiltersResource
{
    /** Logger */
    private final Logger logger = LoggerFactory.getLogger(FiltersResource.class);
    
    /**
     * 등록한 필터 목록
     * 
     * @param id
     *            회원 아이디
     * @return
     */
    @GET
    public Response Get(@QueryParam("id")
    String id)
    {
        logger.info("Filter GET params: {}", id);
        
        try
        {
            return Response.ok(new ListResponse(new FilterBO().searchFilterList(id))).build();
        }
        catch (Exception e)
        {
            return Response.ok(ResponseMessage.SERVER_ERROR).build();
        }
    }
    
    @POST
    public Response doPost()
    {
        return Response.ok(ResponseMessage.NOT_FOUND).build();
    }
    
    @PUT
    public Response doPut()
    {
        return Response.ok(ResponseMessage.NOT_FOUND).build();
    }
    
    @DELETE
    public Response doDelete()
    {
        return Response.ok(ResponseMessage.NOT_FOUND).build();
    }
    
}
