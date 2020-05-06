package com.luog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luog.model.ExpressData;
import com.luog.model.ExpressDetail;
import com.luog.model.OrderTrackingModel;
import com.luog.model.ResponseModel;
import com.luog.util.ExcelUtil;
import com.luog.util.HttpInvoke;
import com.luog.util.KdniaoTrackQueryAPI;
import com.luog.util.StringUtil;

/**
 * Hello world!
 *
 */
public class App {
	private final static String LIST_CU_URL_FORMAT = "https://api.chenyistyle.com/order/listCu?keyword=%s&peopleId=%s&lastId=&tmst=%s";

	private final static String GET_CU_URL_FORMAT = "https://api.chenyistyle.com/order/getCu?orderNo=%s&peopleId=%s&tmst=%s";

	private final static String PEOPLE_ID = "12529934";

	private final static String BASE_PATH = System.getProperty("user.dir") + File.separatorChar;

	private KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();

	private static final Map<String, String> MAP = new HashMap<>();

	static {
		MAP.put("顺丰", "SF");
		MAP.put("百世汇通", "HTKY");
		MAP.put("中通", "ZTO");
		MAP.put("申通", "STO");
		MAP.put("圆通", "YTO");
		MAP.put("韵达", "YD");
		MAP.put("邮政", "YZPY");
		MAP.put("EMS", "EMS");
		MAP.put("天天", "HHTT");
		MAP.put("京东", "JD");
		MAP.put("优速", "UC");
		MAP.put("德邦", "DBL");
		MAP.put("宅急送", "ZJS");
	}

	public static void main(String[] args) {

		try {
			String path = BASE_PATH + "input" + File.separatorChar + "水果店快递信息模板.xlsx";
			List<List<String>> list = ExcelUtil.readExcel(path, 0, 1);
			if (CollectionUtils.isEmpty(list)) {
				return;
			}

			List<List<String>> result = new ArrayList<>();
			App app = new App();
			for (List<String> rowList : list) {
				if (CollectionUtils.isEmpty(rowList)) {
					continue;
				}
				String name = rowList.get(0);
				if (StringUtils.isEmpty(name)) {
					continue;
				}

				result.addAll(app.getExpressInfo(name.trim()));
			}

			String date = getDateToString(System.currentTimeMillis());
			date = date.replace(":", "").replace("-", "").replace(" ", "");
			String outputPath = BASE_PATH + "output" + File.separatorChar + "水果店快递信息-" + date + ".xlsx";

			String[] titles = new String[] { "商品名称", "数量", "单价", "总金额", "运费", "收件人", "电话", "地址", "是否发货", "单号", "日期",
					"快递公司", "快递单号" };
			ExcelUtil.writeExcel(result, titles, outputPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<List<String>> getExpressInfo(String name) throws Exception {
		List<List<String>> result = new ArrayList<>();

		Gson gson = new Gson();
		String encodeName = StringUtil.encode(name);
		if (StringUtils.isEmpty(encodeName)) {
			return result;
		}
		String listCuUrl = String.format(LIST_CU_URL_FORMAT, encodeName, PEOPLE_ID, System.currentTimeMillis());
		String listCuResponse = HttpInvoke.doGet(listCuUrl);
		if (StringUtils.isEmpty(listCuResponse)) {
			return result;
		}

		ResponseModel listCuModel = gson.fromJson(listCuResponse, ResponseModel.class);
		Object obj = listCuModel.getData();
		List<ExpressData> expressDataList = gson.fromJson(gson.toJson(obj), new TypeToken<List<ExpressData>>() {
		}.getType());
		if (CollectionUtils.isEmpty(expressDataList)) {
			return result;
		}
		for (ExpressData expressData : expressDataList) {
			String orderNo = expressData.getOrderNo();
			if (StringUtils.isEmpty(orderNo)) {
				continue;
			}

			String getCuResponse = HttpInvoke
					.doGet(String.format(GET_CU_URL_FORMAT, orderNo, PEOPLE_ID, System.currentTimeMillis()));
			if (StringUtils.isEmpty(getCuResponse)) {
				continue;
			}

			ResponseModel getCuModel = gson.fromJson(getCuResponse, ResponseModel.class);
			Object obj1 = getCuModel.getData();
			ExpressDetail expressDetail = gson.fromJson(gson.toJson(obj1), ExpressDetail.class);

			List<OrderTrackingModel> orderTrackingList = expressDetail.getOrderTrackings();
			if (CollectionUtils.isEmpty(orderTrackingList)) {
				continue;
			}

			for (OrderTrackingModel orderTracking : orderTrackingList) {
				List<String> expressInfo = new ArrayList<>();
				expressInfo.add(expressDetail.getGoodsName());
				expressInfo.add(expressDetail.getGoodsPieces());
				// expressInfo.add(expressDetail.getGoodsImageUrl());
				expressInfo.add(expressDetail.getRetailPrice());
				expressInfo.add(expressDetail.getGoodsAmountReceivable());
				expressInfo.add(expressDetail.getGoodsAmountShipping());
				expressInfo.add(expressDetail.getReceiverName());
				expressInfo.add(expressDetail.getReceiverPhone());
				expressInfo.add(expressDetail.getAddress());
				expressInfo.add(expressDetail.getOrderStatus());
				// expressInfo.add(expressDetail.getRefundStatus());
				// expressInfo.add(expressDetail.getInvalidStatus());
				expressInfo.add(expressDetail.getOrderNo());
				expressInfo.add(expressDetail.getCommitTime());
				// expressInfo.add(expressDetail.getIsLive());
				// expressInfo.add(expressDetail.getSupplierId());
				expressInfo.add(orderTracking.getExpressCompanyName());
				// expressInfo.add(orderTracking.getBusinessCode());
				expressInfo.add(orderTracking.getTrackingNo());
				// expressInfo.add(orderTracking.getImportTime());
				// expressInfo.add(orderTracking.getAfterSaleStatus());
				// expressInfo.add(orderTracking.getAfterSaleTimes());

				String orderTraces = api.getOrderTracesByJson(
						MAP.getOrDefault(orderTracking.getExpressCompanyName(), ""), orderTracking.getTrackingNo());
				expressInfo.add(orderTraces);

				result.add(expressInfo);
			}
		}
		return result;
	}

	public static String getDateToString(long time) {
		Date d = new Date(time);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(d);
	}
}
