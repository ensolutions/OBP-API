/**
  * Open Bank Project - API
  * Copyright (C) 2011-2018, TESOBE Ltd
  **
  *This program is free software: you can redistribute it and/or modify
  *it under the terms of the GNU Affero General Public License as published by
  *the Free Software Foundation, either version 3 of the License, or
  *(at your option) any later version.
  **
  *This program is distributed in the hope that it will be useful,
  *but WITHOUT ANY WARRANTY; without even the implied warranty of
  *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *GNU Affero General Public License for more details.
  **
  *You should have received a copy of the GNU Affero General Public License
  *along with this program.  If not, see <http://www.gnu.org/licenses/>.
  **
  *Email: contact@tesobe.com
  *TESOBE Ltd
  *Osloerstrasse 16/17
  *Berlin 13359, Germany
  **
  *This product includes software developed at
  *TESOBE (http://www.tesobe.com/)
  * by
  *Simon Redfern : simon AT tesobe DOT com
  *Stefan Bethge : stefan AT tesobe DOT com
  *Everett Sochowski : everett AT tesobe DOT com
  *Ayoub Benali: ayoub AT tesobe DOT com
  *
  */
package code.api.berlin.group.v1

import code.api.OBPRestHelper
import code.api.util.APIUtil.{OBPEndpoint, ResourceDoc, getAllowedEndpoints}
import code.api.util.ApiVersion
import code.util.Helper.MdcLoggable

import scala.collection.immutable.Nil



/*
This file defines which endpoints from all the versions are available in v1
 */


object OBP_BERLIN_GROUP_1 extends OBPRestHelper with APIMethods_BERLIN_GROUP_1 with MdcLoggable {

  val version = ApiVersion.berlinGroupV1
  val versionStatus = "DRAFT"

  val endpointsOf1 =  
    Implementations1.getAccountList ::
    Implementations1.getAccountBalances ::
    Implementations1.getTransactionList ::
    Nil
  
  val allResourceDocs = Implementations1.resourceDocs
  
  def findResourceDoc(pf: OBPEndpoint): Option[ResourceDoc] = {
    allResourceDocs.find(_.partialFunction==pf)
  }

  // Filter the possible endpoints by the disabled / enabled Props settings and add them together
  val routes : List[OBPEndpoint] = getAllowedEndpoints(endpointsOf1, Implementations1.resourceDocs)


  // Make them available for use!
  routes.foreach(route => {
    oauthServe(("berlin-group" / version.toString).oPrefix{route}, findResourceDoc(route))
  })

  logger.info(s"version $version has been run! There are ${routes.length} routes.")

}
