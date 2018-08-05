package com.liaozixu.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CommonUtils {

	/***
	 * 判断数组里有没有某某某
	 * 
	 * @param v
	 *            字符串
	 * @param data
	 *            数组
	 * @return
	 */
	public static boolean isArrayString(String v, String[] data) {
		if (data.length != 0) {
			for (String rV : data) {
				if (rV.equals(v)) {
					return true;
				}
			}
		}
		return false;
	}

	/***
	 * 通过uuid生成随机数
	 * 
	 * @return 随机数[32位数]
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
	}

	/***
	 * 获得当前时间戳
	 * 
	 * @return s
	 */
	public static long getCurrentTimestamp() {
		return System.currentTimeMillis() / 1000;
	}

	/***
	 * 获得当前时间戳
	 * 
	 * @return 毫秒
	 */
	public static long getCurrentTimestampMs() {
		return System.currentTimeMillis();
	}

	/***
	 * 判断是否整数
	 * 
	 * @param num
	 *            需要判断的值[String]
	 * @return 结果
	 */
	public static boolean checkRoundNum(String num) {
		try {
			int numInt = Integer.valueOf(num);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/***
	 * 判断是否整数
	 * 
	 * @param num
	 *            需要判断的值[Object]
	 * @return 结果
	 */
	public static boolean checkRoundNum(Object num) {
		try {
			int numInt = Integer.valueOf(num.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/***
	 * 判断是否整数
	 * 
	 * @param num
	 *            需要判断的值[int]
	 * @return 结果
	 */
	public static boolean checkRoundNum(int num) {
		return checkRoundNum(String.valueOf(num));
	}

	/***
	 * 文件流转字符串
	 * 
	 * @param i
	 *            流
	 * @return String
	 */
	public static String fileInputStreamToString(FileInputStream i) {
		if (i == null) {
			return null;
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(i, "UTF-8"));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/***
	 * md5编码
	 * 
	 * @param string
	 *            需编码内容
	 * @return md5 String
	 */
	public static String md5Encode(String string) {
		byte[] hash = new byte[0];
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (Exception e) {
			return null;
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) {
				hex.append("0");
			}
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	/***
	 *  微信支付md5签名
	 * 
	 * @param map
	 * @param keyValue
	 * @return
	 */

	public static String wechatSign(HashMap<String, String> map, String keyValue) {
		Object[] array = map.keySet().toArray();
		Arrays.sort(array);
		StringBuilder sb = new StringBuilder();
		for (Object mapKey : array) {
			sb.append(mapKey).append("=").append(map.get(mapKey.toString())).append("&");
		}
		String sortArray = sb.append("key").append("=").append(keyValue).toString();
		return md5Encode(sortArray).toUpperCase();
	}

	/**
	 * 生成sha1
	 */
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
	/***
	 * 卡券签名
	 * @param params
	 * @return
	 */
	public static String makeCardExtSign(Map<String, String> params) {
		List<Map.Entry<String, String>> userCardStatusTemp = new ArrayList<>(params.entrySet());
        userCardStatusTemp.sort(Comparator.comparing(Map.Entry::getValue));
        StringBuilder addCardSignature = new StringBuilder();
        for (Map.Entry<String, String> mapping : userCardStatusTemp) {
            addCardSignature.append(mapping.getValue());
        }
        return getSha1(addCardSignature.toString());
	}
	/***
	 * 微信支付客户端jsapi签名
	 * @param map
	 * @param key
	 * @return
	 */
	public static HashMap<String, String> wechatPayJsapiSign(HashMap<String, String> map, String key,boolean isMiniapp){
		HashMap<String, String> signMap = new HashMap<>();
		if(isMiniapp) {
			if(map.get("sub_appid") != null) {
				signMap.put("appId", map.get("sub_appid"));
			}
		}else {
			signMap.put("appId", map.get("appid"));
		}
		signMap.put("timeStamp", String.valueOf(CommonUtils.getCurrentTimestamp()));
		signMap.put("nonceStr", CommonUtils.generateUUID());
		signMap.put("package", "prepay_id="+map.get("prepay_id"));
		signMap.put("signType", "MD5");
		signMap.put("paySign", wechatSign(signMap, key));
		return signMap;
	}
	
	/**
	 * 生成订单号
	 * @return
	 */
	public static String getOutTradeNo() {
		return (new Date().getTime() + CommonUtils.generateUUID()).substring(0,32);
	}
	
}
