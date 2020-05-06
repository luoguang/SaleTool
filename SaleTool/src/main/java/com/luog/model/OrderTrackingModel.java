package com.luog.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderTrackingModel {
	private String expressCompanyName;

	private String businessCode;

	private String trackingNo;

	private String importTime;

	private String afterSaleStatus;

	private String afterSaleTimes;
}
