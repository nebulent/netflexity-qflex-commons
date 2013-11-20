/*
 *  2005 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.orm.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.netflexity.api.orm.FieldMetadata;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.util.StringConstants;

/**
 * @author MAX
 *
 */
public final class RecordUtil {
    
    /**
     * @param rec1
     * @param rec2
     * @return
     */
    public static boolean equals(Object rec1, Object rec2) {
        if (rec1 == rec2){
            return true;
	}
        if (rec1 == null || !rec1.getClass().equals(rec2.getClass())){
            return false;
	}
        return rec2.toString().equals(rec1.toString());
    }

    /**
     * @param recordMetadata
     * @param record
     * @return
     */
    public static String toString(RecordMetadata recordMetadata, Object record) {
        StringBuilder buffer = new StringBuilder(128);
        buffer.append(recordMetadata.getName());
        buffer.append(StringConstants.PIPE);
        for (Iterator iter = recordMetadata.getFieldMetadata().iterator(); iter.hasNext();) {
            FieldMetadata fmd = (FieldMetadata)iter.next();
            Object value = fmd.getProperty(record);
            if(value != null){
                buffer.append(fmd.getName()).append(StringConstants.EQ).append(String.valueOf(value));
            }
            buffer.append(StringConstants.PIPE);
        }
        return buffer.toString();
    }

    /**
     * @param recordMetadata
     * @param record
     * @return
     */
    public static int hashCode(RecordMetadata recordMetadata, Object record) {
        StringBuilder buffer = new StringBuilder(32);
        buffer.append(recordMetadata.getName());
        for (Iterator iter = recordMetadata.getPrimaryKeyMetadata().iterator(); iter.hasNext();) {
            FieldMetadata fmd = (FieldMetadata)iter.next();
            Object value = fmd.getProperty(record);
            if(value != null){
                buffer.append(String.valueOf(value));
            }
            buffer.append(StringConstants.PIPE);
        }
        return (buffer.toString()).hashCode();
    }

    /**
     * @param records
     * @param columnName
     * @return
     */
    public static Map createRecordMap(RecordMetadata recordMetadata, List records, String columnName){
        if(records == null || records.isEmpty()){
            return Collections.EMPTY_MAP;
        }
        Map recMap = new HashMap(records.size());
        FieldMetadata fieldMetadata = recordMetadata.getFieldMetadataByName(columnName);
        for (Iterator iter = records.iterator(); iter.hasNext();) {
            Object rec = (Object) iter.next();
            recMap.put(fieldMetadata.getProperty(rec), rec);
        }
        return recMap;
    }
}
