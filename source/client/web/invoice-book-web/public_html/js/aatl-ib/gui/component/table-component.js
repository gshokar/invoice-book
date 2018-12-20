/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-15  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.TableComponent = (function () {
    function TableComponent(componentId, parentComponent, componentName) {
     
        let component = new $aatl_ib.gui.Component(componentId, parentComponent, componentName);     
        let onRowDoubleClicked = null;
        
        function getTableControl(){
            
            return component.getControl().find("table");
        }
        
        function getTableHeader(){
            return getTableControl().find("thead");
        }
        
        function getTableBody(){
            return getTableControl().find("tbody");
        }
        
        function getRow(rowData){
            
            let row = '<tr data-key-value="' + rowData.keyValue + '">';
            
            $.each(rowData.columnValues, function(index, value){
               row = row + "<td>" + value + "</td>";
            });
            
            row = row + "</tr>";
            
            return row;
        }
        
        this.addRow = function(values){
            getTableBody().append(getRow(values));
        };
        
        this.addRows = function(data){
            
            let rows = "";
            
            $.each(data, function(index, row){
                rows = rows + getRow(row);
            });
           
            let tableBody = getTableBody();
            
            tableBody.append(rows);
            
           tableBody.find('tr').dblclick(function(event){
            
                let element = $(event.currentTarget);
                
               if( typeof onRowDoubleClicked === 'function'){
                   let value = '' + element.data("keyValue");
                    onRowDoubleClicked(value);
               }
           });
        };
        
        this.clearRows = function(){
          getTableBody().empty();
            
        };
        
        this.setOnRowDoubleClicked = function(action){
          
            onRowDoubleClicked = action;
        };
    }
    
    return TableComponent;
}());
