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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvdprime.server.mobile.bo.NotificationBO;
import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.request.NotificationRequest;

/**
 * 알림 정보 등록
 * 
 * @author 작은광명
 * 
 */
@Path("/notification")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationResource
{
    /** Logger */
    private final Logger logger = LoggerFactory.getLogger(NotificationResource.class);
    
    @GET
    public Response doGet()
    {
        return Response.ok(ResponseMessage.NOT_FOUND).build();
    }
    
    /**
     * 알림 정보 등록
     * 
     * @param formParameters
     * @return
     */
    @POST
    public Response doPost(final MultivaluedMap<String, String> formParameters)
    {
        NotificationRequest param = NotificationRequest.fromMultiValuedFormParameters(formParameters);
        logger.info("Notification POST params: {0}", param);
        
        try
        {
            if (new NotificationBO().creationNotificationOne(param))
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
    
    @DELETE
    public Response doDelete()
    {
        return Response.ok(ResponseMessage.NOT_FOUND).build();
    }
}
