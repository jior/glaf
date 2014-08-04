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
		insert into ${tableName} 
		<trim prefix="(" suffix=")" suffixOverrides=",">
		    ${idField.columnName}
		<#if pojo_fields?exists>
		  <#list  pojo_fields as field>
		    <#if field.name?exists && field.columnName?exists && field.type?exists>
			<if test="${field.name} != null">
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
			<#elseif field.type?exists && ( field.type== 'Blob')>
				,${field.columnName} 
			<#elseif field.type?exists && ( field.type== 'Clob')>
				,${field.columnName} 
			<#elseif field.type?exists && ( field.type== 'String')>
				,${field.columnName} 
			</#if>
			</if>
	        </#if>
		  </#list>
		 </#if>
        </trim>

		<trim prefix=" values (" suffix=")" suffixOverrides=",">
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
			<if test="${field.name} != null">
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
			<#elseif field.type?exists && ( field.type== 'Blob')>
				,#GG{${field.name}, jdbcType=BLOB}
			<#elseif field.type?exists && ( field.type== 'Clob')>
				,#GG{${field.name}, jdbcType=VARCHAR}
			<#elseif field.type?exists && ( field.type== 'String')>
				,#GG{${field.name}, jdbcType=VARCHAR}
			</#if>
			</if>
		  </#if>
		</#list>
	    </#if>
	    </trim>
    </insert>

	 
	<update id="update${entityName}" parameterType="${packageName}.domain.${entityName}">
		update ${tableName}
		set
        <trim prefix="" suffix="" suffixOverrides=",">		
		<#if pojo_fields?exists>
		  <#list  pojo_fields as field>
			<#if field.name?exists && field.columnName?exists && field.type?exists>
			<if test="${field.name} != null">
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
			<#elseif field.type?exists && ( field.type== 'Clob')>
				${field.columnName} = #GG{${field.name}, jdbcType=VARCHAR},
			<#elseif field.type?exists && ( field.type== 'String')>
				${field.columnName} = #GG{${field.name}, jdbcType=VARCHAR},
			</#if>
			</if>
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

 
	<delete id="delete${entityName}ById" parameterType="${idField.lowerCaseType}"> 
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


	<select id="get${entityName}ById" parameterType="${idField.lowerCaseType}" resultMap="${modelName}ResultMap">
		select * from ${tableName} where ${idField.columnName} = #GG{id}
	</select>


	<select id="get${entityName}s" 
	    parameterType="${packageName}.query.${entityName}Query"
		resultMap="${modelName}ResultMap">
		select distinct E.*
		<include refid="select${entityName}sSql" />
		<choose>
		  <when test="orderBy != null">
		     order by #F{orderBy}
          </when>
		  <otherwise>
		      order by E.${idField.columnName} desc
		  </otherwise>
        </choose>
	</select>


	<select id="get${entityName}Count" 
	    parameterType="${packageName}.query.${entityName}Query"
		resultType="int">
		select count(*)
		<include refid="select${entityName}sSql" />
	</select>


	<sql id="select${entityName}sSql">

		from ${tableName} E
		
		<#if classDefinition.jbpmSupport >
		<if test=" workedProcessFlag == 'WD' and appActorIds != null and appActorIds.size != 0  ">
           inner join JBPM_TASKINSTANCE T
		   on E.PROCESSINSTANCEID_ = T.PROCINST_
		</if>
		</#if>
		 
		<where>
		       1 = 1  
			  <#if classDefinition.jbpmSupport >
			   <if test="workedProcessFlag == 'WD' and appActorIds != null and appActorIds.size != 0 ">
			     and ( T.END_ IS NOT NULL)
                 and ( T.ACTORID_ in
                 <foreach item="x_actorId" index="index" collection="appActorIds" 
						open="(" separator="," close=")">
					#GG{x_actorId}
				 </foreach>
				 )
			   </if>

			   <if test="workedProcessFlag == 'PD' and appActorIds != null and appActorIds.size != 0 ">
			      and E.PROCESSINSTANCEID_ in (
				          SELECT a.PROCINST_
						  FROM JBPM_TASKINSTANCE a 
						  WHERE (1 = 1) 
						  AND (a.END_ IS NULL)
						  AND (a.ACTORID_ IS NOT NULL) 
						  AND (a.ACTORID_ in 
						    <foreach item="x_actorId2" index="index" collection="appActorIds" 
						          open="(" separator="," close=")">
					              #GG{x_actorId2}
				            </foreach>
						  )
				        union
				          SELECT a.PROCINST_
						  FROM JBPM_TASKINSTANCE a 
						  INNER JOIN JBPM_TASKACTORPOOL t
						  ON a.ID_ = t.TASKINSTANCE_
						  INNER JOIN JBPM_POOLEDACTOR p  
						  ON p.ID_ = t.POOLEDACTOR_ 
						  WHERE (1 = 1)  
						  AND (a.END_ IS NULL)
						  AND (a.ACTORID_ IS NULL)
						  AND (p.ACTORID_ in 
                            <foreach item="x_actorId3" index="index" collection="appActorIds" 
						             open="(" separator="," close=")">
					              #GG{x_actorId3}
				            </foreach>
						  )  
                 )
			   </if>

			   <if test="workedProcessFlag == 'ALL' and appActorIds != null and appActorIds.size != 0 ">
			      and E.PROCESSINSTANCEID_ in (
				          SELECT a.PROCINST_
						  FROM JBPM_TASKINSTANCE a 
						  WHERE (1 = 1) 
						  AND (a.ACTORID_ IS NOT NULL) 
						  AND (a.ACTORID_ in 
						    <foreach item="x_actorId2" index="index" collection="appActorIds" 
						          open="(" separator="," close=")">
					              #GG{x_actorId2}
				            </foreach>
						  )
				        union
				          SELECT a.PROCINST_
						  FROM JBPM_TASKINSTANCE a 
						  INNER JOIN JBPM_TASKACTORPOOL t
						  ON a.ID_ = t.TASKINSTANCE_
						  INNER JOIN JBPM_POOLEDACTOR p  
						  ON p.ID_ = t.POOLEDACTOR_ 
						  WHERE (1 = 1) 
						  AND (a.ACTORID_ IS NULL)
						  AND (p.ACTORID_ in 
                            <foreach item="x_actorId3" index="index" collection="appActorIds" 
						             open="(" separator="," close=")">
					              #GG{x_actorId3}
				            </foreach>
						  )  
                 )
			   </if>


			<if test="processInstanceIds != null and processInstanceIds.size != 0">
				and E.PROCESSINSTANCEID_ IN
				<foreach item="x_processInstanceId" index="index"
					collection="processInstanceIds" open="(" separator="," close=")">
					#GG{x_processInstanceId}
                </foreach>
			</if>
		  </#if>

