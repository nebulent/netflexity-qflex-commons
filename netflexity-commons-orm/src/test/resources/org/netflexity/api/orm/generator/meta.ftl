<?xml version="1.0" ?>
<DATASOURCES>
	<DATASOURCE CLASS="&JNDI_JDBC;" NAME="sample">
		<!--JNDI_JDBC-->
		<PROPERTY KEY="JNDI_NAME" VALUE="jdbc/sample" />
		<PROPERTY KEY="DOUBLE_QUOTE_LITERAL" VALUE="Y" />

		<#list tables?if_exists as table>
		<RECORD NAME="${table.name?lower_case}" CLASS="${package}.${createJavaClassName(table.name?lower_case)}" VALIDATING="N">
			<#list table.fieldMetadata?if_exists as col>
				<#if col.jdbcType == 1 || col.jdbcType == 12 || col.jdbcType == -1>
			<FIELD NAME="${col.name?lower_case}" <#if col.primaryKey>PRIMARY_KEY="Y"</#if> <#if col.foreignKey>FOREIGN_KEY="Y"</#if> REQUIRED=<#if col.isRequired()>"Y"<#else>"N"</#if> LENGTH="#{col.length}" JDBC_TYPE="${col.jdbcType}">
				<NORMALIZE TYPE="TRIM" />
				<NORMALIZE TYPE="TRUNCATE_SILENTLY" />
			</FIELD>
				<#else>
			<FIELD NAME="${col.name?lower_case}" <#if col.primaryKey>PRIMARY_KEY="Y"</#if> <#if col.foreignKey>FOREIGN_KEY="Y"</#if> REQUIRED=<#if col.isRequired()>"Y"<#else>"N"</#if> JDBC_TYPE="${col.jdbcType}" />
				</#if>
			</#list>
		</RECORD>
		</#list>
		
	</DATASOURCE>
</DATASOURCES>