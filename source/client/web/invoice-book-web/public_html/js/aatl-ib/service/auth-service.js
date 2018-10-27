/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Oct-27  GShokar         Created
 * =============================================================================
 */


"use strict";

$aatl_ib.AuthService = {
    
    user: null,
    
    isLoggedIn : function(){
                    return user !== undefined && user !== null;
                },
                
    login: function(loginId, password, callback){
        
                if(!$aatl_ib.utils.isFunction(callback)){
                    console.log("$aatl_ib.service.AuthService.login(callback) - callback is not function");
                    return;
                }
                
                let postCall = {
                                 url: "http://localhost:8080/invoicebookservice/service/authenticate",
                                 method: "POST",
                                 crossDomain: true,
                                 contentType: "application/json; charset=utf-8", 
                                 dataType: "json",
                                 data: JSON.stringify({ loginId: loginId, password: password })
                                };
                
                let request = $.ajax(postCall);
                        
                request.done(function(res){
                            callback();
                        });
                
                request.fail(function(jqXHR, textStatus){
                            callback("Login request failed: " + textStatus);
                        });
                
           }
};