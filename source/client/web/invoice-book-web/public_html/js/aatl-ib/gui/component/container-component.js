/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Jan-23  GShokar         Created
 * =============================================================================
 */


$aatl_ib.gui.ContainerComponent = (function () {

    function ContainerComponent(controlId) {

        let componentId = "#" + controlId;

        let currentViewControlId = undefined;

        this.getComponent = function () {
            return $(componentId);
        };

        this.addView = function (id) {

            this.getComponent().append('<div id="' + id + '" hidden></div>');
        };

        this.getView = function (id) {

            return this.getComponent().find(("#" + id));
        };

        this.showView = function (id) {

            if (currentViewControlId !== id) {
                //hideView(currentViewControlId);
                if (currentViewControlId) {
                    this.getView(currentViewControlId).prop("hidden", true);
                }
                this.getView(id).prop("hidden", false);
                currentViewControlId = id;
            }
        };

        this.hideView = function (id) {

            this.getView(id).prop("hidden", true);
        };

        this.removeView = function (id) {

            this.getView(id).remove();
        };
    }

    return ContainerComponent;
}());