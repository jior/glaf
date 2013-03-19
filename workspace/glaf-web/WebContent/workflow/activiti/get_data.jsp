<%@ page contentType="text/plain;charset=UTF-8"%>
<%@ page import="org.json.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%
    JSONObject json = new JSONObject();
    JSONArray array = new JSONArray();
	Connection con = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	try {
		con = com.glaf.core.jdbc.DBConnectionFactory.getConnection();
		psmt = con.prepareStatement(" select * from Test_ACT order by ID_ asc ");
		rs = psmt.executeQuery();
		int total = 0;
		while(rs.next()){
			total++;
			JSONObject obj = new JSONObject();
			obj.put("ID_", rs.getObject("ID_"));
			obj.put("PROCESSNAME_", rs.getObject("PROCESSNAME_"));
			obj.put("PROCESSINSTANCEID_", rs.getObject("PROCESSINSTANCEID_"));
			obj.put("STATUS_", rs.getObject("STATUS_"));
			obj.put("WFSTATUS_", rs.getObject("WFSTATUS_"));
			array.put(obj);
		}
		json.put("total", total);
		json.put("rows", array);
        out.println(json.toString('\n'));
        out.flush();
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		com.glaf.core.util.JdbcUtils.close(rs);
		com.glaf.core.util.JdbcUtils.close(psmt);
		com.glaf.core.util.JdbcUtils.close(con);
	}
%>