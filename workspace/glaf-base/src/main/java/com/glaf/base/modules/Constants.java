package com.glaf.base.modules;

/**
 * <p>
 * Title: Global.java
 * </p>
 * <p>
 * Description: ���ñ�����
 * </p>
 */

public class Constants {
	// �û�session����
	public static String ROOT_PATH = "";
	public static int PAGE_SIZE = 10;// ȱʡҳ���С

	// �ϴ��ļ�����
	public static String UPLOAD_DIR = "/upload/";
	public static long UPLOAD_MAX_SIZE = 2 * 1024 * 1024;// 20M
	public static long UPLOAD_FILE_SIZE_MAX = 2 * 1024 * 1024;// 20M

	// ��ģ������� �ض��ɹ�
	final public static String SYS_BUD = "BUDGET"; // Ԥ��
	final public static String SYS_IMP = "IMPORT"; // �ز�
	final public static String SYS_PUR = "PURCHA"; // �ɹ�
	final public static String SYS_QUE = "QUERY"; // ѯ��
	final public static String SYS_REP = "REPORT"; // ����
	final public static String SYS_CON = "CON"; // ��ͬ
	final public static String SYS_ORD = "ORD"; // ����
	final public static String SYS_ABI = "ABI"; // �þ�
	final public static String SYS_PAY = "PAY";// ����
	final public static String SYS_COM = "COM";// ��һ��Ӧ�̵ľ�������
	final public static String SYS_SUP = "SUP";
	final public static String SYS_TEMPSUP = "TEMPSUP";
	final public static String SYS_GOOD = "GOOD";// ��Ʒ���

	// Ŀ¼�ɹ�
	final public static String SYS_ORDMC = "ORDMC"; // Ŀ¼�ɹ��۸�������
	final public static String SYS_ORDMO = "ORDMO"; // Ŀ¼�ɹ�����
	final public static String SYS_PAYM = "PAYM"; // Ŀ¼�ɹ�����֪ͨ��
	final public static String SYS_CATE = "CATE"; // Ŀ¼�ɹ��ز����뵥
	final public static String SYS_CKACM = "CKACM"; // Ŀ¼�ɹ����յ�
	final public static String SYS_QUEM = "QUERYM"; // Ŀ¼�ɹ�ѯ��
	final public static String SYS_ABIM = "ABIM"; // Ŀ¼�ɹ�����;
	final public static String SYS_GOODAPPLYM = "GOODAPPLYM";// ��Ʒ������

	// ģ����
	final public static String SYS_MOD = "MODULE";

	// ������
	final public static int INTERVEL_0 = 0; // �ۼ�
	final public static int INTERVEL_1 = 10; // ��������λ
	final public static int INTERVEL_2 = 20; // ��������λ
	final public static int INTERVEL_3 = 30; // ��������λ

	// ���ڵ���
	public static int TREE_ROOT = 1;// Ŀ¼���ڵ�
	public static String TREE_BASE = "01";// �������ݽṹ�����
	public static String TREE_APP = "02";// ģ��ṹ�����
	public static String TREE_DICTORY = "011";// �����ֵ�ṹ�����
	public static String TREE_DICT_1 = "0111";// Ͷ������
	public static String TREE_DICT_2 = "0112";// ���ÿ�Ŀ
	public static String TREE_DICT_3 = "0113";// �ɹ����
	public static String TREE_DICT_4 = "0114";// �ɹ�����
	public static String TREE_DICT_5 = "0115";// ��ͬ����
	public static String TREE_DICT_6 = "0116";// ��ͬ����
	public static String TREE_DICT_7 = "0117";// �������
	public static String TREE_DICT_8 = "0118";// ��Ŀ���
	public static String TREE_DICT_9 = "0119";// ���ʽ
	public static String TREE_DICT_10 = "0120";// Ҫ���յ�Ʊ��
	public static String TREE_DICT_11 = "0121";// ��������
	public static String TREE_DICT_12 = "0122";// ��ҵ����(���ڹ�Ӧ����)
	public static String TREE_DICT_13 = "0124";// ��ҵ����(��ʱ��Ӧ��)
	public static String TREE_DICT_14 = "0123";// ����
	public static String TREE_DICT_15 = "0125";// ������λ
	public static String TREE_DICT_16 = "0126";// ��ɫ
	public static String TREE_DICT_17 = "0127";// ְ��
	public static String TREE_DICT_18 = "0128";// Ŀ¼�ɹ�����
	public static String TREE_DICT_19 = "0130";// ����Ԥ�����
	public static String TREE_DICT_20 = "0131";// Ŀ¼�ɹ����㷽ʽ

	public static String TREE_DEPT = "012";// ���Žṹ�����

	// �ֵ����
	public static String BD_KEYS[] = { "ZD0000", // 0���û���Ϣ
			"ZD0001", // 1�����Žṹ������
			"ZD0002", // 2��Ͷ������
			"ZD0003", // 3���ɹ����
			"ZD0004", // 4���ɹ�����
			"ZD0005", // 5����ͬ����
			"ZD0006", // 6����ͬ����
			"ZD0007", // 7���������
			"ZD0008", // 8����Ŀ���
			"ZD0009", // 9�����ʽ
			"ZD0010", // 10��Ҫ���յ�Ʊ��
			"ZD0011", // 11����������
			"ZD0012", // 12����ҵ����(���ڹ�Ӧ��)
			"ZD0013", // 13����ҵ����(��ʱ��Ӧ��)
			"ZD0014", // 14������
			"ZD0015", // 15��ģ�鹦����Ϣ
			"ZD0016", // 16: ������λ
			"ZD0017", // 17: ��ɫ
			"ZD0018", // 18: ��Ӧ����Ϣ
			"ZD0019", // 19: ְ��
			"ZD0020", // 20: Ŀ¼�ɹ�����
			"ZD0021", // 21: ��ͬģ��
			"ZD0022", // 22�� ��Ŀ����
			"ZD0023", // 23�� ѯ�۵�����
			"ZD0024", // 24�� ����Ԥ�����
			"ZD0025", // 25: Ŀ¼�ɹ����㷽ʽ
	};

	public static String QUERY_PREFIX = "query_";
	public static String ORDER_PREFIX = "order_";
	public static String COLLE_PREFIX = "colle_";

	public final static String ministerZ = "����";
	public final static String ministerF = "������";
	// public final static String keZhangZ = "���Ƴ�";
	public final static String keZhangZ = "�Ƴ�";
	public final static String keZhangF = "���Ƴ�";
	public final static String CO = "CO";
	public final static String xiZhang = "ϵ��";
	public final static String fawu = "������鵣��";
	public final static String jiancha = "�����鵣��";
	public final static String charge = "����";
	public final static String YSY = "Ԥ��Ա";
	public final static String JBR = "���뵣��";
	public final static String ZR = "����";
	public final static String cgk = "�ɹ���";// �ɹ���
	public final static String fgk = "�����";// �ܾ�����
	public final static String jck = "��Ƽ���";// �ܾ�����
}