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
package com.glaf.oa.reports.web.springmvc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.oa.assesscontent.model.AssesscontentAndScore;
import com.glaf.oa.assesscontent.service.AssesscontentService;
import com.glaf.oa.assessresult.model.Assessresult;
import com.glaf.oa.assessresult.service.AssessresultService;
import com.glaf.oa.assessscore.query.AssessscoreQuery;
import com.glaf.oa.assesssort.model.Assesssort;
import com.glaf.oa.assesssort.model.AssesssortType;
import com.glaf.oa.assesssort.query.AssesssortQuery;
import com.glaf.oa.assesssort.service.AssesssortService;
import com.glaf.oa.base.web.springmvc.BaseRMB;
import com.glaf.oa.borrow.model.Borrow;
import com.glaf.oa.borrow.model.Borrowadderss;
import com.glaf.oa.borrow.model.Borrowmoney;
import com.glaf.oa.borrow.service.BorrowService;
import com.glaf.oa.borrow.service.BorrowadderssService;
import com.glaf.oa.borrow.service.BorrowmoneyService;
import com.glaf.oa.budget.model.Budget;
import com.glaf.oa.budget.service.BudgetService;
import com.glaf.oa.contract.model.Contract;
import com.glaf.oa.contract.service.ContractService;
import com.glaf.oa.leave.model.Leave;
import com.glaf.oa.leave.service.LeaveService;
import com.glaf.oa.ltravel.model.Ltravel;
import com.glaf.oa.ltravel.service.LtravelService;
import com.glaf.oa.seal.model.Seal;
import com.glaf.oa.seal.service.SealService;
import com.glaf.oa.optional.model.Optional;
import com.glaf.oa.optional.query.OptionalQuery;
import com.glaf.oa.optional.service.OptionalService;
import com.glaf.oa.overtime.model.Overtime;
import com.glaf.oa.overtime.service.OvertimeService;
import com.glaf.oa.payment.model.Payment;
import com.glaf.oa.payment.service.PaymentService;
import com.glaf.oa.paymentplan.model.Paymentplan;
import com.glaf.oa.paymentplan.query.PaymentplanQuery;
import com.glaf.oa.paymentplan.service.PaymentplanService;
import com.glaf.oa.purchase.model.Purchase;
import com.glaf.oa.purchase.model.Purchaseitem;
import com.glaf.oa.purchase.service.PurchaseService;
import com.glaf.oa.purchase.service.PurchaseitemService;
import com.glaf.oa.reimbursement.model.Reimbursement;
import com.glaf.oa.reimbursement.model.Ritem;
import com.glaf.oa.reimbursement.service.ReimbursementService;
import com.glaf.oa.reimbursement.service.RitemService;
import com.glaf.oa.salescontract.model.Salescontract;
import com.glaf.oa.salescontract.service.SalescontractService;
import com.glaf.oa.stravel.model.Stravel;
import com.glaf.oa.stravel.service.StravelService;
import com.glaf.oa.traveladdress.model.Traveladdress;
import com.glaf.oa.travelfee.model.Travelfee;
import com.glaf.oa.travelpersonnel.model.Travelpersonnel;
import com.glaf.oa.withdrawal.model.Withdrawal;
import com.glaf.oa.withdrawal.service.WithdrawalService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.others.model.Attachment;
import com.glaf.base.modules.others.service.AttachmentService;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.jbpm.container.ProcessContainer;
import com.glaf.jbpm.model.ActivityInstance;
import com.glaf.report.config.ReportConfig;
import com.glaf.report.domain.Report;
import com.glaf.report.gen.ReportFactory;
import com.glaf.report.service.IReportService;

@Controller("/oa/reports")
@RequestMapping("/oa/reports")
public class OAReportController {

	protected static final Log logger = LogFactory
			.getLog(OAReportController.class);

	private Map<String, Object> map = new HashMap<String, Object>();

