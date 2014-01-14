package com.vertonur.pagination;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.constant.ServiceEnum;

public class PaginationContext {
	public static final String PAGE_CXT = "pageCxt";

	public static enum CxtType {
		TOPIC, RESPONSE, PRIVATE_MSG, MEMBER, MDR_LOG
	}

	private long size;
	private int start;
	protected int offset;
	private long totalPages;
	private int currentPage;
	private String url;

	public PaginationContext(long size, int start, String url, CxtType cxtType) {
		this.size = size;
		this.start = start;
		setOffset(cxtType);

		if (size % offset != 0)
			totalPages = size / offset + 1;
		else
			totalPages = size / offset;

		this.currentPage = start / offset + 1;
		this.url = url;
	}

	public long getSize() {
		return size;
	}

	public int getStart() {
		return start;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(CxtType cxtType) {
		RuntimeParameterService service = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		switch (cxtType) {
		case TOPIC:
			offset = service.getInfoConfig().getInfoPgnOffset();
			break;
		case RESPONSE:
			offset = service.getCommentConfig().getCmtPgnOffset();
			break;
		case PRIVATE_MSG:
			offset = service.getPmConfig().getPrivateMsgPgnOffset();
			break;
		case MEMBER:
			offset = service.getUserConfig().getUsrPgnOffset();
			break;
		case MDR_LOG:
			offset = service.getModerationConfig().getMdrLgPgnOffset();
			break;
		}
	}

	public long getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public String getUrl() {
		return url;
	}
}