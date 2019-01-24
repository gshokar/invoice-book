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
    function TableComponent(props) {
     
        let component = new $aatl_ib.gui.Component(props);     
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
        
        function getColumnElement(value){
            
            if(typeof value === 'boolean'){
                
                let icon = value === true ? "check-square" : "x-square";
                
                return '<td><span data-feather="' + icon +'"></span></td>';
            }
            
            return "<td>" + value + "</td>";
        }
        function getRow(rowData){
            
            let row = '<tr data-key-value="' + rowData.keyValue + '">';
            
            $.each(rowData.columnValues, function(index, value){
               row = row + getColumnElement(value);
            });
            
            row = row + "</tr>";
            
            return row;
        }
        
        function findRowControl(keyValue){
            return getTableBody().find("tr[data-key-value='" + keyValue + "']");
        }
        
        function tableRowDoubleClicked(evt){
            
            let element = $(evt.currentTarget);
                
               if( typeof onRowDoubleClicked === 'function'){
                   let value = '' + element.data("keyValue");
                    onRowDoubleClicked(value);
               }
        }
        
        this.addRow = function(value){
            getTableBody().append(getRow(value));
            
            findRowControl(value.keyValue).dblclick(tableRowDoubleClicked);
            
            $aatl_ib.gui.replaceIcons();
        };
        
        this.addRows = function(data){
            
            let rows = "";
            
            $.each(data, function(index, row){
                rows = rows + getRow(row);
            });
           
            let tableBody = getTableBody();
            
            tableBody.append(rows);
            
           tableBody.find('tr').dblclick(tableRowDoubleClicked);
           
           $aatl_ib.gui.replaceIcons();
        };
        
        this.clearRows = function(){
          getTableBody().empty();
            
        };
        
        this.setOnRowDoubleClicked = function(action){
          
            onRowDoubleClicked = action;
        };
        
        this.getRowControl = function(keyValue){
          return findRowControl(keyValue);
        };
        
        this.replaceRow = function(keyValue, rowData){
           
           let rowControl = findRowControl(keyValue);
           
           rowControl.replaceWith(getRow(rowData));
           
           rowControl = findRowControl(rowData.keyValue);
           
           rowControl.dblclick(tableRowDoubleClicked);
            
            $aatl_ib.gui.replaceIcons();
        };
    }
    
    return TableComponent;
}());
