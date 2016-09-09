package nashorn.model;

public class ApiResponse {

	public ApiResponse() {
	}

	public enum Status {
		ok, error
	}

	private String message;

	private Status status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static class Builder {
		private String message;
		private Status status;

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder status(Status status) {
			this.status = status;
			return this;
		}

		public ApiResponse build() {
			return new ApiResponse(this);
		}
	}

	private ApiResponse(Builder builder) {
		this.message = builder.message;
		this.status = builder.status;
	}
}
