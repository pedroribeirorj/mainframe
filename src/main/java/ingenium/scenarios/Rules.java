package ingenium.scenarios;

import static com.keyword.Keyword.cleanAndInput;
import static com.keyword.Keyword.input;
import static com.keyword.Keyword.inputAndEnter;
import static com.keyword.Keyword.read;
import static com.utils.CommonMapping.FIELD_GROUP_CD_SUF_RENW;
import static com.utils.CommonMapping.FIELD_PLAN_CODE;
import static com.utils.CommonMapping.FIELD_SCREEN;
import static com.utils.CommonMapping.FIELD_SUPPLEMENTARY_PLAN;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


import com.controller.ExcelReaderController;
import com.jagacy.util.JagacyException;
import com.model.UWDETable;
import com.utils.Data;
import com.utils.ExcelReader;

public class Rules {
	public static int RULE_MQC_NAO_TEMP_PREF = 1;
	public static int RULE_BASICO_TEMP_PREF = 2;
	public static ExcelReaderController erc;
	static final Logger logger = Logger.getLogger(Rules.class.getName());


	public static ExcelReaderController getERC() {
		if (erc == null)
			erc = new ExcelReaderController(ExcelReaderController.TAB_RULES);
		return erc;
	}

	public static boolean isBasicPlan(UWDETable uwdeTable) {
		return uwdeTable.getCardinal().equals("01");
	}

	public static boolean isCO(UWDETable uwdeTable) {
		return !isBasicPlan(uwdeTable);
	}

	public static boolean isMQC(String grpCD, String supplementaryPlan) {

		return !grpCD.equalsIgnoreCase("4") & !grpCD.isEmpty() & supplementaryPlan.equalsIgnoreCase("N");
	}

	public static boolean isPap(String supplementaryPlan) {
		return supplementaryPlan.equalsIgnoreCase("I");
	}

	public static boolean isAF(String supplementaryPlan) {
		// TODO Auto-generated method stub
		return supplementaryPlan.equalsIgnoreCase("F");
	}

	public static boolean isTempPref(String grpCD, String supplementaryPlan) {
		return supplementaryPlan.equalsIgnoreCase("N") & grpCD.equals("4");
	}

	private static boolean premioAplicadoIgualAoEsperado(String premioAplicado, String premioEsperado) {
		return premioAplicado.equalsIgnoreCase(premioEsperado);
	}

	private static boolean classeDeAjusteDaPlanilhaContemRegra(String classeDeAjuste, String premioAplicado) {
		return classeDeAjuste.equalsIgnoreCase(premioAplicado);
	}

	/*
	 * se a cobertura básica for temporaria preferencial então se suas coberturas
	 * opcionais forem MQC ou assistencia funeral o premio aplicado para essas
	 * coberturas deve ser o definido na planilha (mesma linha do premio)
	 * 
	 */

	public static String validaRegraParaBasicoTemporarioPreferencial(List<UWDETable> regs, UWDETable uwde, Data data)
			throws JagacyException, InterruptedException {
		int linha = encontraLinhaNaPlanilha(data.getUwde_stb2(), RULE_BASICO_TEMP_PREF);
		if (linha == -1)
			return "";

		String premioAplicado = getERC().readPremioAplicado();
		String premioAplicadoMQC_AF = getERC().readAjustePremioAplicadoMQC_AF();
		String premioPAPCoberturaAdc = getERC().readPAPCoberturaAdicional();
		String supplementaryPlan;

		for (Iterator iterator = regs.iterator(); iterator.hasNext();) {
			UWDETable uwdeTable = (UWDETable) iterator.next();
			cleanAndInput(FIELD_PLAN_CODE, uwdeTable.getPlan().toUpperCase());
			inputAndEnter(FIELD_SCREEN, "01");
			String[] grpCD_Renw = read(FIELD_GROUP_CD_SUF_RENW).split("/");
			String grpCD = grpCD_Renw.length == 0 ? "" : grpCD_Renw[0].trim();
			supplementaryPlan = read(FIELD_SUPPLEMENTARY_PLAN);

			if (isMQC(grpCD, supplementaryPlan) || isAF(supplementaryPlan)) {
				String msg = validateAssistenciaFuneralOrCoberturaMQC(uwdeTable.getStb2(), uwde.getPlan(),
						premioAplicadoMQC_AF, premioAplicado, data.getClient(), data.getPolicy());
				if (!msg.isEmpty())
					return msg;
			}

			if (isPap(supplementaryPlan)) {
				String msg = validatePAP(uwdeTable.getStb2(), uwde.getPlan(), premioPAPCoberturaAdc, uwde.getStb2(),
						data.getClient(), data.getPolicy());
				if (!msg.isEmpty())
					return msg;
			}
		}
		return "";
	}

