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

package com.glaf.core.io.compress;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.glaf.core.config.Configuration;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.ReflectUtils;

public class PooledStreamCompressor {

	public static void main(String[] args) throws Exception {
		String codecClassname = "com.glaf.core.io.compress.SnappyCodec";
		Class<?> codecClass = Class.forName(codecClassname);
		Configuration conf = new Configuration();
		CompressionCodec codec = (CompressionCodec) ReflectUtils.newInstance(
				codecClass, conf);
		FileInputStream fin = null;
		FileOutputStream fout = null;
		Compressor compressor = null;
		try {
			fin = new FileInputStream("./glaf-core-src.zip");
			fout = new FileOutputStream("./tmp.zip");
			compressor = CodecPool.getCompressor(codec);
			CompressionOutputStream out = codec.createOutputStream(fout,
					compressor);
			IOUtils.copyBytes(fin, out, 4096, false);
			out.finish();
		} finally {
			CodecPool.returnCompressor(compressor);
		}
	}

}