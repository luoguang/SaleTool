package com.luog.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.luog.model.TrackExpress;
import com.luog.model.TrackExpressData;

public class ExpressTrackUtil {
	private final static String EXPRESS_TRACK_URL = "https://www.kuaidi100.com/query?type=%s&postid=%s&temp=0.5527547774480053&phone=";

	private final static Map<String, String> COM_MAP = new HashMap<>();

	static {
		COM_MAP.put("圆通", "yuantong");
		COM_MAP.put("韵达", "yunda");
	}

	public static void main(String path[]) throws Exception {
		String aa = trackExpress("韵达", "3103500411786");

		Gson gson = new Gson();
		TrackExpress trackExpress = gson.fromJson(aa, TrackExpress.class);
		List<TrackExpressData> dataList = trackExpress.getData();
		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}

		for (TrackExpressData data : dataList) {
			String context = data.getContext();
		}

		System.out.println(aa);
	}

	public static String trackExpress(String com, String nu) {
		if (StringUtils.isEmpty(com) || StringUtils.isEmpty(nu)) {
			return "";
		}

		String parsedCom = COM_MAP.getOrDefault(com, com);
		String response = HttpInvoke.doGet(String.format(EXPRESS_TRACK_URL, parsedCom, nu));

		if (StringUtils.isEmpty(response)) {
			return "";
		}

		return response;
	}

}
