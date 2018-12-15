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
package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.SequenceDao;
import ca.aatl.app.invoicebook.data.jpa.entity.base.Sequence;
import ca.aatl.app.invoicebook.util.AppUtils;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author GShokar
 */
@Stateless
@LocalBean
public class SequenceService {

    @EJB
    SequenceDao seqenceDao;

    public synchronized String next(String name) {
        String number = null;

        Sequence seq = seqenceDao.find(name);

        if (seq != null) {
            number = next(seq);
        }

        return number;
    }

    public synchronized String next(Sequence seq) {
        String code = null;
        StringBuilder sb = new StringBuilder();
        int length = 0;

        if (seq != null) {

            if (!AppUtils.isNullOrEmpty(seq.getPreFix())) {
                sb.append(seq.getPreFix());
                length = sb.length();
            }
            if (!AppUtils.isNullOrEmpty(seq.getPostFix())) {
                length += seq.getPostFix().length();
            }
            code = String.valueOf(seq.NextNo());
            length += code.length();

            if (seq.getMaxLength() != null && seq.getMaxLength() > 0) {
                length = seq.getMaxLength() - length;

                for (int i = 0; i < length; i++) {
                    sb.append(seq.getFiller());
                }
            }
            sb.append(code);

            if (!AppUtils.isNullOrEmpty(seq.getPostFix())) {
                sb.append(seq.getPostFix());
            }

            code = sb.toString();

            seq.setLastNo(seq.NextNo());

        }
        return code;

    }
}
