/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.webservices.rest.web.v1_0.controller.openmrs2_0;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.v1_0.controller.openmrs1_9.SessionController1_9;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

/**
 * Tests functionality of {@link SessionController1_9} in OpenMRS 2.0
 */
public class SessionController1_9Test extends BaseModuleWebContextSensitiveTest {

    /**
     * @see SessionController1_9#get()
     * @verifies return the session with current provider if the user doesn't have Get Providers privilege
     */
    @Test
    public void get_shouldReturnCurrentProviderIfTheUserDoesNotHaveGetProvidersPrivilege() throws Exception {
        executeDataSet("sessionControllerTestDataset.xml");

        // authenticate new user without privileges
        Context.logout();
        Context.authenticate("test_user", "test");
        Assert.assertTrue(Context.isAuthenticated());

        SessionController1_9 controller = Context.getRegisteredComponents(SessionController1_9.class).get(0);

        Object ret = controller.get();
        Object currentProvider = PropertyUtils.getProperty(ret, "currentProvider");
        Assert.assertNotNull(currentProvider);
        Assert.assertTrue(currentProvider.toString().contains("Test Provider"));
    }
}
