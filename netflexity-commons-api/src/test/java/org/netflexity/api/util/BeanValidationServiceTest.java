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
package org.netflexity.api.util;

import java.util.List;

import junit.framework.TestCase;

import org.netflexity.api.util.validation.BeanValidationService;
import org.netflexity.api.util.validation.BeanValidationServiceFactory;

public class BeanValidationServiceTest extends TestCase {

    private BeanValidationService service;
    
    protected void setUp() throws Exception {
        service = BeanValidationServiceFactory.createValidationService();
        
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * BeanValidationService.validate()
     */
    public void testValidate(){
        // #1. No error scenario.
        SampleBean bean = new SampleBean();
        bean.setLastName("Tester");
        bean.setFirstName("John");
        bean.setStreet1("1 Test Street");
        bean.setCity("Testville");
        bean.setState("TE");
        bean.setPostalCode("12345");
        bean.setAge("1");
        
        List errors = service.validate(bean, null);
        assertTrue("Validation should not find any errors!", errors.isEmpty());
        
        // #2. Age "must be an integer" scenario.
        bean.setAge("ABC");
        errors = service.validate(bean, BeanValidationServiceTest.class.getClassLoader());
        assertFalse("Age is not an integer!", errors.isEmpty());
        
        System.out.println(errors);
        
        // #3. Last name "cannot be > 9 characters long "scenario.
        bean.setAge("23");
        bean.setLastName("Tester789XX");
        errors = service.validate(bean, BeanValidationServiceTest.class.getClassLoader());
        assertFalse("Age is not an integer!", errors.isEmpty());
    }
    
}
