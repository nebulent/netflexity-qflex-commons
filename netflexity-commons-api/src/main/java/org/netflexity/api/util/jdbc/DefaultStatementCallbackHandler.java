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
package org.netflexity.api.util.jdbc;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

/**
 * @author FEDORMAX
 *
 * DefaultStatementCallbackHandler
 */
public class DefaultStatementCallbackHandler implements StatementCallbackHandler {

    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementCallbackHandler#processStatement(java.sql.Statement)
     */
    public void processStatement(Statement st) throws SQLException {
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementCallbackHandler#getParameters()
     */
    public List getParameters() {
        return Collections.EMPTY_LIST;
    }
}
