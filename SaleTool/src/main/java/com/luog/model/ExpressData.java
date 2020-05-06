package com.luog.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpressData {
	private long id;

	private String orderNo;

	private String receiverName;

	private String commitTime;

	private String goodsName;

	private String goodsImageUrl;

	private String orderStatus;
}
