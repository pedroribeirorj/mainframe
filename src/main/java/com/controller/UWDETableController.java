package com.controller;

import com.model.UWDETable;
import com.keyword.Keyword;
import com.model.EntryField;
import com.model.LabelField;
import com.utils.Data;
import com.utils.ExcelReader;

import ingenium.scenarios.Rules;

import static com.utils.CommonMapping.*;
import static com.keyword.Keyword.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jagacy.util.JagacyException;

import static com.keyword.Keyword.*;

public class UWDETableController {
	UWDETable[] uwde;

	public static int LINES_PAGINATION = 5;

	public UWDETableController(UWDETable[] uwde) {
		this.uwde = uwde;
	}

	public static List<UWDETable> readAllUWDE() throws JagacyException {
		List<UWDETable> list = new ArrayList<UWDETable>();
		for (int i = 1; i <= LINES_PAGINATION; i++) {
			UWDETable uwde = readUWDE(i);
			if (uwde.getCardinal().isBlank())
				return list;
			list.add(uwde);
		}

		return list;
	}

	public static UWDETable readUWDE(int line) throws JagacyException {
		UWDETable uwde = new UWDETable();
		uwde.setCardinal(
				read(FIELD_CSHARP_1.getRow() + (line - 1), FIELD_CSHARP_1.getColumn(), FIELD_CSHARP_1.getLength()));
		uwde.setAd_m(read(FIELD_AD_M_1.getRow() + (line - 1), FIELD_AD_M_1.getColumn(), FIELD_AD_M_1.getLength()));
		uwde.setAdb_amt(
				read(FIELD_ADB_AMT_1.getRow() + line - 1, FIELD_ADB_AMT_1.getColumn(), FIELD_ADB_AMT_1.getLength()));
		uwde.setCo_dec(
				read(FIELD_COD_DEC_1.getRow() + line - 1, FIELD_COD_DEC_1.getColumn(), FIELD_COD_DEC_1.getLength()));
		uwde.setFace_amt(
				read(FIELD_FACE_AMT_1.getRow() + line - 1, FIELD_FACE_AMT_1.getColumn(), FIELD_FACE_AMT_1.getLength()));
		uwde.setPlan(read(FIELD_PLAN_1.getRow() + line - 1, FIELD_PLAN_1.getColumn(), FIELD_PLAN_1.getLength()));
		uwde.setPolicy(
				read(FIELD_POLICY_1.getRow() + line - 1, FIELD_POLICY_1.getColumn(), FIELD_POLICY_1.getLength()));
		uwde.setR(read(FIELD_R_1.getRow() + line - 1, FIELD_R_1.getColumn(), FIELD_R_1.getLength()));
		uwde.setRs(read(FIELD_RS_1.getRow() + line - 1, FIELD_RS_1.getColumn(), FIELD_RS_1.getLength()));
		uwde.setStb1(read(FIELD_STB1_1.getRow() + line - 1, FIELD_STB1_1.getColumn(), FIELD_STB1_1.getLength()));
		uwde.setStb2(read(FIELD_STB2_1.getRow() + line - 1, FIELD_STB2_1.getColumn(), FIELD_STB2_1.getLength()));
		uwde.setSuf(read(FIELD_SUF.getRow() + line - 1, FIELD_SUF.getColumn(), FIELD_SUF.getLength()));
		uwde.setWp_m(read(FIELD_WP_M_1.getRow() + line - 1, FIELD_WP_M_1.getColumn(), FIELD_WP_M_1.getLength()));
		return uwde;
	}

	public static String validateRules(List<UWDETable> regs, UWDETable uwdeTable, Data data)
			throws JagacyException, InterruptedException {
		cleanAndInput(FIELD_PLAN_CODE, uwdeTable.getPlan().toUpperCase());
		inputAndEnter(FIELD_SCREEN, "01");
		String supplementaryPlan = read(FIELD_SUPPLEMENTARY_PLAN);
		String[] grpCD_Renw = read(FIELD_GROUP_CD_SUF_RENW).split("/");
		String grpCD = grpCD_Renw.length == 0 ? "" : grpCD_Renw[0].trim();
		boolean basicPlan = Rules.isBasicPlan(uwdeTable);
		boolean isMQC = Rules.isMQC(grpCD, supplementaryPlan);
		boolean isTempPref = Rules.isTempPref(grpCD, supplementaryPlan);
		if (basicPlan) {
			if (isMQC & !isTempPref) {
				String validation = Rules.validaRegraParaMQCeNaoTemporarioPreferencial(regs, uwdeTable,
						uwdeTable.getStb2(), uwdeTable.getPlan(), data.getClient());
				if (!validation.isEmpty())
					return validation;
			}

			if (isTempPref) {
				String validation = Rules.validaRegraParaBasicoTemporarioPreferencial(regs, uwdeTable, data);
				if (!validation.isEmpty())
					return validation;
			}
		}
		return "";
	}

}
