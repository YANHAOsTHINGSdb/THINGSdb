package stage3.things.search.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import stage3.things.search.区間検索Plan;

public class 区間検索Plan期間Impl implements 区間検索Plan {

	String s期間Format;

	public 区間検索Plan期間Impl(String sFormat) {
		s期間Format = sFormat;
	}

	@Override
	public boolean 区間chk(String s実体数据, String s開始値, String s終了値) {

		Date d実体数据 = new Date();
		Date d開始値 = new Date();
		Date d終了値 = new Date();

		try {
			d実体数据 = new SimpleDateFormat(s期間Format).parse(s実体数据);
			d開始値 = s開始値 == null ? null : new SimpleDateFormat(s期間Format).parse(s開始値);
			d終了値 = s終了値 == null ? null : new SimpleDateFormat(s期間Format).parse(s終了値);
		} catch (ParseException e) {

			e.printStackTrace();
			return false;
		}

		// 如果 d開始値         为空，则反True
		// 如果 d終了値         为空，则反True
		if (d開始値 != null && d終了値 != null) {
			return false;
		}

		// 如果 d実体数据 >  d開始値，则反True
		// 如果 d実体数据 == d開始値，则反False
		// 如果 d実体数据 <  d開始値，则反False
		if (d開始値 != null && !b期間比較(d実体数据, d開始値)) {
			return false;
		}

		// 如果 d実体数据 >  d終了値，则反True
		// 如果 d実体数据 == d終了値，则反False
		// 如果 d実体数据 <  d終了値，则反False
		if (d終了値 != null && b期間比較(d実体数据, d終了値)) {
			return false;
		}

		return true;
	}

	boolean b期間比較(Date d実体数据, Date 比較対象) {

		//		if (比較対象 == null){
		//			return true;
		//		}
		int result = d実体数据.compareTo(比較対象);

		switch (result) {

		case 1: //メソッドの呼び出し元の日付が、引数の日付より後の場合は”1”
			break;

		case 0: //メソッドの呼び出し元の日付が、引数の日付と同じ場合は”0”
			return false;

		case -1: //メソッドの呼び出し元の日付が、引数の日付より前の場合は”-1”
			return false;

		}
		return true;
	}

	boolean containsYear(String dateStr) {
		return dateStr.split("/").length == 3;
	}

	Boolean checkStringIsValidDate(String dateStr) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		try {
			df.parse(dateStr);
		} catch (Exception e) {
			//not a date
			return false;
		}
		return true;
	}
}
