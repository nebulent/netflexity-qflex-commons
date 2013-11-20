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
package org.netflexity.api.util.validation;

/**
 * @author MAX
 *
 * Creates an instance of validation service.
 */
public final class BeanValidationServiceFactory {

    private static BeanValidationService instance = new BeanValidationServiceImpl();

    private BeanValidationServiceFactory() {
    }

    /**
     * @return singleton instance of validation service.
     */
    public static BeanValidationService createValidationService(){
        return instance;
    }
}
