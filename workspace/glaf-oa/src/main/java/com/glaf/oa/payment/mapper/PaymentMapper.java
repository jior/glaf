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
package com.glaf.oa.payment.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.oa.payment.model.*;
import com.glaf.oa.payment.query.*;

@Component
public interface PaymentMapper {

	void deletePayments(PaymentQuery query);

	void deletePaymentById(Long id);

	Payment getPaymentById(Long id);

	int getPaymentCount(PaymentQuery query);

	List<Payment> getPayments(PaymentQuery query);

	void insertPayment(Payment model);

	void updatePayment(Payment model);

	int getReviewPaymentCount(PaymentQuery query);

	List<Payment> getReviewPayments(PaymentQuery query);

}