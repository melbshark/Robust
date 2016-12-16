package cn.wjdiankong.patchimpl;

import android.text.TextUtils;
import cn.wjdiankong.patch.ChangeQuickRedirect;

public class MoneyBeanStatePatch implements ChangeQuickRedirect {
    @Override
    public Object accessDispatch(String methodSignature, Object[] paramArrayOfObject) {
    	//����ͨ���жϷ���ǩ�����з������滻
    	//methodSignature��ʽΪ��classname:methodname:isstatic
        String[] signature = methodSignature.split(":");
        
        //���¾�ʽ��Ҫ�����޸������ľ����޸�����
        if(TextUtils.equals(signature[1], "getMoneyValue")) {
            return 10000;
        }
        if(TextUtils.equals(signature[1], "desc")){
        	return "Patch Desc";
        }
        return null;
    }

    @Override
    public boolean isSupport(String methodSignature, Object[] paramArrayOfObject) {
    	//������Ҫ��У�鷽������ȷ��
    	//methodSignature��ʽΪ��classname:methodname:isstatic
        String[] signature = methodSignature.split(":");
        
        //���¾�����Ҫ�����޸��ķ�������
        if(TextUtils.equals(signature[1], "getMoneyValue")) {
            return true;
        }
        if(TextUtils.equals(signature[1], "desc")){
        	return true;
        }
        return false;
    }
}