	private List<String> dataList;

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 2、差旅费用汇总表
	 */
	@ResponseBody
	@RequestMapping("/exportReimbursement")
	public void exportReimbursement(HttpServletRequest request,
			HttpServletResponse response) {
		Long reimbursementid = RequestUtils.getLong(request, "reimbursementid");
		if (reimbursementid != null) {
			ReimbursementService reimbursementService = ContextFactory
					.getBean(ReimbursementService.class);
			Reimbursement reimbursement = reimbursementService
					.getReimbursement(reimbursementid);
			if (reimbursement != null) {
				RitemService ritemService = ContextFactory
						.getBean(RitemService.class);
				List<Ritem> ritemList = ritemService
						.getRitemByParentId(reimbursementid);
				double rmbsum = 0.0;
				String describe = "";
				double remain = 0.0;
				for (Ritem ri : ritemList) {
					ri.setUpdateBy(getConvert(ri.getFeetype() + "", "FYLX")); // 借用无效字段（费用类型）
					ri.setCurrency(getConvert(ri.getCurrency(), "money"));
					DecimalFormat df = new DecimalFormat("###############0.0");
					String strappsum = df.format(ri.getItemsum()
							* ri.getExrate());
					ri.setCreateBy(strappsum); // 借用无效字段(币种*汇率
					rmbsum += ri.getItemsum() * ri.getExrate();
				}
				if (rmbsum > reimbursement.getBudgetsum()) { // 支出>预算
					describe = "公司应付申请人";
					remain = rmbsum - reimbursement.getBudgetsum();
				} else {
					describe = "向申请人应收回";
					remain = reimbursement.getBudgetsum() - rmbsum;
				}
				map.clear();
				reimbursement.setAppuser(getConvert(reimbursement.getAppuser(),
						"SYS_USERS"));
				reimbursement.setArea(getConvert(reimbursement.getArea(),
						"eara"));

				reimbursement.setDept(getConvert(reimbursement.getDept(),
						"SYS_DEPTS"));

				reimbursement.setBrands1(getConvert(reimbursement.getBrands1(),
						"Brand"));
				reimbursement.setBrands2(getConvert(reimbursement.getBrands2(),
						"Brand"));
				reimbursement.setBrands3(getConvert(reimbursement.getBrands3(),
						"Brand"));

				reimbursement.setBrand(reimbursement.getBrands1() + "/"
						+ reimbursement.getBrands2() + "/"
						+ reimbursement.getBrands3());
				map.put("describe", describe);
				map.put("remain", remain);
				map.put("rmbsum", rmbsum);
				map.put("reimbursement", reimbursement);
				map.put("ritemList", ritemList);
				dataList = new ArrayList<String>();
				for (int i = 0; i < 9 - ritemList.size(); i++) {
					dataList.add("");
				}
				map.put("nullList", dataList);
				if (reimbursement.getProcessinstanceid() != null) {
					Long Processinstanceid = Long.valueOf(reimbursement
							.getProcessinstanceid());
					setActivityInstanceMap(Processinstanceid, map);
				}

				exportExcel("2reimbursement_apply", "reimbursement_apply"
						+ new Date().getTime() + "", map, response);
			}
		}
	}

	/**
	 * 3、合同申请
	 */
	@ResponseBody
	@RequestMapping("/exportContract")
	public void exportContract(HttpServletRequest request,
			HttpServletResponse response) {
		Long id = RequestUtils.getLong(request, "id");
		if (id != null) {
			ContractService contractService = ContextFactory
					.getBean(ContractService.class);
			Contract contract = contractService.getContract(id);
			if (contract != null) {
				map.clear();
				map.put("projrctName", contract.getProjrctname()); // 项目名称
				map.put("contactName", contract.getContactname()); // 合同名称
				map.put("companyName",
						getConvert(contract.getCompanyname(),
								contract.getArea())); // 我方签约单位
				map.put("suppliserName", contract.getSupplisername());// 对方签约单位
				map.put("contractSum", contract.getContractsum());// 总额
				map.put("currency", getConvert(contract.getCurrency(), "money"));
				map.put("appDate", format(contract.getAppdate()));
				if (contract.getPaytype() != null) {
					map.put("payType",
							getConvert(contract.getPaytype() + "", "paytype"));
				}
				map.put("attachment", getAttachment(id, 2)); // 附件
				if (contract.getProcessinstanceid() != null) {
					Long Processinstanceid = Long.valueOf(contract
							.getProcessinstanceid());
					setActivityInstanceMap(Processinstanceid, map);
				}

				exportExcel("3contract_apply",
						"contract_apply" + new Date().getTime() + "", map,
						response);
			}
		}
	}

