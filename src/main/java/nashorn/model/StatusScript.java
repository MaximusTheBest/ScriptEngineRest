package nashorn.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StatusScript implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private Boolean isCalcel;

	private Boolean isFinished;

	private Long id;

	private String url;

	public Boolean getIsCalcel() {
		return isCalcel;
	}

	public void setIsCalcel(Boolean isCalcel) {
		this.isCalcel = isCalcel;
	}

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

	public static class Builder {
		private Boolean isCalcel;
		private Boolean isFinished;
		private Long id;
		private String url;

		public Builder isCalcel(Boolean isCalcel) {
			this.isCalcel = isCalcel;
			return this;
		}

		public Builder isFinished(Boolean isFinished) {
			this.isFinished = isFinished;
			return this;
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public StatusScript build() {
			return new StatusScript(this);
		}
	}

	private StatusScript(Builder builder) {
		this.isCalcel = builder.isCalcel;
		this.isFinished = builder.isFinished;
		this.id = builder.id;
		this.url = builder.url;
	}
}
