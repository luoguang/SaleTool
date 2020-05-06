package com.luog.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpressDetail {
	private String goodsName;

	private String goodsPieces;

	private String goodsImageUrl;

	private String retailPrice;

	private String goodsAmountReceivable;

	private String goodsAmountShipping;

	private String receiverName;

	private String receiverPhone;

	private String address;

	private String orderStatus;

	private String refundStatus;

	private String afterSaleStatus;

	private String invalidStatus;

	private String orderNo;

	private String commitTime;

	private List<OrderTrackingModel> orderTrackings;

	private String isLive;

	private String supplierId;
}
