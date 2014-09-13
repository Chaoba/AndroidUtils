package com.app.common.util;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

/**
 * Spannable�����࣬�����������ֵ�ǰ��ɫ������ɫ��Typeface�����塢б�塢�ֺš������ӡ�ɾ���ߡ��»��ߡ����±��
 * 
 * */
public class SpannableUtils {
	private SpannableUtils( ){
		
	}
	
	/**
	 * �ı��ַ�����ĳһ�����ֵ��ֺ�
	 * setTextSize("",24,0,2) = null;
	 * setTextSize(null,24,0,2) = null;
	 * setTextSize("abc",-2,0,2) = null;
	 * setTextSize("abc",24,0,4) = null;
	 * setTextSize("abc",24,-2,2) = null;
	 * setTextSize("abc",24,0,2) = normal string
	 * */
	public static SpannableString setTextSize( String content, int startIndex, int endIndex, int fontSize ){
		if( TextUtils.isEmpty( content ) || fontSize <= 0 || startIndex >= endIndex || startIndex < 0 || endIndex >= content.length( ) ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan( new AbsoluteSizeSpan( fontSize ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
		
		return spannableString;
	}
	
	public static SpannableString setTextSub( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan( new SubscriptSpan( ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
		
		return spannableString;
	}
	
	public static SpannableString setTextSuper( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan( new SuperscriptSpan( ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
		
		return spannableString;
	}
	
	public static SpannableString setTextStrikethrough( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new StrikethroughSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	public static SpannableString setTextUnderline( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	public static SpannableString setTextBold( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	public static SpannableString setTextItalic( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	public static SpannableString setTextBoldItalic( String content, int startIndex, int endIndex ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	public static SpannableString setTextForeground( String content, int startIndex, int endIndex, int foregroundColor ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new ForegroundColorSpan( foregroundColor ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	public static SpannableString setTextBackground( String content, int startIndex, int endIndex, int backgroundColor ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new BackgroundColorSpan( backgroundColor ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	/**
	 * �����ı��ĳ�����
	 * @param content ��Ҫ������ı�
	 * @param startIndex
	 * @param endIndex �������ı�����Ҫ�����ִ��Ŀ�ʼ�ͽ�������
	 * @param url �ı���Ӧ�����ӵ�ַ����Ҫע���ʽ��
	 * ��1���绰��"tel:"��ͷ������"tel:02355692427"
	 * ��2���ʼ���"mailto:"��ͷ������"mailto:zmywly8866@gmail.com"
	 * ��3��������"sms:"��ͷ������"sms:02355692427"
	 * ��4��������"mms:"��ͷ������"mms:02355692427"
	 * ��5����ͼ��"geo:"��ͷ������"geo:68.426537,68.123456"
	 * ��6��������"http://"��ͷ������"http://www.google.com"
	 * */
	public static SpannableString setTextURL( String content, int startIndex, int endIndex, String url ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new URLSpan( url ), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
	
	public static SpannableString setTextImg( String content, int startIndex, int endIndex, Drawable drawable ){
		if( TextUtils.isEmpty( content ) || startIndex < 0 || endIndex >= content.length( ) || startIndex >= endIndex ){
			return null;
		}
		
		SpannableString spannableString = new SpannableString( content );
		spannableString.setSpan(new ImageSpan(drawable), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return spannableString;
	}
}
