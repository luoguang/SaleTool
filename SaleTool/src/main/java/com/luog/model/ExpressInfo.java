package com.luog.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpressInfo {
	public ExpressInfo(ExpressDetail expressDetail, OrderTrackingModel orderTrackingModel) {
		this.goodsName = expressDetail.getGoodsName();
		this.goodsPieces = expressDetail.getGoodsPieces();
		this.goodsImageUrl = expressDetail.getGoodsImageUrl();
		this.retailPrice = expressDetail.getRetailPrice();
		this.goodsAmountReceivable = expressDetail.getGoodsAmountReceivable();
		this.goodsAmountShipping = expressDetail.getGoodsAmountShipping();
		this.receiverName = expressDetail.getReceiverName();
		this.receiverPhone = expressDetail.getReceiverPhone();
		this.address = expressDetail.getAddress();
		this.orderStatus = expressDetail.getOrderStatus();
		this.refundStatus = expressDetail.getRefundStatus();
		this.invalidStatus = expressDetail.getInvalidStatus();
		this.orderNo = expressDetail.getOrderNo();
		this.commitTime = expressDetail.getCommitTime();
		this.isLive = expressDetail.getIsLive();
		this.supplierId = expressDetail.getSupplierId();

		this.expressCompanyName = orderTrackingModel.getExpressCompanyName();
		this.businessCode = orderTrackingModel.getBusinessCode();
		this.trackingNo = orderTrackingModel.getTrackingNo();
		this.importTime = orderTrackingModel.getImportTime();
		this.afterSaleStatus = orderTrackingModel.getAfterSaleStatus();
		this.afterSaleTimes = orderTrackingModel.getAfterSaleTimes();
	}

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

	private String isLive;

	private String supplierId;

	private String expressCompanyName;

	private String businessCode;

	private String trackingNo;

	private String importTime;

	private String afterSaleTimes;
}
