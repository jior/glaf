/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.report.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.report.domain.*;
import com.glaf.report.query.*;

@Component
public interface ReportMapper {

	void deleteReports(ReportQuery query);

	void deleteReportById(String id);

	Report getReportById(String id);

	int getReportCount(ReportQuery query);

	List<Report> getReports(ReportQuery query);

	void insertReport(Report model);

	void updateReport(Report model);

}