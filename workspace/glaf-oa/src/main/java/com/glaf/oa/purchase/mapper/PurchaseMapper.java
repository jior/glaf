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
package com.glaf.oa.purchase.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.oa.purchase.model.*;
import com.glaf.oa.purchase.query.*;

@Component
public interface PurchaseMapper {

	void deletePurchases(PurchaseQuery query);

	void deletePurchaseById(Long id);

	Purchase getPurchaseById(Long id);

	int getPurchaseCount(PurchaseQuery query);

	List<Purchase> getPurchases(PurchaseQuery query);

	void insertPurchase(Purchase model);

	void updatePurchase(Purchase model);

	void submitById(Long id);

	int getPurchaseApproveCount(PurchaseQuery query);

	List<Purchase> getPurchasesApprove(PurchaseQuery query);

}