	/**
	 * 4、印章申请
	 */
	@ResponseBody
	@RequestMapping("/exportSeal")
	public void exportSeal(HttpServletRequest request,
			HttpServletResponse response) {
		Long sealid = RequestUtils.getLong(request, "sealid");
		if (sealid != null) {
			SealService sealService = ContextFactory.getBean(SealService.class);
			Seal seal = sealService.getSeal(sealid);
			if (seal != null) {
				map.clear();
				map.put("appdate", format(seal.getAppdate()));
				if (seal.getProcessinstanceid() != null) {
					Long Processinstanceid = new Double(
							(seal.getProcessinstanceid())).longValue();
					setActivityInstanceMap(Processinstanceid, map);
				}
				seal.setAppuser(getConvert(seal.getAppuser(), "SYS_USERS"));
				seal.setAttachment(getAttachment(sealid, 4));
				seal.setCompany(getConvert(seal.getCompany(), seal.getArea()));
				String sealType = "";
				if (seal.getSealtype().trim().length() > 0) {
					String args[] = seal.getSealtype().split(",");
					for (int i = 0; i < args.length; i++) {
						sealType += "," + getConvert(args[i], "seal");
					}
					if (sealType.trim().length() > 0)
						sealType = sealType.substring(1);
				}
				seal.setSealtype(sealType);
				map.put("seal", seal);
				map.put("oaSeal", seal);

				exportExcel("4seal_apply", "seal_apply" + new Date().getTime()
						+ "", map, response);
			}
		}
	}

	/**
	 * 5、支出预算申请单
	 */
	@ResponseBody
	@RequestMapping("/exportBudget")
	public void exportBudget(HttpServletRequest request,
			HttpServletResponse response) {
		Long budgetid = RequestUtils.getLong(request, "budgetid");
		if (budgetid != null) {
			BudgetService budgetService = ContextFactory
					.getBean(BudgetService.class);
			Budget budget = budgetService.getBudget(budgetid);
			if (budget != null) {
				map.clear();
				PaymentplanService paymentplanService = ContextFactory
						.getBean(PaymentplanService.class);
				PaymentplanQuery query = new PaymentplanQuery();
				query.setBudgetid(budgetid);
				List<Paymentplan> payList = paymentplanService.list(query);
				if (payList.size() == 0) {
					payList.add(new Paymentplan());
				}
				for (Paymentplan pp : payList) {
					pp.setCreateBy(format(pp.getPaymentdate()));// 借用无效字段
				}
				map.put("payList", payList);
				budget.setCurrency(getConvert(budget.getCurrency(), "money"));
				map.put("area", getConvert(budget.getArea(), "eara"));
				map.put("paymentModel",
						getConvert(budget.getPaymentmodel() + "", "paytype"));
				map.put("paymentType",
						getConvert(budget.getPaymenttype() + "", "paymenttype"));

				map.put("appdate", format(budget.getAppdate()));
				if (budget.getProcessinstanceid() != null) {
					Long Processinstanceid = new Double(
							(budget.getProcessinstanceid())).longValue();
					setActivityInstanceMap(Processinstanceid, map);
				}
				budget.setAttachment(getAttachment(budgetid, 5));
				budget.setCompany(getConvert(budget.getCompany(),
						budget.getArea()));
				map.put("budget", budget);

				exportExcel("5budget_apply",
						"budget_apply" + new Date().getTime() + "", map,
						response);
			}
		}
	}

	/**
	 * 6、付款申请单
	 */
	@RequestMapping("/exportPayment")
	public void exportPayment(HttpServletRequest request,
			HttpServletResponse response) {
		Long paymentid = RequestUtils.getLong(request, "paymentid");
		if (paymentid != null) {
			PaymentService paymentService = ContextFactory
					.getBean(PaymentService.class);
			Payment payment = paymentService.getPayment(paymentid);
			if (payment != null) {
				map.clear();
				payment.setCompany(getConvert(payment.getCompany(),
						payment.getArea()));

				payment.setDept(getConvert(payment.getDept(), "SYS_DEPTS"));
				payment.setArea(getConvert(payment.getArea(), "eara"));
				map.put("appdate", format(payment.getAppdate()));
				map.put("maturitydate", format(payment.getMaturitydate()));
				if (payment.getProcessinstanceid() != null) {
					Long Processinstanceid = new Double(
							(payment.getProcessinstanceid())).longValue();
					setActivityInstanceMap(Processinstanceid, map);
				}
				map.put("payment", payment);

				exportExcel("6payment_apply",
						"payment_apply" + new Date().getTime() + "", map,
						response);
			}
		}
	}

