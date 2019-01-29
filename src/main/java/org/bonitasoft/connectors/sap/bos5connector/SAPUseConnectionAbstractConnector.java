/**
 * Copyright (C) 2009 BonitaSoft S.A.
 * BonitaSoft, 31 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.connectors.sap.bos5connector;

import java.util.ArrayList;
import java.util.List;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Repository;
import org.bonitasoft.engine.connector.ConnectorException;

/**
 * @author Charles Souillard
 */
public abstract class SAPUseConnectionAbstractConnector {

  protected String existingConnectionName;

  protected String repository = SAPConstants.DEFAULT_REPOSITORY_NAME;
  protected boolean releaseClient = true;

  protected List<String> validateValues() {
    final List<String> errors = new ArrayList<String>();
    if (isNull(existingConnectionName)) {
      errors.add("Existing Connection Name MUST BE DEFINED xhen using an existing connection. ");
    }
    return errors;
  }

  protected boolean isNull(final String s) {
    //handles empty strings like null
    return s == null || "".equals(s);
  }

  protected boolean isNotNull(final String s) {
    //handles empty strings like null
    return s != null && !"".equals(s);
  }

  protected Client getJcoClient() {
    final Client jcoClient = SAPJcoClientRepository.getJcoClient(existingConnectionName);
    if (jcoClient == null) {
      throw new RuntimeException("Unable to get an exisitng JCO client with name: " + existingConnectionName);
    }
    return jcoClient;
  }

  protected void releaseClient(final Client jcoClient) {
    if (releaseClient) {
      try {
        JCO.releaseClient(jcoClient);
      } finally {
        SAPJcoClientRepository.removeJcoClient(existingConnectionName);
      }
    }
  }
  protected Repository createRepository(final Client jcoClient) {
    return new Repository(repository, jcoClient);
  }

  protected void executeFunction(final Client jcoClient, final Function jcoFunction) throws ConnectorException {
    if (jcoFunction == null) {
      throw new ConnectorException("Unable to execute a null function.");
    }
    if (jcoClient == null) {
      throw new ConnectorException("Unable to execute function: " + jcoFunction + ", with a null jcoClient");
    }
    if (!jcoClient.isAlive()) {
      throw new ConnectorException("Unable to execute function: " + jcoFunction + ", with an inactive jcoClient: " + jcoClient);
    }

    jcoClient.execute(jcoFunction);
  }
  protected Function createFunction(final Repository jcoRepository, final String functionName) throws ConnectorException {
    IFunctionTemplate ft = jcoRepository.getFunctionTemplate(functionName.toUpperCase());
    if (ft == null) {
      throw new ConnectorException(functionName + " not found in SAP.");
    }
    return ft.getFunction();
  }

  /*
   * SETTERS
   */
  public void setReleaseClient(boolean releaseClient) {
    this.releaseClient = releaseClient;
  }
  public void setRepository(String repository) {
    this.repository = repository;
  }
  public void setExistingConnectionName(String existingConnectionName) {
    this.existingConnectionName = existingConnectionName;
  }

}
