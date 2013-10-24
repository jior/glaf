<?xml version="1.0" encoding="UTF-8" ?> 

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packageName}.mapper.${entityName}Mapper">

 	<resultMap id="${modelName}ResultMap"	type="${packageName}.domain.${entityName}">
		   
<#if idField.type?exists && ( idField.type== 'Integer' )>
        <id property="${idField.name}" column="${idField.columnName}" jdbcType="INTEGER" />
<#elseif idField.type?exists && ( idField.type== 'Long' )>
        <id property="${idField.name}" column="${idField.columnName}" jdbcType="BIGINT" />
<#elseif idField.type?exists && ( idField.type== 'String')>
        <id property="${idField.name}" column="${idField.columnName}" jdbcType="VARCHAR"/>
</#if>
		
<#if pojo_fields?exists>
  <#list  pojo_fields as field>
   <#if field.name?exists && field.columnName?exists && field.type?exists>
	<#if field.type?exists && ( field.type== 'Integer' )>
        <result property="${field.name}" column="${field.columnName}" jdbcType="INTEGER"/>
      <#elseif field.type?exists && ( field.type== 'Long' )>
        <result property="${field.name}" column="${field.columnName}" jdbcType="BIGINT"/>
	  <#elseif field.type?exists && ( field.type== 'Double' )>
        <result property="${field.name}" column="${field.columnName}" jdbcType="DOUBLE"/>
	  <#elseif field.type?exists && ( field.type== 'Boolean' )>
        <result property="${field.name}" column="${field.columnName}" jdbcType="BOOLEAN"/>
	  <#elseif field.type?exists && ( field.type== 'Date')>
        <result property="${field.name}" column="${field.columnName}" jdbcType="TIMESTAMP"/>
	  <#elseif field.type?exists && ( field.type== 'String')>
        <result property="${field.name}" column="${field.columnName}" jdbcType="VARCHAR"/>
	</#if>
	</#if>
  </#list>
</#if>
	</resultMap>

 
	<insert id="insert${entityName}" parameterType="${packageName}.domain.${entityName}">
		insert into
		${tableName} ( ${idField.columnName}
<#if pojo_fields?exists>
  <#list  pojo_fields as field>
   <#if field.name?exists && field.columnName?exists && field.type?exists>
	<#if field.type?exists && ( field.type== 'Integer' )>
        ,${field.columnName} 
      <#elseif field.type?exists && ( field.type== 'Long' )>
        ,${field.columnName} 
	  <#elseif field.type?exists && ( field.type== 'Double' )>
        ,${field.columnName} 
	  <#elseif field.type?exists && ( field.type== 'Boolean' )>
        ,${field.columnName} 
	  <#elseif field.type?exists && ( field.type== 'Date')>
        ,${field.columnName} 
	  <#elseif field.type?exists && ( field.type== 'String')>
        ,${field.columnName} 
	</#if>
   </#if>
  </#list>
</#if>
		)
		values (
		<#if idField.type?exists && ( idField.type== 'Integer' )>
          #GG{${idField.name}, jdbcType=INTEGER}
        <#elseif idField.type?exists && ( idField.type== 'Long' )>
          #GG{${idField.name}, jdbcType=BIGINT}
	    <#elseif idField.type?exists && ( idField.type== 'String')>
          #GG{${idField.name}, jdbcType=VARCHAR}
	    </#if>
	     
<#if pojo_fields?exists>
  <#list  pojo_fields as field>
   <#if field.name?exists && field.columnName?exists && field.type?exists>
	<#if field.type?exists && ( field.type== 'Integer' )>
        ,#GG{${field.name}, jdbcType=INTEGER}
      <#elseif field.type?exists && ( field.type== 'Long' )>
        ,#GG{${field.name}, jdbcType=BIGINT}
	  <#elseif field.type?exists && ( field.type== 'Double' )>
        ,#GG{${field.name}, jdbcType=DOUBLE}
	  <#elseif field.type?exists && ( field.type== 'Boolean' )>
        ,#GG{${field.name}, jdbcType=BOOLEAN}
	  <#elseif field.type?exists && ( field.type== 'Date')>
        ,#GG{${field.name}, jdbcType=TIMESTAMP}
	  <#elseif field.type?exists && ( field.type== 'String')>
        ,#GG{${field.name}, jdbcType=VARCHAR}
	</#if>
   </#if>
  </#list>
</#if>
		)
    </insert>

	 
	<update id="update${entityName}" parameterType="${packageName}.domain.${entityName}">
		update
		${tableName}
		set
        <trim prefix="" suffix="" suffixOverrides=",">		
