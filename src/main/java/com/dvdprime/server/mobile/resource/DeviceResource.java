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

import com.dvdprime.server.mobile.bo.DeviceBO;
import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.model.DeviceDTO;
import com.dvdprime.server.mobile.request.DeviceRequest;

/**
 * 디바이스 정보 등록 삭제
 * 
 * @author 작은광명
 * 
 */
@Path("/device")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceResource
{
    /** Logger */
    private final Logger logger = LoggerFactory.getLogger(DeviceResource.class);
    
    @Context
    HttpServletRequest request;
    
    @GET
    public Response doGet()
    {
        return Response.ok(ResponseMessage.NOT_FOUND).build();
    }
    
    /**
     * 디바이스 정보 등록
     * 
     * @param id
     *            회원 아이디
     * @param token
     *            디바이스 토큰
     * @param version
     *            디바이스 버전
     * @return
     */
    @POST
    public Response doPost(@FormParam("id")
    String id, @FormParam("token")
    String token, @FormParam("version")
    String version)
    {
        DeviceRequest param = new DeviceRequest(id, token, version);
        logger.info("Device POST params: {}", param);
        
        try
        {
            if (new DeviceBO().creationDeviceOne(param))
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
     * 디바이스 정보 삭제
     * 
     * @param id
     *            회원 아이디
     * @param token
     *            디바이스 토큰
     * @return
     */
    @DELETE
    public Response doDelete(@QueryParam("id")
    String id, @QueryParam("token")
    String token)
    {
        logger.info("Device DELETE params: {}, {}", new Object[] { id, token });
        
        try
        {
            if (new DeviceBO().removeDeviceOne(new DeviceDTO(id, token)))
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
