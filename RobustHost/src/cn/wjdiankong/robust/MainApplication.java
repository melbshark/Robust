package cn.wjdiankong.robust;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import cn.wjdiankong.patch.ChangeQuickRedirect;
import cn.wjdiankong.patch.PatchedClassInfo;
import cn.wjdiankong.patch.PatchesInfo;
import cn.wjdiankong.robust.utils.Logger;
import dalvik.system.DexClassLoader;

public class MainApplication extends Application{
	
	private DexClassLoader mLoader;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	protected void attachBaseContext(Context base) {
		Logger.debug("jw", "start load dex");
		boolean isSucc = loadDex(base);
		Logger.debug("jw", "load dex issucc:"+isSucc);
		if(isSucc){
			Logger.debug("jw", "start patch");
			patch();
		}
		super.attachBaseContext(base);
	}
	
	@SuppressLint({ "SdCardPath"})
	private boolean loadDex(Context ctx){
		File dexFile = new File("/sdcard/patch.dex");
		if(!dexFile.exists()){
			Logger.debug("jw", "patch.dex is not exist!");
			return false;
		}
		try{
			File odexDir = new File(ctx.getFilesDir()+File.separator+"odex"+File.separator);
			if(!odexDir.exists()){
				odexDir.mkdirs();
			}
			mLoader = new DexClassLoader(dexFile.getAbsolutePath(), odexDir.getAbsolutePath(), null, ctx.getClassLoader());
			Log.i("jw", "mloader;"+mLoader);
			return true;
		}catch(Throwable e){
			Logger.debug("jw", "load patch error:"+Log.getStackTraceString(e));
		}
		return false;
	}
	
	@SuppressLint("NewApi")
	private void patch(){
		try{
			//�ȵõ��޸����е�PatchesInfoImpl��
			Class<?> patchInfoClazz = mLoader.loadClass("cn.wjdiankong.patchimpl.PatchesInfoImpl");
			PatchesInfo patchInfo = (PatchesInfo)patchInfoClazz.newInstance();
			//��ȡ�޸��������д��޸�����Ϣ
			List<PatchedClassInfo> infoList = patchInfo.getPatchedClassesInfo();
			for(PatchedClassInfo info : infoList){
				//���������޸������
				ChangeQuickRedirect redirectObj = (ChangeQuickRedirect)mLoader.loadClass(
						info.getPatchClassName()).newInstance();
				//��ȡ���޸���������
				Class<?> fixClass = mLoader.loadClass(info.getFixClassName());
				//���޸���������õ����޸������changeQuickRedirect������
				Field redirectF = fixClass.getField("changeQuickRedirect");
				redirectF.set(null, redirectObj);
			}
			Logger.debug("jw", "patch succ");
		}catch(Throwable e){
			Logger.debug("jw", "patch error:"+Log.getStackTraceString(e));
		}
	}
	
}