<#if pojo_fields?exists>
  <#list  pojo_fields as field>
  <#if field.name?exists && field.columnName?exists && field.type?exists>
   <#if field.name != 'processInstanceId'>
	<#if field.type?exists && ( field.type== 'Integer' || field.type== 'Long' )>

			<if test="${field.name} != null">
				and E.${field.columnName} = #GG{${field.name}}
            </if>

			<if test="${field.name}GreaterThanOrEqual != null">
				and E.${field.columnName} &gt;= #GG{${field.name}GreaterThanOrEqual}
            </if>

			<if test="${field.name}LessThanOrEqual != null">
				and E.${field.columnName} &lt;= #GG{${field.name}LessThanOrEqual}
            </if>

			<if test="${field.name}s != null and ${field.name}s.size != 0">
			    and E.${field.columnName} IN
                <foreach item="x_${field.name}" index="index" collection="${field.name}s" 
                     open="(" separator="," close=")">
                  #GG{x_${field.name}}
                </foreach>
			</if>
      
	<#elseif field.type?exists && (field.type== 'Double' || field.type== 'Date')>

			<if test="${field.name}GreaterThanOrEqual != null">
				and E.${field.columnName} &gt;= #GG{${field.name}GreaterThanOrEqual}
            </if>

			<if test="${field.name}LessThanOrEqual != null">
				and E.${field.columnName} &lt;= #GG{${field.name}LessThanOrEqual}
            </if>

	<#elseif field.type?exists && ( field.type== 'String')>
	        
			<if test="${field.name} != null and ${field.name} != '' ">
				and E.${field.columnName} = #GG{${field.name}}
            </if>

			<if test="${field.name}Like != null and ${field.name}Like != '' ">
				and E.${field.columnName} like #GG{${field.name}Like}
            </if>

			<if test="${field.name}s != null and ${field.name}s.size != 0">
			    and E.${field.columnName} IN
                <foreach item="x_${field.name}" index="index" collection="${field.name}s" 
                     open="(" separator="," close=")">
                  #GG{x_${field.name}}
                </foreach>
			</if>
      </#if>
	 </#if>
	</#if>
  </#list>
</#if>			 
			 
		</where>
	</sql>

</mapper>