	/**
	 * 7、员工借支单
	 */
	@RequestMapping("/exportBorrow")
	public void exportBorrow(HttpServletRequest request,
			HttpServletResponse response) {
		Long borrowid = RequestUtils.getLong(request, "borrowid");
		if (borrowid != null) {
			BorrowService borrowService = ContextFactory
					.getBean(BorrowService.class);
			Borrow borrow = borrowService.getBorrow(borrowid);
			if (borrow != null) {
				BorrowadderssService borrowadderssService = ContextFactory
						.getBean(BorrowadderssService.class);
				List<Borrowadderss> borrowadderssList = borrowadderssService
						.getBorrowadderssByParentId(borrowid);
				if (borrowadderssList.size() == 0)
					borrowadderssList = new ArrayList<Borrowadderss>();
				for (Borrowadderss bd : borrowadderssList) {
					bd.setUpdateBy(bd.getStart() + " 至：" + bd.getReach());
				}
				borrow.setDept(getConvert(borrow.getDept(), "SYS_DEPTS"));
				borrow.setAppuser(getConvert(borrow.getAppuser(), "SYS_USERS"));
				BorrowmoneyService borrowmoneyService = ContextFactory
						.getBean(BorrowmoneyService.class);
				List<Borrowmoney> borrowmoneyList = borrowmoneyService
						.getBorrowmoneyByParentId(borrowid);
				double rmbsum = 0.0;
				String sql = "";
				for (Borrowmoney bm : borrowmoneyList) {
					bm.setUpdateBy(getConvert(bm.getFeename() + "", "freeName")
							+ ":" + bm.getFeesum());
					sql += bm.getUpdateBy() + "     ";
					rmbsum += bm.getFeesum();
				}
				DecimalFormat df = new DecimalFormat("###############0.0");
				String strrmbsum = df.format(rmbsum);

				map.clear();
				if (borrow.getProcessinstanceid() != null) {
					Long Processinstanceid = new Double(
							(borrow.getProcessinstanceid())).longValue();
					setActivityInstanceMap(Processinstanceid, map);
				}
				borrow.setCompany(getConvert(borrow.getCompany(),
						borrow.getArea()));
				borrow.setArea(getConvert(borrow.getArea(), "eara"));
				borrow.setContent(getConvert(borrow.getContent(), "content"));
				map.put("sql", sql);
				map.put("appdate", format(borrow.getAppdate()));
				map.put("startDate", format(borrow.getStartdate()));
				map.put("endDate", format(borrow.getEnddate()));
				map.put("rmbsum", strrmbsum);
				map.put("upSum", BaseRMB.upperRMB(rmbsum));
				map.put("borrow", borrow);
				map.put("borrowmoneyList", borrowmoneyList);
				map.put("borrowadderssList", borrowadderssList);

				exportExcel("7borrow_apply",
						"borrow_apply" + new Date().getTime() + "", map,
						response);
			}
		}
	}

	/**
	 * 8、公出&出差申请表
	 */
	@ResponseBody
	@RequestMapping("/exportLTravel")
	public void exportLTravel(HttpServletRequest request,
			HttpServletResponse response) {
		Long travelid = RequestUtils.getLong(request, "travelid");
		if (travelid != null) {
			LtravelService ltravelService = ContextFactory
					.getBean(LtravelService.class);
			Ltravel lTravel = ltravelService.getLtravel(travelid);
			if (lTravel != null) {
				map.clear();
				map.put("appdate", format(lTravel.getAppdate()));
				setActivityInstanceMap(lTravel.getProcessinstanceid(), map);
				lTravel.setDept(getConvert(lTravel.getDept(), "SYS_DEPTS"));
				lTravel.setAppuser(getConvert(lTravel.getAppuser(), "SYS_USERS"));
				lTravel.setCreateBy(format(lTravel.getStartdate()));
				lTravel.setUpdateBy(format(lTravel.getEnddate()));
				lTravel.setAttachment(getAttachment(travelid, 8));
				map.put("lTravel", lTravel);

				exportExcel("8ltravel_apply",
						"ltravel_apply" + new Date().getTime() + "", map,
						response);
			}
		}
	}

