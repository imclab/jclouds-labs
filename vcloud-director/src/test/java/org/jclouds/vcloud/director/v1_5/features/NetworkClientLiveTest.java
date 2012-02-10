/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 *(Link.builder().regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless(Link.builder().required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.vcloud.director.v1_5.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.net.URI;

import org.jclouds.vcloud.director.v1_5.VCloudDirectorException;
import org.jclouds.vcloud.director.v1_5.domain.Error;
import org.jclouds.vcloud.director.v1_5.domain.Link;
import org.jclouds.vcloud.director.v1_5.domain.Metadata;
import org.jclouds.vcloud.director.v1_5.domain.Reference;
import org.jclouds.vcloud.director.v1_5.internal.BaseVCloudDirectorClientLiveTest;
import org.testng.annotations.Test;

/**
 * Tests behavior of {@code NetworkClient}
 * 
 * @author danikov
 */
@Test(groups = { "live", "apitests" }, testName = "NetworkClientLiveTest")
public class NetworkClientLiveTest extends BaseVCloudDirectorClientLiveTest {

   @Test(testName = "GET /network/{id}")
   public void testWhenResponseIs2xxLoginReturnsValidNetwork() {
      Reference networkRef = Reference.builder()
            .href(URI.create(endpoint + "/network/55a677cf-ab3f-48ae-b880-fab90421980c")).build();
      
      assertEquals(context.getApi().getNetworkClient().getNetwork(networkRef), 
            NetworkClientExpectTest.orgNetwork());
   }
   
   @Test(testName = "GET /network/NOTAUUID")
   public void testWhenResponseIs400ForInvalidNetworkId() {
      Reference networkRef = Reference.builder()
            .href(URI.create(endpoint + "/network/NOTAUUID")).build();
      
      Error expected = Error.builder()
            .message("validation error : EntityRef has incorrect type, expected type is com.vmware.vcloud.entity.network.")
            .majorErrorCode(400)
            .minorErrorCode("BAD_REQUEST")
            .build();
      
      try {
         context.getApi().getNetworkClient().getNetwork(networkRef);
         fail("Should give HTTP 400 error");
      } catch (VCloudDirectorException vde) {
         assertEquals(vde.getError(), expected);
      } catch (Exception e) {
         fail("Should have thrown a VCloudDirectorException");
      }
   }
   
   @Test(testName = "GET /network/{catalog_id}")
   public void testWhenResponseIs403ForCatalogIdUsedAsNetworkId() {
      Reference networkRef = Reference.builder()
            .href(URI.create(endpoint + "/network/9e08c2f6-077a-42ce-bece-d5332e2ebb5c")).build();

      Error expected = Error.builder()
            .message("This operation is denied.")
            .majorErrorCode(403)
            .minorErrorCode("ACCESS_TO_RESOURCE_IS_FORBIDDEN")
            .build();

      try {
         context.getApi().getNetworkClient().getNetwork(networkRef);
         fail("Should give HTTP 403 error");
      } catch (VCloudDirectorException vde) {
         assertEquals(vde.getError(), expected);
      } catch (Exception e) {
         fail("Should have thrown a VCloudDirectorException");
      }
   }

   @Test(testName = "GET /network/{fake_id}")
   public void testWhenResponseIs403ForFakeNetworkId() {
      Reference networkRef = Reference.builder()
            .href(URI.create(endpoint + "/network/aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")).build();
      
      Error expected = Error.builder()
            .message("This operation is denied.")
            .majorErrorCode(403)
            .minorErrorCode("ACCESS_TO_RESOURCE_IS_FORBIDDEN")
            .build();

      try {
         context.getApi().getNetworkClient().getNetwork(networkRef);
         fail("Should give HTTP 403 error");
      } catch (VCloudDirectorException vde) {
         assertEquals(vde.getError(), expected);
      } catch (Exception e) {
         fail("Should have thrown a VCloudDirectorException");
      }
   }
   
   @Test(testName = "GET /network/{id}/metadata")
   public void testWhenResponseIs2xxLoginReturnsValidMetadataList() {
      Reference networkRef = Reference.builder()
            .href(URI.create(endpoint + "/network/55a677cf-ab3f-48ae-b880-fab90421980c")).build();
      
      Metadata expected = Metadata.builder()
            .type("application/vnd.vmware.vcloud.metadata+xml")
            .href(URI.create("https://vcloudbeta.bluelock.com/api/network/55a677cf-ab3f-48ae-b880-fab90421980c/metadata"))
            .link(Link.builder()
                  .rel("up")
                  .type("application/vnd.vmware.vcloud.network+xml")
                  .href(URI.create("https://vcloudbeta.bluelock.com/api/network/55a677cf-ab3f-48ae-b880-fab90421980c"))
                  .build())
            .build();
 
       assertEquals(context.getApi().getNetworkClient().getMetadata(networkRef), expected);
   }

}
