package com.hxwl.newwlf.emoji;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmojiUtil {
    private static ArrayList<Emoji> emojiList;

    public static ArrayList<Emoji> getEmojiList() {
        if (emojiList == null) {
            emojiList = generateEmojis();
        }
        return emojiList;
    }

    private static ArrayList<Emoji> generateEmojis() {
        ArrayList<Emoji> list = new ArrayList<>();
        for (int i = 0; i < EmojiResArray.length; i++) {
            Emoji emoji = new Emoji();
            emoji.setImageUri(EmojiResArray[i]);
            emoji.setContent(EmojiTextArray[i]);
            list.add(emoji);
        }
        return list;
    }


    public static final int[] EmojiResArray = {
            R.drawable.f1,
            R.drawable.f2,
            R.drawable.f3,
            R.drawable.f4,
            R.drawable.f5,
            R.drawable.f6,
            R.drawable.f7,
            R.drawable.f8,
            R.drawable.f9,
            R.drawable.f10,
            R.drawable.f11,
            R.drawable.f12,
            R.drawable.f13,
            R.drawable.f14,
            R.drawable.f15,
            R.drawable.f16,
            R.drawable.f17,
            R.drawable.f18,
            R.drawable.f19,
            R.drawable.f20,
            R.drawable.f21,
            R.drawable.f22,
            R.drawable.f23,
            R.drawable.f24,
            R.drawable.f25,
            R.drawable.f26,
            R.drawable.f27,
            R.drawable.f28,
            R.drawable.f29,
            R.drawable.f30,
            R.drawable.f31,
            R.drawable.f32,
            R.drawable.f33,
            R.drawable.f34,
            R.drawable.f35,
            R.drawable.f36,
            R.drawable.f37,
            R.drawable.f38,
            R.drawable.f39,
            R.drawable.f40,
            R.drawable.f41,
            R.drawable.f42,
            R.drawable.f43,
            R.drawable.f44,
            R.drawable.f45,
            R.drawable.f46,
            R.drawable.f47,
            R.drawable.f48,
            R.drawable.f49,
            R.drawable.f50,
            R.drawable.f51,
            R.drawable.f52,
            R.drawable.f53,
            R.drawable.f54,
            R.drawable.f55,
            R.drawable.f56,
            R.drawable.f57,
            R.drawable.f58,
            R.drawable.f59,
            R.drawable.f60,
            R.drawable.f61,
            R.drawable.f62,
            R.drawable.f63,
            R.drawable.f64,
            R.drawable.f65,
            R.drawable.f66,
            R.drawable.f67,
            R.drawable.f68,
            R.drawable.f69,
            R.drawable.f70,
            R.drawable.f71,
            R.drawable.f72,
    };

    public static final String[] EmojiTextArray = {
            "[呲牙]",
            "[调皮]",
            "[汗]",
            "[偷笑]",
            "[拜拜]",
            "[打你]",
            "[擦汗]",
            "[猪头]",
            "[玫瑰]",
            "[流泪]",
            "[快哭了]",
            "[嘘]",
            "[酷]",
            "[抓狂]",
            "[委屈]",
            "[屎]",
            "[炸弹]",
            "[菜刀]",
            "[可爱]",
            "[色]",
            "[害羞]",
            "[得意]",
            "[吐]",
            "[微笑]",
            "[发火]",
            "[尴尬]",
            "[惊恐]",
            "[冷汗]",
            "[心]",
            "[嘴唇]",
            "[白眼]",
            "[傲慢]",
            "[难过]",
            "[惊讶]",
            "[疑问]",
            "[睡]",
            "[亲亲]",
            "[憨笑]",
            "[爱情]",
            "[衰]",
            "[撇嘴]",
            "[奸笑]",
            "[奋斗]",
            "[发呆]",
            "[右哼哼]",
            "[抱抱]",
            "[坏笑]",
            "[企鹅亲]",
            "[鄙视]",
            "[晕]",
            "[大兵]",
            "[拜托]",
            "[强]",
            "[垃圾]",
            "[握手]",
            "[胜利]",
            "[抱拳]",
            "[枯萎]",
            "[米饭]",
            "[蛋糕]",
            "[西瓜]",
            "[啤酒]",
            "[瓢虫]",
            "[勾引]",
            "[互粉]",
            "[哦了]",
            "[手势]",
            "[咖啡]",
            "[月亮]",
            "[匕首]",
            "[发抖]",
            "[菜]",
            "[拳头]",
    };

    static {
        emojiList = generateEmojis();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static void handlerEmojiText(TextView comment, String content, Context context) throws IOException {
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "\\[(\\S+?)\\]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        Iterator<Emoji> iterator;
        Emoji emoji = null;
        while (m.find()) {
            iterator = emojiList.iterator();
            String tempText = m.group();
            while (iterator.hasNext()) {
                emoji = iterator.next();
                if (tempText.equals(emoji.getContent())) {
                    //转换为Span并设置Span的大小
                    sb.setSpan(new ImageSpan(context, decodeSampledBitmapFromResource(context.getResources(), emoji.getImageUri()
                                    , dip2px(context, 18), dip2px(context, 18))),
                            m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                }
            }
        }
        comment.setText(sb);
    }
}
