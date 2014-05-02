package com.glaf.core.web.springmvc;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.context.ApplicationContext;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.ZipUtils;

@Controller("/sys/project")
@RequestMapping("/sys/project")
public class WebProjectController {

	@RequestMapping
	public ModelAndView showPkg(HttpServletRequest request) {
		return new ModelAndView("/modules/sys/project/showPkg");
	}

	@ResponseBody
	@RequestMapping("/export")
	public void zip(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String includes = request.getParameter("includes");
		List<String> list = StringTools.split(includes);
		File path = new File(ApplicationContext.getAppPath());
		if (path.exists() && path.isDirectory()) {
			Map<String, byte[]> bytesMap = new java.util.HashMap<String, byte[]>();
			String root = path.getAbsolutePath();
			this.readFile(root, bytesMap, path, list);
			byte[] bytes = ZipUtils.toZipBytes(bytesMap);
			try {
				ResponseUtils.download(request, response, bytes, "export.zip");
			} catch (ServletException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void readFile(String root, Map<String, byte[]> bytesMap, File path,
			List<String> includes) {
		File contents[] = path.listFiles();
		if (contents != null) {
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].isFile()) {
					String ext = FileUtils.getFileExt(contents[i]
							.getAbsolutePath());
					if (includes.contains(ext.toLowerCase())) {
						byte[] bytes = FileUtils.getBytes(contents[i]);
						if (bytes.length < FileUtils.MB_SIZE * 5) {
							String name = contents[i].getAbsolutePath();
							if (StringUtils.contains(name, "jdbc.properties")) {
								continue;
							}
							if (StringUtils.contains(name, "hibernate.cfg.xml")) {
								continue;
							}
							name = StringTools.replace(name, root, "");
							bytesMap.put(name, bytes);
						}
					}
				} else {
					readFile(root, bytesMap, contents[i], includes);
				}
			}
		}
	}

}
