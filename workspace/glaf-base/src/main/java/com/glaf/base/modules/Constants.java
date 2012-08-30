package com.glaf.base.modules;

/**
 * <p>
 * Title: Global.java
 * </p>
 * <p>
 * Description: 公用变量类
 * </p>
 */

public class Constants {
	// 用户session名称
	public static String ROOT_PATH = "";
	public static int PAGE_SIZE = 10;// 缺省页面大小

	// 上传文件参数
	public static String UPLOAD_DIR = "/upload/";
	public static long UPLOAD_MAX_SIZE = 2 * 1024 * 1024;// 20M
	public static long UPLOAD_FILE_SIZE_MAX = 2 * 1024 * 1024;// 20M

	// 各模块编码编号 特定采购
	final public static String SYS_BUD = "BUDGET"; // 预算
	final public static String SYS_IMP = "IMPORT"; // 重财
	final public static String SYS_PUR = "PURCHA"; // 采购
	final public static String SYS_QUE = "QUERY"; // 询价
	final public static String SYS_REP = "REPORT"; // 报价
	final public static String SYS_CON = "CON"; // 合同
	final public static String SYS_ORD = "ORD"; // 订单
	final public static String SYS_ABI = "ABI"; // 裁决
	final public static String SYS_PAY = "PAY";// 付款
	final public static String SYS_COM = "COM";// 单一供应商的决策评价
	final public static String SYS_SUP = "SUP";
	final public static String SYS_TEMPSUP = "TEMPSUP";
	final public static String SYS_GOOD = "GOOD";// 物品编号

	// 目录采购
	final public static String SYS_ORDMC = "ORDMC"; // 目录采购价格联络书
	final public static String SYS_ORDMO = "ORDMO"; // 目录采购订单
	final public static String SYS_PAYM = "PAYM"; // 目录采购付款通知书
	final public static String SYS_CATE = "CATE"; // 目录采购特采申请单
	final public static String SYS_CKACM = "CKACM"; // 目录采购验收单
	final public static String SYS_QUEM = "QUERYM"; // 目录采购询价
	final public static String SYS_ABIM = "ABIM"; // 目录采购决裁;
	final public static String SYS_GOODAPPLYM = "GOODAPPLYM";// 物品申请编号

	// 模块编号
	final public static String SYS_MOD = "MODULE";

	// 归零间隔
	final public static int INTERVEL_0 = 0; // 累计
	final public static int INTERVEL_1 = 10; // 以月做单位
	final public static int INTERVEL_2 = 20; // 以年做单位
	final public static int INTERVEL_3 = 30; // 以日做单位

	// 树节点编号
	public static int TREE_ROOT = 1;// 目录根节点
	public static String TREE_BASE = "01";// 基础数据结构树编号
	public static String TREE_APP = "02";// 模块结构树编号
	public static String TREE_DICTORY = "011";// 数据字典结构树编号
	public static String TREE_DICT_1 = "0111";// 投资类型
	public static String TREE_DICT_2 = "0112";// 费用科目
	public static String TREE_DICT_3 = "0113";// 采购类别
	public static String TREE_DICT_4 = "0114";// 采购性质
	public static String TREE_DICT_5 = "0115";// 合同性质
	public static String TREE_DICT_6 = "0116";// 合同类型
	public static String TREE_DICT_7 = "0117";// 结算币种
	public static String TREE_DICT_8 = "0118";// 项目类别
	public static String TREE_DICT_9 = "0119";// 付款方式
	public static String TREE_DICT_10 = "0120";// 要求收到票据
	public static String TREE_DICT_11 = "0121";// 款项名称
	public static String TREE_DICT_12 = "0122";// 行业种类(长期供应产商)
	public static String TREE_DICT_13 = "0124";// 行业种类(临时供应商)
	public static String TREE_DICT_14 = "0123";// 地区
	public static String TREE_DICT_15 = "0125";// 计量单位
	public static String TREE_DICT_16 = "0126";// 颜色
	public static String TREE_DICT_17 = "0127";// 职务
	public static String TREE_DICT_18 = "0128";// 目录采购分类
	public static String TREE_DICT_19 = "0130";// 费用预算分类
	public static String TREE_DICT_20 = "0131";// 目录采购结算方式

	public static String TREE_DEPT = "012";// 部门结构树编号

	// 字典编码
	public static String BD_KEYS[] = { "ZD0000", // 0：用户信息
			"ZD0001", // 1：部门结构代码编号
			"ZD0002", // 2：投资类型
			"ZD0003", // 3：采购类别
			"ZD0004", // 4：采购性质
			"ZD0005", // 5：合同性质
			"ZD0006", // 6：合同类型
			"ZD0007", // 7：结算币种
			"ZD0008", // 8：项目类别
			"ZD0009", // 9：付款方式
			"ZD0010", // 10：要求收到票据
			"ZD0011", // 11：款项名称
			"ZD0012", // 12：行业种类(长期供应商)
			"ZD0013", // 13：行业种类(临时供应商)
			"ZD0014", // 14：地区
			"ZD0015", // 15：模块功能信息
			"ZD0016", // 16: 计量单位
			"ZD0017", // 17: 颜色
			"ZD0018", // 18: 供应商信息
			"ZD0019", // 19: 职务
			"ZD0020", // 20: 目录采购分类
			"ZD0021", // 21: 合同模板
			"ZD0022", // 22： 科目代码
			"ZD0023", // 23： 询价单附件
			"ZD0024", // 24： 费用预算分类
			"ZD0025", // 25: 目录采购结算方式
	};

	public static String QUERY_PREFIX = "query_";
	public static String ORDER_PREFIX = "order_";
	public static String COLLE_PREFIX = "colle_";

	public final static String ministerZ = "部长";
	public final static String ministerF = "副部长";
	// public final static String keZhangZ = "正科长";
	public final static String keZhangZ = "科长";
	public final static String keZhangF = "副科长";
	public final static String CO = "CO";
	public final static String xiZhang = "系长";
	public final static String fawu = "法务审查担当";
	public final static String jiancha = "监察审查担当";
	public final static String charge = "担当";
	public final static String YSY = "预算员";
	public final static String JBR = "申请担当";
	public final static String ZR = "主任";
	public final static String cgk = "采购部";// 采购部
	public final static String fgk = "法规科";// 总经理室
	public final static String jck = "审计监察科";// 总经理室
}