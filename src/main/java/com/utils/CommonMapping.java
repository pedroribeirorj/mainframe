package com.utils;

import com.model.EntryField;
import com.model.LabelField;

public class CommonMapping {
	public static LabelField FIELD_ADMIN_SYSTEM_STATUS = new LabelField(15, 9, "Connect");
	public static LabelField FIELD_NEW_BUSINESS_STATUS = new LabelField(16, 9, "Connect");
	public static EntryField FIELD_COMMAND = new EntryField(2, 14);
	public static EntryField FIELD_COMMAND_AUX = new EntryField(1, 14);
	public static EntryField UNDERWRITER_DECISION_LIFE_CLIENT_FIELD_COMMAND = new EntryField(1, 14);
	public static EntryField UNDERWRITER_DECISION_LIFE_CLIENT = new EntryField(5, 9);
	public static EntryField UNDERWRITER_DECISION_LIFE_INITIALS = new EntryField(6, 43);

	public static EntryField UWWK_INITIALS = new EntryField(5, 11,8);
	public static EntryField UWWK_CLIENT_NUMBER = new EntryField(5, 35);
	public static EntryField UWWK_ENTER = new EntryField(6, 11);
	public static EntryField UWWK_NEW_INITIALS = new EntryField(12, 19);
	public static LabelField MESSAGE_MAINTENANCE_OK = new LabelField(3, 12, "MAINTENANCE COMPLETED - CONTINUE");
	public static LabelField MESSAGE_END_OF_LIST = new LabelField(2, 1, "XS00000015 ** END OF LIST **");
	public static EntryField UWWK_FUNCTION = new EntryField(12, 42);
	public static EntryField UWDE_ENTER = new EntryField(6, 9);
	public static EntryField UWDE_STB2 = new EntryField(7, 45);
	public static EntryField UWDE_R = new EntryField(7, 52);
	public static EntryField UWDE_PAGE = new EntryField(6,71);
	public static EntryField UWDE_MORE_DETAILS = new EntryField(2,15,79);
	

	public static EntryField FIELD_PPRO = new EntryField(18, 32);

	public static EntryField FIELD_COMMAND_APPLICATION_ENTRY = new EntryField(1, 14);
	public static EntryField FIELD_POLICY = new EntryField(5, 10);
	public static EntryField FIELD_ENTER = new EntryField(6, 10);
	public static EntryField FIELD_POLICY_PROCESSING_ENTER = new EntryField(6, 7);
	public static EntryField FIELD_SUPRESS_ISS_SET = new EntryField(18, 32);
	public static EntryField FIELD_OWNER_NAME = new EntryField(5, 41);
	public static LabelField MESSAGE_OK_CC60600001 = new LabelField(3, 1, "CC60600001 ANALYSIS COMPLETE FOR POLICY");
	public static LabelField MESSAGE_OK_CC59500001 = new LabelField(4, 1, "CC59500001 ANALYSIS COMPLETE FOR POLICY");
	public static EntryField OTHER_MESSAGE= new EntryField(2, 1, 79);
	public static EntryField OTHER_MESSAGE2= new EntryField(3, 1, 79);
	public static EntryField OTHER_MESSAGE3= new EntryField(4, 1, 79);
	/// UWDE TABLE
	public static EntryField FIELD_CSHARP_1 = new EntryField(9, 1, 2);
	public static EntryField FIELD_POLICY_1 = new EntryField(9, 5, 9);
	public static EntryField FIELD_PLAN_1 = new EntryField(9, 18, 5);
	public static EntryField FIELD_RS_1 = new EntryField(9, 24, 1);
	public static EntryField FIELD_FACE_AMT_1 = new EntryField(9, 26, 12);
	public static EntryField FIELD_ADB_AMT_1 = new EntryField(9, 39, 12);
	public static EntryField FIELD_AD_M_1 = new EntryField(9, 52, 4);
	public static EntryField FIELD_WP_M_1 = new EntryField(9, 57, 4);
	public static EntryField FIELD_STB1_1 = new EntryField(9, 63, 2);
	public static EntryField FIELD_STB2_1 = new EntryField(9, 68, 3);
	public static EntryField FIELD_COD_DEC_1 = new EntryField(9, 74, 2);
	public static EntryField FIELD_R_1 = new EntryField(9, 78, 2);
	public static EntryField FIELD_SUF = new EntryField(9, 15, 1);

	
	//PLAN
	public static EntryField FIELD_PLAN_CODE = new EntryField(5,12, 5);
	public static EntryField FIELD_SCREEN = new EntryField(5,40, 2);
	public static EntryField FIELD_GROUP_CD_SUF_RENW = new EntryField(13,20, 1);
	public static EntryField FIELD_SUPPLEMENTARY_PLAN = new EntryField(14,62, 1);
	
	public static EntryField FIELD_WRJACTJOB_USER_01 = new EntryField(9,21, 6);
	public static EntryField FIELD_WRJACTJOB_OPT_01 = new EntryField(9,1, 2);
	public static EntryField FIELD_WRJACTJOB_BOTTOM = new EntryField(18,73, 12);
	public static EntryField FIELD_WRJACTJOB_CONFIRM_END_JOB = new EntryField(0,27, 26);
	
	public static EntryField CONFIRMATION =new EntryField(23,1,78);
	public static EntryField  USER = new EntryField(8,21,1113);
}
