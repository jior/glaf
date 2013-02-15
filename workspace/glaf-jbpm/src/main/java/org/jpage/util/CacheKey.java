/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.util;

public interface CacheKey {

	public static final String ORM_CACHE = "cache-orm";

	public static final String MAIL_CACHE = "cache-mail";

	public static final String MESSAGER_CACHE = "cache-messager";

	public static final String METADATA_CACHE = "cache-metadata";

	public static final String QUERY_CACHE = "cache-query";

	public static final String QUERY_ID_CACHE = "cache-query-id";

	public static final String PERSISTENT_CACHE = "cache-persistent";

	public static final String NO_PERSISTENT_CACHE = "cache-no-persistent";

	public static final String AUTHENTICATION_CACHE = "cache-authentication";

}