	/**
	 * 9、员工出差申请单
	 */
	@ResponseBody
	@RequestMapping("/exportSTravel")
	public void exportSTravel(HttpServletRequest request,
			HttpServletResponse response) {
		Long travelid = RequestUtils.getLong(request, "travelid");
		if (travelid != null) {
			StravelService stravelService = ContextFactory
					.getBean(StravelService.class);
			Stravel stravel = stravelService.getStravel(travelid);
			if (stravel != null) {
				List<Traveladdress> addressList = stravelService
						.getTraveladdressList(travelid); // 行程

				List<Travelpersonnel> personnelList = stravelService
						.getTravelpersonnelList(travelid); // 人员

				List<Travelfee> feeList = stravelService
						.getTravelfeeList(travelid); // 费用

				map.clear();
				map.put("appdate", format(stravel.getAppdate()));
				stravel.setAppuser(getConvert(stravel.getAppuser(), "SYS_USERS"));
				stravel.setCreateBy(format(stravel.getStartdate()));
				stravel.setUpdateBy(format(stravel.getEnddate()));
				if (stravel.getProcessinstanceid() != null) {
					Long Processinstanceid = new Double(
							(stravel.getProcessinstanceid())).longValue();
					setActivityInstanceMap(Processinstanceid, map);
				}
				for (Travelpersonnel tr : personnelList) {
					tr.setDept(getConvert(tr.getDept(), "SYS_DEPTS"));
				}
				double total = 0.0;
				DecimalFormat df = new DecimalFormat("###############0.0");
				for (Travelfee tf : feeList) {
					total += tf.getFeesum();
				}
				String total_1 = df.format(total);
				map.put("total", total_1);
				map.put("stravel", stravel);
				map.put("feeList", feeList);
				map.put("addressList", addressList);
				map.put("personnelList", personnelList);

				exportExcel("9stravel_apply",
						"stravel_apply" + new Date().getTime() + "", map,
						response);
			}
		}
	}

	/**
	 * 10、采购申请单
	 */
	@ResponseBody
	@RequestMapping("/exportPurchase")
	public void exportPurchase(HttpServletRequest request,
			HttpServletResponse response) {
		Long purchaseId = RequestUtils.getLong(request, "purchaseId");
		if (purchaseId != null) {
			PurchaseService purchaseService = ContextFactory
					.getBean(PurchaseService.class);
			Purchase purchase = purchaseService.getPurchase(purchaseId);
			if (purchase != null) {
				map.clear();
				PurchaseitemService purchaseitemService = ContextFactory
						.getBean(PurchaseitemService.class);
				List<Purchaseitem> purchaseList = purchaseitemService
						.getPurchaseitemByParentId(purchaseId);
				purchase.setAppuser(getConvert(purchase.getAppuser(),
						"SYS_USERS"));
				purchase.setDept(getConvert(purchase.getDept(), "SYS_DEPTS"));
				purchase.setArea(getConvert(purchase.getArea(), "eara"));
				map.put("purchase", purchase);
				DecimalFormat df = new DecimalFormat("###############0.0");
				for (Purchaseitem pi : purchaseList) {
					String sum = df.format(pi.getQuantity()
							* pi.getReferenceprice());
					pi.setUpdateBy(sum); // 借用无效字段（明细总价）
				}
				map.put("purchaseList", purchaseList);
				dataList = new ArrayList<String>();
				for (int i = 0; i < 18 - purchaseList.size(); i++) {
					dataList.add("");
				}
				map.put("nullList", dataList);

				exportExcel("10purchase_apply",
						"purchase_apply" + new Date().getTime() + "", map,
						response);
			}
		}
	}

	/**
	 * 11、取现申请
	 */
	@ResponseBody
	@RequestMapping("/exportWithDrawal")
	public void exportWithDrawal(HttpServletRequest request,
			HttpServletResponse response) {
		Long withdrawalid = RequestUtils.getLong(request, "withdrawalid");
		if (withdrawalid != null) {
			WithdrawalService withdrawalService = ContextFactory
					.getBean(WithdrawalService.class);
			Withdrawal withdrawal = withdrawalService
					.getWithdrawal(withdrawalid);
			if (withdrawal != null) {
				map.clear();
				map.put("appdate", format(withdrawal.getAppdate()));
				setActivityInstanceMap(withdrawal.getProcessinstanceid(), map);
				map.put("appsum", BaseRMB.upperRMB(withdrawal.getAppsum()));
				DecimalFormat df = new DecimalFormat("###############0.0");
				String strappsum = df.format(withdrawal.getAppsum());
				map.put("strappsum", strappsum);
				withdrawal.setCompany(getConvert(withdrawal.getCompany(),
						withdrawal.getArea()));
				withdrawal
						.setDept(getConvert(withdrawal.getDept(), "SYS_DEPTS"));
				withdrawal.setAppuser(getConvert(withdrawal.getAppuser(),
						"SYS_USERS"));
				map.put("withdrawal", withdrawal);

				exportExcel("11withdrawal_apply", "withdrawal_apply"
						+ new Date().getTime() + "", map, response);
			}
		}
	}

