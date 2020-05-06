package com.luog.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackExpress {
	private String message;

	private String nu;

	private String ischeck;

	private String condition;

	private String com;

	private String status;

	private String state;

	private List<TrackExpressData> data;
}
