<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

        <!--请将此节点内容复制到WebContent\WEB-INF\conf\spring\action-apps.xml中-->
	<bean name="/apps/${modelName}" class="${packageName}.action.${entityName}Action"> 
		<property name="${modelName}Service"> 
			<ref bean="${modelName}Service" /> 
		</property>
	</bean> 


</beans>