package com.topideal.dscp.common.util;

import org.apache.commons.lang3.BooleanUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 编码生成工具类
 */
public class CodeGeneratorUtils {

	/**
	 * 生成UUID
	 * @param isStandard 是否标准格式(是否包含 '-')
	 * @return
	 */
	public static String generateUUID(Boolean isStandard) {
		String uuid = UUID.randomUUID().toString();
		if (BooleanUtils.isFalse(isStandard)) {
			uuid = uuid.replaceAll("-", "");
		}
		return uuid;
	}

	/**
	 * 生成随机数字和字母组合
	 *
	 * @param length      长度
	 * @param letterType  字母类型(1.大写; 2.小写; 3.不区分大小写; 4.纯数字)
	 * @return
	 */
	public static String getCharAndNumr(int length, int letterType) {
		StringBuffer valSb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			if (letterType == 4) {
				valSb.append(String.valueOf(random.nextInt(10)));
			} else {
				String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
				if ("char".equalsIgnoreCase(charOrNum)) {
					// 字符串
					int choice = 65; // 默认全大写
					if (letterType == 2) {
						choice = 97; // 全小写
					} else if (letterType == 3) { // 随机生成
						choice = random.nextInt(2) % 2 == 0 ? 65 : 97;  // 取得大写字母还是小写字母
					}
					valSb.append((char) (choice + random.nextInt(26)));

				} else if ("num".equalsIgnoreCase(charOrNum)) {
					// 数字
					valSb.append(String.valueOf(random.nextInt(10)));
				}
			}

		}
		return valSb.toString();
	}

	/**
	 * 生成 文档 / 图片名称key (上传S3用)
	 *
	 * @return 规则: 当前时间 + 6位随机值
	 */
	public static String generateFileKey() {
		String now = DateUtils.format(new Date(), "yyyyMMddHHmmss");
		StringBuilder sb = new StringBuilder(now);
		sb.append(getCharAndNumr(6, 2));
		return sb.toString();
	}

	/**
	 * 生成 手机 / 邮件 验证码
	 *
	 * @return 规则: 6位随机值
	 */
	public static String generateVerifyCode() {
		return getCharAndNumr(6, 4);
	}


}
