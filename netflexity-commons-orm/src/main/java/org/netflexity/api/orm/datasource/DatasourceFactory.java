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
package org.netflexity.api.orm.datasource;

import org.netflexity.api.orm.DatasourceMetadata;

public interface DatasourceFactory {

    /**
     * @param datasourceMetadata
     * @return Datasource
     */
    Datasource getDatasource(DatasourceMetadata datasourceMetadata);

}