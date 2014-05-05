/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.id;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.glaf.core.jdbc.DBConnectionFactory;

public class LongIdStartup {

	public static void init() {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = " select * from SYS_DBID where NAME_= 'next.dbid' ";
		try {
			con = DBConnectionFactory.getConnection();
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			boolean insert = true;
			if (rs.next()) {
				insert = false;
			}
			if (insert) {
				sql = "insert into SYS_DBID(NAME_, TITLE_, VALUE_, VERSION_) values ('next.dbid', '系统内置主键', '1001', 1)";
				psmt = con.prepareStatement(sql);
				psmt.executeUpdate();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException ex) {
			}
			try {
				if (psmt != null) {
					psmt.close();
				}
			} catch (SQLException ex) {
			}
			try {
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException ex) {
			}
		}
	}
}
