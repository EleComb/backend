package io.penguinstats.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "stage_v2")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "The model of a stage.")
public class Stage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@JsonIgnore
	private ObjectId id;
	private String stageType;
	@Indexed
	private String stageId;
	private String zoneId;
	private String code;
	@JsonProperty("code_i18n")
	private Map<String, String> codeMap;
	private Integer apCost;
	private Boolean isGacha;
	@Transient
	private List<DropInfo> dropInfos;
	@Transient
	private StageExistence existence;
	private List<String> normalDrop;
	private List<String> specialDrop;
	private List<String> extraDrop;

	@JsonIgnore
	public Set<String> getDropsSet() {
		Set<String> set = new HashSet<>();
		if (this.normalDrop != null)
			set.addAll(this.normalDrop);
		if (this.specialDrop != null)
			set.addAll(this.specialDrop);
		if (this.extraDrop != null)
			set.addAll(this.extraDrop);
		set.add("furni");
		return set;
	}

	@JsonIgnore
	public Stage toLegacyView() {
		this.dropInfos = null;
		this.existence = null;
		return this;
	}

	@JsonIgnore
	public Stage toNewView() {
		this.normalDrop = null;
		this.specialDrop = null;
		this.extraDrop = null;
		return this;
	}

}
