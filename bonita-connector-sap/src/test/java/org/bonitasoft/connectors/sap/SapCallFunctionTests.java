/*******************************************************************************
 * Copyright (C) 2013 BonitaSoft S.A.
 * BonitaSoft is a trademark of BonitaSoft SA.
 * This software file is BONITASOFT CONFIDENTIAL. Not For Distribution.
 * For commercial licensing information, contact:
 *      BonitaSoft, 32 rue Gustave Eiffel 38000 Grenoble
 *      or BonitaSoft US, 51 Federal Street, Suite 305, San Francisco, CA 94107
 *******************************************************************************/
package org.bonitasoft.connectors.sap;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = {
        BAPI_COMPANY_GETDETAIL.class,
        BAPI_COMPANY_GETLIST.class,
        BAPI_COMPANYCODE_GETLIST.class,
        BAPI_MATERIAL_GETLIST.class,
        BAPI_PO_GETITEMS.class,
        RFC_READ_TABLE_SFLIGHT.class,
        RFC_SYSTEM_INFO.class
})

public class SapCallFunctionTests {
}
