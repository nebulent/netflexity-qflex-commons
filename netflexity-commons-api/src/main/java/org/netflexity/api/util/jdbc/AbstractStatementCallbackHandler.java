/*
 *  2006 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.util.jdbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author MAX
 *
 */
public abstract class AbstractStatementCallbackHandler implements StatementCallbackHandler {

    protected List parameters = new ArrayList(5);
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementCallbackHandler#getParameters()
     */
    public List getParameters() {
        return Collections.unmodifiableList(parameters);
    }
}