	public static String validaRegraParaMQCeNaoTemporarioPreferencial(List<UWDETable> regs, UWDETable uwde,
			String classeDeAjuste, String plano, String client) throws JagacyException, InterruptedException {
		// encontrar linha da planilha referente ao premio aplicado
		int linha = encontraLinhaNaPlanilha(classeDeAjuste, RULE_MQC_NAO_TEMP_PREF);
		if (linha == -1)
			return "";

		String premioAplicado = getERC().readPremioAplicado(linha);
		String premioPAPCoberturaAdc = getERC().readPAPCoberturaAdicional(linha);
		String supplementaryPlan;

		// validar se em stb2 das regs entraram o valor definido pela planilha

		for (Iterator iterator = regs.iterator(); iterator.hasNext();) {
			UWDETable uwdeTable = (UWDETable) iterator.next();
			cleanAndInput(FIELD_PLAN_CODE, uwdeTable.getPlan().toUpperCase());
			inputAndEnter(FIELD_SCREEN, "01");
			supplementaryPlan = read(FIELD_SUPPLEMENTARY_PLAN);
			if (isPap(supplementaryPlan)) {
				String stb2 = uwdeTable.getStb2();

				if (!(isNoneOrEmpty(stb2) && isNoneOrEmpty(premioPAPCoberturaAdc))) {
					if (!stb2.equalsIgnoreCase(premioPAPCoberturaAdc)) {
						String basicData = "\n Apólice: " + uwde.getPolicy() + "\n Cliente: " + client + "\n";
						String msg = "ERRO - Cobertura básica MQC = " + plano + ", com prêmio = " + premioAplicado
								+ " , não aplicou o premio \"" + premioPAPCoberturaAdc + "\", na cobertura opcional =  "
								+ uwdeTable.getPlan() + ", mesmo ela sendo PAP. O prêmio a ela atribuído foi: \""
								+ uwdeTable.getStb2() + "\"\n";
						msg = basicData + msg;
						logger.error(msg);
						return msg;
					}
				}
			}
		}
		return "";
	}

	private static boolean isNoneOrEmpty(String value) {
		return value.equalsIgnoreCase("Nenhum") || value.isEmpty();
	}

	private static int encontraLinhaNaPlanilha(String classeDeAjuste, int rule) {
		String premioAplicado = getERC().readPremioAplicado();
		while (!premioAplicado.isEmpty()) {
			if (!classeDeAjusteDaPlanilhaContemRegra(classeDeAjuste, premioAplicado) & getERC().readRule() == rule) {
				getERC().addRow();
			} else {
				premioAplicado = getERC().readPremioAplicado();
				break;
			}
			premioAplicado = getERC().readPremioAplicado();
		}
		if (premioAplicado.isBlank())
			return -1;
		return getERC().getRow();
	}

	private static String validatePAP(String classeDeAjusteAplicadaACoberturaAdicional, String plano,
			String premioPAPCoberturaAdc, String premioAplicado, String client, String policy)
			throws JagacyException, InterruptedException {
		if (!(isNoneOrEmpty(classeDeAjusteAplicadaACoberturaAdicional) && isNoneOrEmpty(premioPAPCoberturaAdc))) {
			if (!classeDeAjusteAplicadaACoberturaAdicional.equalsIgnoreCase(premioPAPCoberturaAdc)) {
				String basicData = "Apólice: " + policy + "\n Cliente: " + client + "\n";

				String msg = "ERRO - Cobertura básica Temporário Preferencial \"" + plano + "\", com prêmio \""
						+ premioAplicado + "\" não aplicou as coberturas opcionais PAP \"" + premioPAPCoberturaAdc
						+ "\", mas sim \"" + classeDeAjusteAplicadaACoberturaAdicional + "\"";
				msg = basicData + msg;
				logger.error(msg);
				return msg;
			}
		}
		return "";
	}

	private static String validateAssistenciaFuneralOrCoberturaMQC(String classeDeAjusteAplicadaACoberturaAdicional,
			String plano, String premioAplicadoMQC_AF, String premioAplicado, String client, String policy)
			throws JagacyException, InterruptedException {
		if (!(isNoneOrEmpty(classeDeAjusteAplicadaACoberturaAdicional) && isNoneOrEmpty(premioAplicadoMQC_AF))) {
			if (!classeDeAjusteAplicadaACoberturaAdicional.equalsIgnoreCase(premioAplicadoMQC_AF)) {
				String basicData = "Apólice: " + policy + "\n Cliente: " + client + "\n";

				String msg = "ERRO - Cobertura básica Temporário preferencial \"" + plano + "\", com prêmio \""
						+ premioAplicado + " não aplicou as coberturas opcionais MQC ou Assistência Funeral o premio \""
						+ premioAplicadoMQC_AF + "\", mas sim \"" + classeDeAjusteAplicadaACoberturaAdicional + "\"";
				msg = basicData + msg;
				logger.error(msg);
				return msg;
			}
		}
		return "";
	}

	public static List<UWDETable> getCoberturaAdicional(List<UWDETable> regs, UWDETable coberturaBasica) {
		// cleanRegs => lista de todas as coberturas opcionais da coberturaBasica
		// informada
		List<UWDETable> cleanRegs = new ArrayList<UWDETable>();
		if (!isBasicPlan(coberturaBasica))
			return cleanRegs;

		boolean activeCopy = false;

		for (Iterator iterator = regs.iterator(); iterator.hasNext();) {
			UWDETable uwdeTable = (UWDETable) iterator.next();

			if (uwdeTable.equals(coberturaBasica)) {
				activeCopy = true;
				cleanRegs.add(uwdeTable);
			} else {
				if (Rules.isBasicPlan(uwdeTable)) {
					activeCopy = false;
					break;
				} else {
					if (activeCopy)
						cleanRegs.add(uwdeTable);
				}
			}

		}
		cleanRegs.remove(coberturaBasica);

		return cleanRegs;
	}
}
