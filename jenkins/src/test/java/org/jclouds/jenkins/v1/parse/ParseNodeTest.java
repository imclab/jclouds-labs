/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.jenkins.v1.parse;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.jclouds.jenkins.v1.domain.Job;
import org.jclouds.jenkins.v1.domain.Node;
import org.jclouds.json.BaseItemParserTest;
import org.testng.annotations.Test;

/**
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "ParseNodeTest")
public class ParseNodeTest extends BaseItemParserTest<Node> {

   @Override
   public String resource() {
      return "/master.json";
   }

   @Override
   @Consumes(MediaType.APPLICATION_JSON)
   public Node expected() {
      return Node.builder()
                  .name("")
                  .description("the master Jenkins node")
                  .jobs(Job.builder()
                           .name("ddd")
                           .url(URI.create("http://localhost:8080/job/ddd/"))
                           .color("grey").build())
                  .build();
   }
}
