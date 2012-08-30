package baseSrc.common.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import baseSrc.common.BaseCom;
import baseSrc.framework.BaseConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PrivilegeTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	private String actionName;
	
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName.toUpperCase();
	}
	
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();

		Object o = this.pageContext.getSession().getAttribute(BaseConstants.ISC_BASE_BEANCOM_ID);

		// 取得画面信息
		String uri = request.getRequestURI();
		String name = "";
		if(uri.indexOf(".do") > 0){
			name = uri.substring(0, uri.indexOf(".do"));
		}else if(uri.indexOf(".jsp") > 0){
			name = uri.substring(0, uri.indexOf(".jsp"));
		}
		name = name.substring(name.lastIndexOf("/") + 1).toUpperCase();
		
		if (null == o
				|| null == ((BaseCom)o).getUserId()
				|| "".equals(((BaseCom)o).getUserId()))
		{
			return EVAL_BODY_INCLUDE;
		}
		
		BaseCom baseCom = (BaseCom)o;
		
		// 取用户有权限的画面
		Map<String, List> userPrivileMap = baseCom.getUserPrivilageMap();
		
		// 判断是否有权限,没有权限跳转到登陆画面
		if(userPrivileMap.containsKey(name))
		{
			List pageactionprivilege = userPrivileMap.get(name);
			if(!pageactionprivilege.contains(actionName)){
				try {
					pageContext.getOut().write("");//标签的返回值  
				} catch (IOException e) {
					e.printStackTrace();
				}

				// 没有Action权限不显示
				return SKIP_BODY;
			}
		}
		else
		{
			// 没有页面权限什么都不显示
			return SKIP_BODY;
		}
		
		return EVAL_BODY_INCLUDE;
	}



}
