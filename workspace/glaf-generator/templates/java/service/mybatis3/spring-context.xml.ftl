<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

 
	<!--service -->
	<bean id="${modelName}Service" class="${packageName}.service.${entityName}ServiceImpl">

<#if idField.type?exists && ( idField.type== 'Integer' )>        
        <property name="idGenerator">
			<ref bean="myBatisDbLongIdGenerator" />
		</property>
<#elseif idField.type?exists && ( idField.type== 'Long' )>
        <property name="idGenerator">
			<ref bean="myBatisDbLongIdGenerator" />
		</property>
<#elseif idField.type?exists && ( idField.type== 'String')>
	    <property name="idGenerator">
			<ref bean="myBatisDbIdGenerator" />
		</property>
</#if>

		<property name="sqlSessionTemplate">
			<ref bean="sqlSession" />
		</property>

		<property name="${modelName}Mapper">
			<ref bean="${modelName}Mapper" />
		</property>

	</bean>

</beans>