	/**
	 * 12、加班申请单
	 */
	@ResponseBody
	@RequestMapping("/exportOverTime")
	public void exportOverTime(HttpServletRequest request,
			HttpServletResponse response) {
		Long id = RequestUtils.getLong(request, "id");
		if (id != null) {
			OvertimeService overtimeService = ContextFactory
					.getBean(OvertimeService.class);
			Overtime overTime = overtimeService.getOvertime(id);
			if (overTime != null) {
				map.clear();
				map.put("overTimeType",
						getConvert(overTime.getType() + "", "JBLX"));
				String area = getConvert(overTime.getArea(), "eara");
				map.put("area", area);
				map.put("appdate", format(overTime.getAppdate()));
				map.put("startdate", format(overTime.getStartdate()));
				map.put("enddate", format(overTime.getEnddate()));
				setActivityInstanceMap(overTime.getProcessinstanceid(), map); // 流程
				overTime.setCompany(getConvert(overTime.getCompany(),
						overTime.getArea()));
				map.put("overtime", overTime);

				exportExcel("12overtime_apply",
						"overtime_apply" + new Date().getTime() + "", map,
						response);
			}
		}
	}

	/**
	 * 13、请假申请单
	 */
	@ResponseBody
	@RequestMapping("/exportLeave")
	public void exportLeave(HttpServletRequest request,
			HttpServletResponse response) {
		Long leaveid = RequestUtils.getLong(request, "leaveid");
		if (leaveid != null) {
			LeaveService leaveService = ContextFactory
					.getBean(LeaveService.class);
			Leave leave = leaveService.getLeave(leaveid);
			if (leave != null) {
				map.clear();
				leave.setAppuser(getConvert(leave.getAppuser(), "SYS_USERS"));
				leave.setDept(getConvert(leave.getDept(), "SYS_DEPTS"));
				map.put("leaveType", getConvert(leave.getType() + "", "QJLX"));
				map.put("area", getConvert(leave.getArea(), "eara"));
				map.put("appdate", format(leave.getAppdate()));
				map.put("startdate", format(leave.getStartdate()));
				map.put("enddate", format(leave.getEnddate()));
				setActivityInstanceMap(leave.getProcessinstanceid(), map); // 流程
				leave.setCompany(getConvert(leave.getCompany(), leave.getArea()));
				map.put("leave", leave);

				exportExcel("13leave_apply",
						"leave_apply" + new Date().getTime() + "", map,
						response);
			}
		}
	}

