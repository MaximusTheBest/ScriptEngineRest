package nashorn.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StatusRequest implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private Boolean isFinished;

	private Long id;

	private String url;

	public Boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
