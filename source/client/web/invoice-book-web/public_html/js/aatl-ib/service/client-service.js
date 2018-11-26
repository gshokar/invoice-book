/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-26  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.ClientService = {

    get: function(number, callback){
        
        if(number === undefined || number === null){
            
            callback({
                number: "",
                name: "",
                address: {
                    address1: "",
                    address2: "",
                    city: "",
                    province: $aatl_ib.LookupService.defaultProvince.code,
                    postalCode: ""
                },
                contact: {
                    phone: "",
                    email: ""
                }
            }); 
        }else{
            
        }
    },
    
    save: function(client, callback){
        
        client.number = "12345678";
        
        callback(client);
    }
};


