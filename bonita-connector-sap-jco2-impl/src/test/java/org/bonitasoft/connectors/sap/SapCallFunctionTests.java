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
