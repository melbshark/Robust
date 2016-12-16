package cn.wjdiankong.robust;

import java.util.ArrayList;
import java.util.List;

public class MoneyBean {

	public static String desc() {
		//原始逻辑，返回MoneyBean
		return "MoneyBean";
	}

	public List<String> getInfo(String str, float f, int i, List<String> list) {
		//原始逻辑，返回空List
		return new ArrayList<String>();
	}

	public int getMoneyValue() {
		//原始逻辑，返回10
		return 10;
	}
}