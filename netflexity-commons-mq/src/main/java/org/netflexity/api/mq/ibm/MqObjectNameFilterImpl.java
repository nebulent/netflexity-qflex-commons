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
package org.netflexity.api.mq.ibm;


/**
 * @author MAX
 *
 */
public final class MqObjectNameFilterImpl implements MqObjectNameFilter {
    
    MqObjectNameFilterImpl(){};
    
    /* (non-Javadoc)
     * @see com.netflexity.qflex.mq.IObjectNameFilter#filter(java.lang.String, java.lang.String[])
     */
    @Override
    public boolean filter(String name, String filters[]) {
        int len = (filters != null ? filters.length : 0);
        for (int i = 0; i < len; i++){
            if (name.startsWith(filters[i])) {
                return true;
            }
        }
        return false;
    }
}