<#if pojo_fields?exists>
  <#list  pojo_fields as field>
  <#if field.name?exists && field.columnName?exists && field.type?exists && field.editable>
	<#if field.type?exists && ( field.type== 'Integer' )>
        ${field.columnName} = #GG{${field.name}, jdbcType=INTEGER},
      <#elseif field.type?exists && ( field.type== 'Long' )>
        ${field.columnName} = #GG{${field.name}, jdbcType=BIGINT},
	  <#elseif field.type?exists && ( field.type== 'Double' )>
        ${field.columnName} = #GG{${field.name}, jdbcType=DOUBLE},
	  <#elseif field.type?exists && ( field.type== 'Boolean' )>
        ${field.columnName} = #GG{${field.name}, jdbcType=BOOLEAN},
	  <#elseif field.type?exists && ( field.type== 'Date')>
        ${field.columnName} = #GG{${field.name}, jdbcType=TIMESTAMP},
	  <#elseif field.type?exists && ( field.type== 'String')>
        ${field.columnName} = #GG{${field.name}, jdbcType=VARCHAR},
	</#if>
	</#if>
  </#list>
</#if>
        </trim>
		where
		<#if idField.type?exists && ( idField.type== 'Integer' )>
          ${idField.columnName} = #GG{${idField.name}, jdbcType=INTEGER}
        <#elseif idField.type?exists && ( idField.type== 'Long' )>
          ${idField.columnName} = #GG{${idField.name}, jdbcType=BIGINT}
	    <#elseif idField.type?exists && ( idField.type== 'String')>
          ${idField.columnName} = #GG{${idField.name}, jdbcType=VARCHAR}
	    </#if>
		
    </update>

 
	<delete id="delete${entityName}ById" parameterType="string"> 
        delete from ${tableName}
        where ${idField.columnName} =	#GG{id}
	</delete>
	
	<delete id="delete${entityName}s" parameterType="${packageName}.query.${entityName}Query">
		delete from ${tableName}
		where ( 
 			  ${idField.columnName} IN
              <foreach item="x_${idField.name}" index="index" collection="${idField.name}s" 
                     open="(" separator="," close=")">
                  #GG{x_${idField.name}}
              </foreach>
		)
	</delete>

	<select id="get${entityName}ById" parameterType="string" resultMap="${modelName}ResultMap">
		select * from ${tableName} where ${idField.columnName} = #GG{id}
	</select>


	<select id="get${entityName}s" 
	    parameterType="${packageName}.query.${entityName}Query"
		resultMap="${modelName}ResultMap">
		select E.*
		<include refid="select${entityName}sSql" />
		<if test="orderBy != null">
		   order by #F{orderBy}
        </if>
	</select>

	<select id="get${entityName}Count" 
	    parameterType="${packageName}.query.${entityName}Query"
		resultType="int">
		select count(*)
		<include refid="select${entityName}sSql" />
	</select>

	<sql id="select${entityName}sSql">

		from ${tableName} E

        <if test="isFilterPermission">
            <include refid="filterPermissionSql" />
		</if>
		 
		<where>
		       1 = 1  

<#if pojo_fields?exists>
  <#list  pojo_fields as field>
  <#if field.name?exists && field.columnName?exists && field.type?exists>
	<#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long' || field.type== 'Double' || field.type== 'Date')>

			<if test="${field.name} != null">
				and E.${field.columnName} = #GG{${field.name}}
            </if>

			<if test="${field.name}GreaterThanOrEqual != null">
				and E.${field.columnName} &gt;= #GG{${field.name}GreaterThanOrEqual}
            </if>

			<if test="${field.name}LessThanOrEqual != null">
				and E.${field.columnName} &lt;= #GG{${field.name}LessThanOrEqual}
            </if>

			<if test="${field.name}s != null and ${field.name}s.size() &gt; 0">
			    and E.${field.columnName} IN
                <foreach item="x_${field.name}" index="index" collection="${field.name}s" 
                     open="(" separator="," close=")">
                  #GG{x_${field.name}}
                </foreach>
			</if>

	  <#elseif field.type?exists && ( field.type== 'String')>
	        
			<if test="${field.name} != null and ${field.name} != '' ">
				and E.${field.columnName} = #GG{${field.name}}
            </if>

			<if test="${field.name}Like != null and ${field.name}Like != '' ">
				and E.${field.columnName} like #GG{${field.name}Like}
            </if>

			<if test="${field.name}s != null and ${field.name}s.size() &gt; 0">
			    and E.${field.columnName} IN
                <foreach item="x_${field.name}" index="index" collection="${field.name}s" 
                     open="(" separator="," close=")">
                  #GG{x_${field.name}}
                </foreach>
			</if>

	</#if>
	</#if>
  </#list>
</#if>			 
			 

		<if test="dynamicAccesses != null and dynamicAccesses.size !=0 ">
		    <foreach item="dynamicAccess" collection="dynamicAccesses" index="dax">
				<if test="dynamicAccess.filterSql != null">
					$F{dynamicAccess.filterSql} 
			    </if>
			</foreach>	
		</if>

        <if test="isFilterPermission">
            <include refid="filterPermissionCriteriaSql" />
		</if>
			 
		</where>
	</sql>

</mapper>