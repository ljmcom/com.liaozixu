package com.liaozixu.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.liaozixu.system.Config;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;

/**
 * created by LIAOZIXU EMAIL:ADMIN@LIAOZIXU.COM GITHUB:LIAOZIXU TEL:13927773808
 * WECHAT / WHATSAPP : +852 6646 7775
 */
public class QcloudUtils {
	public static String cosPutObject(String suffix, InputStream input, long contentLength, String contentType) {
		Config config = new Config();
		COSCredentials cred = new BasicCOSCredentials(config.get("qcloudSecretID"),
				config.get("qcloudSecretKey"));
		ClientConfig clientConfig = new ClientConfig(new Region(config.get("qcloudCOSRegion")));
		COSClient cosClient = new COSClient(cred, clientConfig);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(contentLength);
		objectMetadata.setContentType(contentType);
		// 文件命名...时间加随机数
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String key = config.get("qcloudCosSavePath");
		key += simpleDateFormat.format(new Date()) + "/";
		simpleDateFormat = new SimpleDateFormat("HHmmss");
		key += simpleDateFormat.format(new Date()) + "/" + CommonUtils.generateUUID() + suffix;
		String bucketName = config.get("qcloudCOSBucket") + "-" + config.get("qcloudAppid");
		try {
			cosClient.putObject(bucketName, key, input, objectMetadata);
			// 提交上传 关闭
			cosClient.shutdown();
			return key;
		} catch (CosClientException e) {
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}

	}
}
