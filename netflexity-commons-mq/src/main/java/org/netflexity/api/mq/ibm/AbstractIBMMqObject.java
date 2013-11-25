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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.netflexity.api.mq.ibm.enums.AbstractMqAttributeEnum;

public abstract class AbstractIBMMqObject implements IBMMqObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7618850544516822354L;
	
	protected Map<AbstractMqAttributeEnum, Object> attributes = new HashMap<AbstractMqAttributeEnum, Object>(50);

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.PcfMqObject#addAttribute(org.netflexity.api.mq.enums.AbstractMqAttributeEnum, java.lang.Object)
     */
    @Override
    public void addAttribute(AbstractMqAttributeEnum key, Object value) {
	attributes.put(key, value);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.PcfMqObject#getAttributes()
     */
    @Override
    public Map<AbstractMqAttributeEnum, Object> getAttributes() {
	return Collections.unmodifiableMap(attributes);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.PcfMqObject#getAttribute(org.netflexity.api.mq.enums.AbstractMqAttributeEnum)
     */
    @Override
    public Object getAttribute(AbstractMqAttributeEnum key) {
	return attributes.get(key);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (obj == this) {
	    return true;
	}
	if (obj == null || !obj.getClass().equals(getClass())) {
	    return false;
	}
	return (hashCode() == obj.hashCode());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return toString().hashCode();
    }

    /**
     * @param key
     * @return
     */
    protected String getAttributeAsString(AbstractMqAttributeEnum key) {
	return (String) attributes.get(key);
    }

    /**
     * @param key
     * @return
     */
    protected Integer getAttributeAsInteger(AbstractMqAttributeEnum key) {
	return (Integer) attributes.get(key);
    }

    /**
     * @param key
     * @return
     */
    protected Long getAttributeAsLong(AbstractMqAttributeEnum key) {
	return (Long) attributes.get(key);
    }
}
