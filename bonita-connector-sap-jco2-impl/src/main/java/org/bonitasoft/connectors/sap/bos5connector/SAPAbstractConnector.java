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

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;

/**
 * @author Charles Souillard
 */
public abstract class SAPAbstractConnector extends SAPUseConnectionAbstractConnector {

  public static final String SERVER_TYPE_APPLICATION_SERVER = "ApplicationServer";
  public static final String SERVER_TYPE_MESSAGE_SERVER = "MessageServer";

  //Connection
  //In Application Server mode, the user may define a systemNumber
  //In Message Server mode, the user must define a systemId AND a groupName
  protected String serverType;
  protected String client;
  protected String user;
  protected String password;
  protected String language = "EN";
  protected String host;
  protected String systemNumber;//if Application Server
  protected String systemId;//if Message Server
  protected String groupName;//if Message Server

  protected boolean useExitingConnection;

  @Override
  protected List<String> validateValues() {
    final List<String> errors = new ArrayList<String>();

    if (useExitingConnection) {
      errors.addAll(super.validateValues());
    } else {
      if (isNull(serverType)) {
        errors.add("Server type MUST BE DEFINED. " + "It MUST BE one of: " + SERVER_TYPE_APPLICATION_SERVER + ", " + SERVER_TYPE_MESSAGE_SERVER);
      }
      if (isNull(client)) {
        errors.add("Client MUST BE DEFINED. ");
      }
      if (isNull(user)) {
        errors.add("User MUST BE DEFINED. ");
      }
      if (isNull(password)) {
        errors.add("Password MUST BE DEFINED. ");
      }
      if (isNull(language)) {
        errors.add("Language MUST BE DEFINED. ");
      }
      if (isNull(host)) {
        errors.add("Host MUST BE DEFINED. ");
      }
      if (SERVER_TYPE_APPLICATION_SERVER.equals(serverType) && isNull(systemNumber)) {
        errors.add("If server type is " + SERVER_TYPE_APPLICATION_SERVER + ", systemNumber MUST BE DEFINED");
      }

      if (SERVER_TYPE_MESSAGE_SERVER.equals(serverType) && isNull(systemId)) {
        errors.add("If server type is " + SERVER_TYPE_MESSAGE_SERVER + ", systemId MUST BE DEFINED");
      }
      if (SERVER_TYPE_MESSAGE_SERVER.equals(serverType) && isNull(groupName)) {
        errors.add("If server type is " + SERVER_TYPE_MESSAGE_SERVER + ", groupName MUST BE DEFINED");
      }
    }
    return errors;
  }

  protected Client getJcoClient() {
    Client jcoClient = null;
    if (useExitingConnection) {
      jcoClient = super.getJcoClient();
    } else {
      //create a new client only if no one is in the input parameters
      if (SERVER_TYPE_APPLICATION_SERVER.equals(serverType)) {
        //Create a client connection to a dedicated R/3 system
        jcoClient = JCO.createClient(client, user, password, language, host, systemNumber);  
      } else if (isNotNull(systemId)) {
        //Create a client connection to Message Server 
        jcoClient = JCO.createClient(client, user, password, language, host, systemId, groupName);
      }
      if (jcoClient == null) {
        throw new RuntimeException("Unable to create a JCO client: systemNumber or systemId or groupName may be null");
      }
      jcoClient.connect();
    }
    return jcoClient;
  }

  protected void releaseClient(final Client jcoClient) {
    if (releaseClient) {
      if (useExitingConnection) {
        super.releaseClient(jcoClient);
      } else {
        JCO.releaseClient(jcoClient);
      }
    }
  }

  /*
   * SETTERS
   */
  public void setHost(String host) {
    this.host = host;
  }
  public void setClient(String client) {
    this.client = client;
  }
  public void setSystemNumber(String systemNumber) {
    this.systemNumber = systemNumber;
  }
  public void setUser(String user) {
    this.user = user;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  public void setSystemId(String systemId) {
    this.systemId = systemId;
  }
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
  public void setServerType(String serverType) {
    this.serverType = serverType;
  }
  public void setUseExitingConnection(boolean useExitingConnection) {
    this.useExitingConnection = useExitingConnection;
  }

}
