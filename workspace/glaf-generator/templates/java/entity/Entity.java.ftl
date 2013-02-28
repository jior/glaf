package ${packageName}.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import ${packageName}.util.*;

@Entity
@Table(name = "${tableName}")
public class ${entityName} implements Serializable {
	private static final long serialVersionUID = 1L;

        @Id
	@Column(name = "${idField.columnName}", length = 50, nullable = false)
	protected ${idField.type} ${idField.name};

	${jpa_fields?if_exists}

         
	public ${entityName}() {

	}

	public ${idField.type} get${idField.firstUpperName}(){
	    return this.${idField.name};
	}



	${jpa_get_methods?if_exists}


	public void set${idField.firstUpperName}(${idField.type} ${idField.name}) {
	    this.${idField.name} = ${idField.name}; 
	}

	${jpa_set_methods?if_exists}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		${entityName} other = (${entityName}) obj;
		if (${idField.name} == null) {
			if (other.${idField.name} != null)
				return false;
		} else if (!${idField.name}.equals(other.${idField.name}))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((${idField.name} == null) ? 0 : ${idField.name}.hashCode());
		return result;
	}


	public ${entityName} jsonToObject(JSONObject jsonObject) {
            return ${entityName}JsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ${entityName}JsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ${entityName}JsonFactory.toObjectNode(this);
	}


	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
