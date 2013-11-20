package ${package};

/**
 * @author 
 * 
 * ${class}.java maps database table &quot;${table.name}&quot; to java bean.
 */
public class ${class}{
	<#assign columns = table.fieldMetadata?if_exists>
	
	/*
	* Constants.
	*/
	<#list columns as col>
    public static final String ${col.name?upper_case}_COL = "${col.name?lower_case}";
    </#list>
    public static final String TABLE_NAME = "${table.name?lower_case}";
    
    /*
    * Properties.
    */
    <#list columns as col>
    private ${col.jdbcTypeClass.name} ${col.name?lower_case};
    </#list>
    
    /**
     * @param descriptor
     */
    public ${class}() {
    }
    
<#list columns as col>
	<#assign colName = col.name?lower_case>
	<#assign methodName = colName?cap_first>
    /**
     * Method get${methodName}.
     * @return
     */
    public ${col.jdbcTypeClass.name} get${methodName}() {
        return ${colName};
    }
	
	/**
	 * Method set${methodName}.
	 * @param ${colName}
	 */
    public void set${methodName}(${col.jdbcTypeClass.name} ${colName}) {
        this.${colName} = ${colName};
    }
    
    <#if col.foreignKey>
    /*Foreign key ${colName} ({fk.targetTableName}.{fk.targetColumnName})*/
    </#if>
</#list>
}