	/**
	 * 14、售车合同申请表
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/exportSalesContract")
	public void exportSalesContract(HttpServletRequest request,
			HttpServletResponse response) {
		Long id = RequestUtils.getLong(request, "id");
		int parentId = RequestUtils.getInt(request, "id");
		if (id != null) {
			SalescontractService salescontractService = ContextFactory
					.getBean(SalescontractService.class);
			Salescontract salescontract = salescontractService
					.getSalescontract(id);
			map.clear();
			if (salescontract != null) {

				map.put("projrctName", salescontract.getProjrctname()); // 项目名称
				map.put("contactName", salescontract.getContactname()); // 合同名称
				map.put("companyName",
						getConvert(salescontract.getCompanyname(),
								salescontract.getArea()));// 我方签约单位
				map.put("suppliserName", salescontract.getSupplisername());// 对方签约单位
				map.put("contractSum", salescontract.getContractsum());// 总额

				map.put("contractNo", salescontract.getContractno());// 合同编号

				map.put("optionalSum", salescontract.getOptionalsum());// 选配总价

				map.put("firstPay", salescontract.getFirstpay());// 首付款

				map.put("lastPay", salescontract.getLastpay());// 尾款
				map.put("discount", salescontract.getDiscount());// 差价

				map.put("currency",
						getConvert(salescontract.getCurrency(), "money"));// 币种
				map.put("deliveryDate", format(salescontract.getDeliverydate()));// 预计交车时间
				map.put("Sales", salescontract.getSales());// 销售顾问
				map.put("contractSales", salescontract.getContractsales());// 合同留存销售顾问
				map.put("giftSum", salescontract.getGiftsum());// 赠送价值
				map.put("giftRemark", salescontract.getGiftremark());// 赠送明细
				map.put("remark", salescontract.getRemark());// 备注条款
				map.put("attachment", getAttachment(id, 3));// 附件

				if (salescontract.getProcessinstanceid() != null) {
					Long Processinstanceid = new Double(
							(salescontract.getProcessinstanceid())).longValue();
					setActivityInstanceMap(Processinstanceid, map);
				}
				OptionalService optionalService = ContextFactory
						.getBean(OptionalService.class);
				OptionalQuery query = new OptionalQuery();
				query.setId(parentId);
				List<Optional> optionList = optionalService.list(query);
				map.put("optionList", optionList);

				exportExcel("14salesContract_apply", "salesContract_apply"
						+ new Date().getTime() + "", map, response);
			}
		}
	}

	/**
	 * 15、职位考核表
	 */
	@ResponseBody
	@RequestMapping("/exportAssessQuestion")
	public void exportAssessQuestion(HttpServletRequest request,
			HttpServletResponse response) {
		Long resultid = RequestUtils.getLong(request, "resultid");
		if (resultid != null) {
			// 考核结果
			AssessresultService assessresultService = ContextFactory
					.getBean(AssessresultService.class);
			Assessresult assessresult = assessresultService
					.getAssessresult(resultid);

			// // 考核问卷
			// AssessquestionService assessquestionService = ContextFactory
			// .getBean(AssessquestionService.class);
			// Assessquestion assessquestion = assessquestionService
			// .getAssessquestion(assessresult.getQustionid());

			// 指标考核内容
			AssesscontentService assesscontentService = ContextFactory
					.getBean(AssesscontentService.class);
			List<AssesscontentAndScore> contentAndScoreList;
			// 指标考核分类
			AssesssortService assesssortService = ContextFactory
					.getBean(AssesssortService.class);
			AssesssortQuery query = new AssesssortQuery();
			query.setQustionid(assessresult.getQustionid());
			List<Assesssort> list = assesssortService.list(query);
			List<AssesssortType> ty = assesssortService
					.getAssesssortsType("ASSESS_CLASS");
			List<AssesssortType> ty1 = new ArrayList<AssesssortType>();
			List<AssesssortType> sortList;
			AssessscoreQuery scoreQuery;
			int fat = 0;
			int fat1 = 0;
			int scores = 0;
			int scores1 = 0;
			boolean rst = false;
			for (AssesssortType a1 : ty) {
				fat = 0;
				scores = 0;
				rst = false;
				sortList = new ArrayList<AssesssortType>();
				for (AssesssortType a2 : a1.getSubAssessList()) {
					for (Assesssort as : list) {
						if (as.getSortid() == a2.getId()) {
							rst = true;
							scoreQuery = new AssessscoreQuery();
							scoreQuery.setResultid(resultid);
							scoreQuery.setSortid(as.getAssesssortid());

							contentAndScoreList = assesscontentService
									.getAssesscontentAndScoreList(scoreQuery);
							for (AssesscontentAndScore aas : contentAndScoreList) {
								fat = (int) (fat + aas.getStandard());
								scores = (int) (scores + aas.getScore());
								fat1 = (int) (fat1 + aas.getStandard());
								scores1 = (int) (scores1 + aas.getScore());
							}

							a2.setAdsList(contentAndScoreList);
							sortList.add(a2);
						}
					}
				}
				if (rst) {
					a1.setSubAssessList(sortList);
					a1.setFat(fat + "");
					a1.setScores(scores + "");
					ty1.add(a1);
				}
			}
			map.clear();
			map.put("fat1", fat1);
			map.put("scores1", scores1);
			map.put("list3", ty1);
			map.put("assessresult", assessresult);

			Workbook workBook = saveExcelWorkbook(SystemProperties.getAppPath()
					+ "/WEB-INF/conf/templates/oa/15assessQuestion.xls", map);
			Sheet sheet = workBook.getSheetAt(0);
			convert(sheet, ty1);
			saveWorkbook(workBook, response,
					"assessQuestion" + new Date().getTime() + ".xls");
		}
	}

	/**
	 * 基础数据
	 * 
	 * @param type1
	 * @param type
	 * @return
	 */
	private String getConvert(String type1, String type) {
		return BaseDataManager.getInstance().getStringValue(type1, type);
	}

	private String format(Date date) {
		if (date != null) {
			return df.format(date);
		} else
			return "";
	}

