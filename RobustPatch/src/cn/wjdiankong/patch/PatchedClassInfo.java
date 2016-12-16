package cn.wjdiankong.patch;

public class PatchedClassInfo {
	
	private String fixClassName;//��Ҫ�޸�������
	private String patchClassName;//��Ҫ�޸�Patch������
	
	public PatchedClassInfo(String fixClassName, String patchClassName){
		this.fixClassName = fixClassName;
		this.patchClassName = patchClassName;
	}
	
	public String getFixClassName(){
		return fixClassName;
	}
	
	public String getPatchClassName(){
		return patchClassName;
	}

}
