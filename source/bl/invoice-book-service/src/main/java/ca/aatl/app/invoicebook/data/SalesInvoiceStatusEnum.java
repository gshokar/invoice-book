/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Apr-06  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.data;

/**
 *
 * @author GShokar
 */
public enum SalesInvoiceStatusEnum {
    New,
    Sent,
    Paid,
    Closed,
    Modified,
    Resent,
    Canceled,
    PartiallyPaid,
    UnKnown
}
