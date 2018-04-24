package com.hxwl.newwlf.home.home.follow;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.text.TextUtils;

/**
 * ������ת��Ϊƴ��
 * 
 * @author Administrator
 * 
 */
public class PinYinUtils {

	public static String getPinYin(String text) {
		if (TextUtils.isEmpty(text))
			return null;

		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		String pinyin = "";
		// 1.��Ϊͬһʱ��PinYin4jֻ�ܽ�һ������ת��Ϊƴ��
		char[] textArr = text.toCharArray();

		for (int i = 0; i < textArr.length; i++) {

			// ~�� �� ==һ���������ֽ�byte (-127 ----127)
			// 2.�������֮�䲻�����ո�
			if (!Character.isWhitespace(textArr[i])) {

				if (textArr[i] > 127) {
					//�п����Ǻ���

					try {
						String[] strArr = PinyinHelper
								.toHanyuPinyinStringArray(textArr[i], format);

						pinyin += strArr[0];
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				} else {
					// �϶����Ǻ��֣�һ���Ǽ�������ĳһ���ַ�
					pinyin += textArr[i];
				}

			} else {
				pinyin += textArr[i];
			}

		}
		return pinyin;

	}
}
