/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Jan-25  GShokar         Created
 * =============================================================================
 */


$aatl_ib.utils = {

    createUniqueId: function () {

        function getRandomInt(min, max) {
            return Math.floor(Math.random() * (max - min + 1)) + min;
        }

        function getRandomChar() {
            return String.fromCharCode(getRandomInt(65, 90));
        }
        let id = new Date().getTime().toString();
        let pos;

        for (var i = 0; i < 6; i++) {
            pos = getRandomInt(1, 12);
            id = id.substr(0, pos) + getRandomChar() + id.substr(pos);
        }

        return id;
    },

    currentDate: function () {
        var today = new Date();

        return new Date(today.getFullYear(), today.getMonth(), today.getDate());
    }
};