	/**
	 * 获取流程
	 * 
	 * @param id
	 * @param map
	 */
	private void setActivityInstanceMap(Long id, Map<String, Object> map) {
		if (id != null) {
			List<ActivityInstance> list = ProcessContainer.getContainer()
					.getActivityInstances(id);
			for (ActivityInstance ai : list) {
				if (ai.getTaskName() != null) {
					if (ai.getTaskName().equalsIgnoreCase("task0")
							|| ai.getIsAgree() != null
							&& ai.getIsAgree().equalsIgnoreCase("true")) {
						ai.setObjectValue(format(ai.getDate())); // 借用无效字段format
						// date 类型
						map.put(ai.getTaskName(), ai);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param map
	 *            数据源
	 * @param fileName
	 *            文件名
	 * @param reportId
	 *            报表ID
	 * @param request
	 * @param response
	 */
	public void CommonExport(Map<String, Object> map, String fileName,
			String reportId, HttpServletRequest request,
			HttpServletResponse response) {
		IReportService reportService = ContextFactory.getBean("reportService");
		Report report = null;
		if (StringUtils.isNotEmpty(reportId)) {
			report = reportService.getReport(reportId);

			if (report != null) {
				try {
					byte[] bytes = ReportFactory
							.createReportStream(report, map);
					if (bytes != null) {
						String destFileName = ReportConfig
								.getReportDestFileName(report);
						FileUtils.save(destFileName, bytes);
						ResponseUtils.download(request, response, bytes,
								fileName);
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	private String getAttachment(long id, int type) {
		AttachmentService attachmentService = ContextFactory
				.getBean(AttachmentService.class);
		List<Attachment> aList = attachmentService.getAttachmentList(id, type);
		String attachment = "";
		for (Attachment att : aList) {
			attachment += "," + att.getName();
		}
		if (attachment.trim().length() > 0)
			attachment = attachment.substring(1);
		return attachment;
	}

	private void convert(Sheet sheet, List<AssesssortType> ty) {
		CellRangeAddress cellRangeAddress;
		int aIndex = 0;
		int first = 6;
		int last = 0;
		int Index = 0;
		int firstRow = 6;
		int lastRow = 0;
		boolean rst = false;
		for (AssesssortType b1 : ty) {
			rst = false;
			for (AssesssortType b2 : b1.getSubAssessList()) {
				int size = b2.getAdsList().size();
				if (size > 0) {
					aIndex = aIndex + size;
					Index = Index + size;
					lastRow = 6 + Index - 1;
					firstRow = lastRow - size + 1;
					cellRangeAddress = new CellRangeAddress(firstRow, lastRow,
							1, 1);
					sheet.addMergedRegion(cellRangeAddress);
					rst = true;
				}
			}
			if (rst) {
				Index = Index + 1;
				firstRow = lastRow + 2;
				last = 6 + aIndex - 1;
				cellRangeAddress = new CellRangeAddress(first, last, 0, 0);
				// 动态合同单元格
				sheet.addMergedRegion(cellRangeAddress);
				cellRangeAddress = new CellRangeAddress(first, last, 3, 3);
				// 动态合同单元格
				sheet.addMergedRegion(cellRangeAddress);
				aIndex = aIndex + 1;
				first = last + 2;
			}
		}

	}

	public static void exportExcel(String fileName, String targetName,
			Map<String, Object> datas, HttpServletResponse response) {
		fileName = SystemProperties.getAppPath()
				+ "/WEB-INF/conf/templates/oa/" + fileName + ".xls";
		XLSTransformer transformer = new XLSTransformer();
		FileInputStream fis = null;
		OutputStream os = null;
		Workbook workbook = null;
		try {
			fis = new FileInputStream(fileName);
			workbook = transformer.transformXLS(fis, datas);
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="
					+ targetName + ".xls");
			response.setContentType("application/msexcel");
			os = response.getOutputStream();
			if (os != null) {
				workbook.write(os);
			}
		} catch (FileNotFoundException e) {
			logger.error("exportExcel:" + e.getMessage());
		} catch (ParsePropertyException e) {
			logger.error("exportExcel:" + e.getMessage());
		} catch (InvalidFormatException e) {
			logger.error("exportExcel:" + e.getMessage());
		} catch (IOException e) {
			logger.error("exportExcel:" + e.getMessage());
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				logger.debug("exportExcel:" + e.getMessage());
			}
		}

	}

	private static Workbook saveExcelWorkbook(String srcPath,
			Map<String, Object> datas) {
		XLSTransformer transformer = new XLSTransformer();
		FileInputStream fis = null;
		Workbook workBook = null;
		try {
			fis = new FileInputStream(srcPath);
			workBook = transformer.transformXLS(fis, datas);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeStream(fis);
		}
		return workBook;
	}

	private void saveWorkbook(Workbook workBook, HttpServletResponse response,
			String targetName) {
		OutputStream os = null;
		try {
			response.reset(); // 非常重要
			response.setHeader("content-disposition", "attachment; filename="
					+ targetName);
			response.setContentType("application/msexcel");
			os = response.getOutputStream();
			if (os != null) {
				workBook.write(os);
			}

		} catch (FileNotFoundException e) {
			logger.error("function saveWorkbook error:" + e.getMessage());
		} catch (IOException e) {
			logger.error("function saveWorkbook error:" + e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				logger.error("function saveWorkbook error:" + e.getMessage());
			}

		}

	}
 
}